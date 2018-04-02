package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.entity.result.GroupCondition;
import com.gooalgene.utils.FrontEndReflectionUtils;
import com.gooalgene.dna.util.JacksonUtils;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by ShiYun on 2017/9/6 0006.
 */
@Service
public class DNARunService {
    private static final Logger logger = LoggerFactory.getLogger(DNARunService.class);
    @Autowired
    private DNARunDao dnaRunDao;

    /**
     * 根据页面分组查询对应的样本信息
     * 在选择品种查询时，传入的一个group对应多个品种，前端传入的品种均为ID值，改ID集合存放在condition变量中，key为idList
     * 返回一个Map：key(群体名称), value(多个idList),
     * 如果前端传入参数为：
     * <pre>
     *     group: [
     *            {
     *            "name": "品种名1,品种名2,品种名3",
     *            "condition": {
     *            "idList": "1,2,3"
     *            }},{
     *            "name": "物种Glycine soja,位置China,百粒重0g-10g,含油量0%-10%,蛋白质30%-40%",
     *            "id": 1521441363524,
     *            "condition": {
     *            "species": "Glycine soja",
     *            "locality": "China",
     *            "weightPer100seeds": {
     *            "min": "0",
     *            "max": "10"
     *            }}]
     * </pre>
     * 上述既包含多个品种构成的群体，也包含多个群体属性构成的群体，此时需要将属性构成的群体转换为SampleInfo对象，
     * 然后获取该SampleInfo对象的id集合，保持最后返回值都一致
     */
    public Map<String, List<String>> queryDNARunByCondition(String group) {
        Map<String, List<String>> result = new HashMap<>();
        if (StringUtils.isNotBlank(group)) {
            try {
                List<GroupCondition> groupConditions = JacksonUtils.convertJsonToArray(group, GroupCondition.class);
                for (GroupCondition input : groupConditions) {
                    String groupName = input.getName();
                    List<String> finalIdList = new ArrayList<>();
                    String idList = (String) input.getCondition().get("idList");
                    // 如果传入的id集合collection属性中包含idList字段且值包含多个sample_info ID值
                    if (!org.springframework.util.StringUtils.isEmpty(idList) && idList.contains(",")) {
                        finalIdList = Arrays.asList(idList.split(","));
                    } else {
                        SampleInfoDto sampleInfoDto = FrontEndReflectionUtils.constructNewInstance("com.gooalgene.dna.entity.SampleInfoDto", input.getCondition());
                        // 获取所有符合条件的样本
                        List<SampleInfoDto> allProperSampleInfo = dnaRunDao.getListByCondition(sampleInfoDto);
                        // 从筛选的样本中获取它们的ID
                        Collection<String> allProperSampleInfoId = Collections2.transform(allProperSampleInfo, new Function<SampleInfoDto, String>() {
                            @Override
                            public String apply(SampleInfoDto input) {
                                return input.getId();
                            }
                        });
                        finalIdList = new ArrayList<>(allProperSampleInfoId);
                    }
                    result.put(groupName, finalIdList);
                }
            } catch (IOException e) {
                logger.error("传入JSON字符串：" + group + "异常", e.getCause());
                e.printStackTrace();
            }
        }
        return result;
    }

    public Map<String, List<String>> queryDNARunByCondition(List<GroupCondition> groupConditions) {
        Map<String, List<String>> result = new HashMap<>();
        for (GroupCondition input : groupConditions) {
            String groupName = input.getName();
            List<String> finalIdList = new ArrayList<>();
            String idList = (String) input.getCondition().get("idList");
            // 如果传入的id集合collection属性中包含idList字段且值包含多个sample_info ID值
            if (!org.springframework.util.StringUtils.isEmpty(idList) && idList.contains(",")) {
                finalIdList = Arrays.asList(idList.split(","));
                // 获取所有的run_no
                List<SampleInfoDto> allSampleRun = dnaRunDao.getByCultivarForExport(finalIdList, true);
                Collection<String> transformRunNo = Collections2.transform(allSampleRun, new Function<SampleInfoDto, String>() {
                    @Override
                    public String apply(SampleInfoDto sampleInfoDto) {
                        return sampleInfoDto.getRunNo();
                    }
                });
                finalIdList = new ArrayList<>(transformRunNo);
            } else {
                SampleInfoDto sampleInfoDto = FrontEndReflectionUtils.constructNewInstance("com.gooalgene.dna.dto.SampleInfoDto", input.getCondition());
                // 获取所有符合条件的样本
                List<SampleInfoDto> allProperSampleInfo = dnaRunDao.getListByCondition(sampleInfoDto);
                // 从筛选的样本中获取它们的ID
                Collection<String> allProperSampleInfoId = Collections2.transform(allProperSampleInfo, new Function<SampleInfoDto, String>() {
                    @Override
                    public String apply(SampleInfoDto input) {
                        return input.getRunNo();
                    }
                });
                finalIdList = new ArrayList<>(allProperSampleInfoId);
            }
            result.put(groupName, finalIdList);
        }
        return result;
    }

    /**
     * 根据条件查询样本数据
     *
     * @param group
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageInfo<SampleInfoDto> queryDNARunByGroup(String group, int pageNo, int pageSize) throws IOException {
//        Map result = new HashMap();
//        result.put("group", group);
//        result.put("pageNo", page.getPageNo());
//        result.put("pageSize", page.getPageSize());
//        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(group)) {
            GroupCondition groupCondition=JacksonUtils.convertJsonToObject(group,GroupCondition.class);
            SampleInfo sampleInfo=getQuery(groupCondition.getCondition());
            SampleInfoDto sampleInfoDto=new SampleInfoDto();
            BeanUtils.copyProperties(sampleInfo,sampleInfoDto);
            PageHelper.startPage(pageNo, pageSize);
            List<SampleInfoDto> list=dnaRunDao.findListWithTypeHandler(sampleInfoDto);
            PageInfo<SampleInfoDto> pageInfo = new PageInfo(list);
            return pageInfo;
        }else {
            return new PageInfo<SampleInfoDto>();
        }
//        result.put("total", page.getCount());
//        result.put("data", data);
//        return result;
    }

    public PageInfo<SampleInfoDto> getListByConditionWithTypeHandler(SampleInfoDto sampleInfoDto, Integer pageNum, Integer pageSize, String isPage){
        if(!StringUtils.isBlank(isPage)){
        PageHelper.startPage(pageNum,pageSize);
        }
        List<SampleInfoDto> list=dnaRunDao.getListByConditionWithTypeHandler(sampleInfoDto);
        PageInfo<SampleInfoDto> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    public  List<SampleInfoDto> getAll(){
        return dnaRunDao.getListByCondition(new SampleInfoDto());
    }

    /**
     * 根据查询参数json转换为DNARun实体
     *
     * @param conditions
     * @return
     */
    private SampleInfo getQuery(Map<String,Object> conditions) {
        JSONObject jsonObject = JSONObject.fromObject(conditions);
        SampleInfo sampleInfo = new SampleInfo();
        if (jsonObject.containsKey("scientificName")) {
            sampleInfo.setScientificName(jsonObject.getString("scientificName"));
        }
        if (jsonObject.containsKey("locality")) {
            sampleInfo.setLocality(jsonObject.getString("locality"));
        }
        if (jsonObject.containsKey("sampleId")) {
            sampleInfo.setSampleId(jsonObject.getString("sampleId"));
        }
        if (jsonObject.containsKey("strainName")) {
            sampleInfo.setStrainName(jsonObject.getString("strainName"));
        }
        if (jsonObject.containsKey("preservationLocation")) {
            sampleInfo.setPreservationLocation(jsonObject.getString("preservationLocation"));
        }
        if (jsonObject.containsKey("type")) {
            sampleInfo.setType(jsonObject.getString("type"));
        }
        if (jsonObject.containsKey("environment")) {
            sampleInfo.setEnvironment(jsonObject.getString("environment"));
        }
        if (jsonObject.containsKey("materials")) {
            sampleInfo.setMaterials(jsonObject.getString("materials"));
        }
        if (jsonObject.containsKey("treat")) {
            sampleInfo.setTreat(jsonObject.getString("treat"));
        }
        if (jsonObject.containsKey("definitionTime")) {
            sampleInfo.setDefinitionTime(jsonObject.getString("definitionTime"));
        }
        if (jsonObject.containsKey("taxonomy")) {
            sampleInfo.setTaxonomy(jsonObject.getString("taxonomy"));
        }
        if (jsonObject.containsKey("myceliaPhenotype")) {
            sampleInfo.setMyceliaPhenotype(jsonObject.getString("myceliaPhenotype"));
        }
        if (jsonObject.containsKey("myceliaDiameter")) {
            sampleInfo.setMyceliaDiameter(jsonObject.getString("myceliaDiameter"));
        }
        if (jsonObject.containsKey("myceliaColor")) {
            sampleInfo.setMyceliaColor(jsonObject.getString("myceliaColor"));
        }
        return sampleInfo;
    }

    public boolean add(SampleInfo sampleInfo) {
        return dnaRunDao.insert(sampleInfo);
    }

    public SampleInfo findById(int id) {
        return dnaRunDao.get(String.valueOf(id));
    }

    public int update(SampleInfo sampleInfo) {
        return dnaRunDao.update(sampleInfo);
    }

    public boolean delete(int id) {
        return dnaRunDao.deleteById(id);
    }

    public PageInfo<DNARun> getByCultivar(List<String> ids,Integer pageNum,Integer pageSize){
        List<DNARun> list= Lists.newArrayList();
        PageHelper.startPage(pageNum,pageSize);
        if(CollectionUtils.isNotEmpty(ids)){
            list=dnaRunDao.getByCultivar(ids);
        }
        return new PageInfo<>(list);
    }
}
