package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.AdvanceInfoDao;
import com.gooalgene.entity.AdvanceInfo;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 陈冬 on 2017/7/14.
 */
@Service
public class AdvanceInfoService {

    @Autowired
    private AdvanceInfoDao advanceInfoDao;

    public List<AdvanceInfo> findAdvanceInfoList() {
        return advanceInfoDao.findList();
    }

    @Transactional(readOnly = false)
    public void saveAdvanceinfo(AdvanceInfo advanceInfo) {
        advanceInfoDao.saveAdvanceinfo(advanceInfo);
    }

    public AdvanceInfo findAdvanceInfoById(int id) {
        return advanceInfoDao.findAdvanceInfoById(id);
    }

    @Transactional(readOnly = false)
    public void updateAdvanceInfo(AdvanceInfo advanceInfo) {
        advanceInfoDao.updateAdvanceInfo(advanceInfo);
    }

    @Transactional(readOnly = false)
    public void deleteAdvanceInfo(int id) {
        advanceInfoDao.deleteAdvanceInfo(id);
    }

    /**
     * 后台管理分页
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchAdvanceInfosbyKeywords(String type, String keywords, Page<AdvanceInfo> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        AdvanceInfo advanceInfo = new AdvanceInfo();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                advanceInfo.setQtlName(keywords);
                advanceInfo.setPlantTraitOntology(keywords);
                advanceInfo.setPlantOntology(keywords);
                advanceInfo.setGeneOntology(keywords);
                advanceInfo.setOtherRelatedQtls(keywords);
                advanceInfo.setOtherNamesQtl(keywords);
            }
        } else {
            return data;
        }
        advanceInfo.setPage(page);
        List<AdvanceInfo> list = null;
        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
            list = advanceInfoDao.findByTypeAllList(advanceInfo);
        }
        for (AdvanceInfo m : list) {
            Map map = new HashMap();
            map.put("id", m.getId());
            String qtlName = m.getQtlName();
            map.put("qtlName", qtlName);
            String plantTraitOntology = m.getPlantTraitOntology();
            map.put("plantTraitOntology", "");
            if (plantTraitOntology != null) {
                String[] plTraiOnto = plantTraitOntology.split("\t");
                if (plTraiOnto.length == 3) {
                    map.put("plantTraitOntology", plTraiOnto[2]);
                }
            }
            String plantOntology = m.getPlantOntology();
            map.put("plantOntology", "");
            if (plantOntology != null) {
                String[] plOnto = plantOntology.split("\t");
                if (plOnto.length == 3) {
                    map.put("plantOntology", plOnto[2]);
                }
            }
            String geneOntology = m.getGeneOntology();
            map.put("geneOntology", "");
            if (geneOntology != null) {
                String[] plOnto = geneOntology.split("\t");
                if (plOnto.length == 3) {
                    map.put("geneOntology", plOnto[2]);
                }
            }
            String otherRelatedQtls = m.getOtherRelatedQtls();
            map.put("otherRelatedQtls", "");
            if (otherRelatedQtls != null) {
                String[] other = otherRelatedQtls.split("\t");
                if (other.length == 2) {
                    Set<String> result = new HashSet<String>();//发现数据库数据存在重复，此处去重
                    if (!qtlName.contains(",")) {//qtlname自己不包含逗号
                        String[] qtls = other[1].split(",");
                        for (String s : qtls) {
                            result.add(s);
                        }
                    } else {
                        //Other related QTL's	Root length, primary 2-1,Root length, primary 2-2
                        String prefix = qtlName.split(",")[0];
                        String[] qtls = other[1].split(",");
                        for (String s : qtls) {
                            if (!s.equals(prefix)) {
                                String a = prefix + "," + s;
                                result.add(a);
                            }
                        }
                    }
                    StringBuffer stringBuffer = new StringBuffer();
                    for (String s1 : result) {
                        stringBuffer.append(s1).append(",");
                    }
                    String qtls = stringBuffer.toString();
                    qtls = qtls.substring(0, qtls.length() - 1);//最后一个逗号
                    map.put("otherRelatedQtls", qtls);
                }
            }
            String otherNamesQtl = m.getOtherNamesQtl();
            map.put("otherNamesQtl", "");
            if (otherNamesQtl != null) {
                String[] otherN = otherNamesQtl.split("\t");
                if (otherN.length == 2) {
                    map.put("otherNamesQtl", otherN[1]);
                }
            }
            data.add(map);
        }
        page.setList(list);
        return data;
    }
}
