package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNAGensDao;
import com.gooalgene.dna.entity.DNAGens;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@Service
public class DNAGensService {

    @Autowired
    private DNAGensDao dnaGensDao;

    /**
     * 后台管理查询MrnaGens分页处理
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchDNAGensbyKeywords(String type, String keywords, Page<DNAGens> page) {
        JSONArray data = new JSONArray();
        DNAGens dnaGens = new DNAGens();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                dnaGens.setKeywords(keywords);
            }//空白查询所有
        }
        dnaGens.setPage(page);
        List<DNAGens> list = dnaGensDao.findDNAGensList(dnaGens);
        page.setList(list);
        for (DNAGens dnaGens1 : list) {
            data.add(dnaGens1.toJSON());
        }
        return data;
    }

    @Transactional(readOnly = false)
    public boolean add(DNAGens dnaGens) {
        return dnaGensDao.add(dnaGens);
    }

    @Transactional(readOnly = false)
    public int insertBatch(List<DNAGens> dnaGenses) {
        return dnaGensDao.insertBatch(dnaGenses);
    }

    public DNAGens findById(int id) {
        return dnaGensDao.findById(id);
    }

    public DNAGens findByGene(String gene) {
        return dnaGensDao.findDNAGensInfoByGene(gene);
    }

    @Transactional(readOnly = false)
    public int update(DNAGens dnaGens) {
        return dnaGensDao.update(dnaGens);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id) {
        return dnaGensDao.deleteById(id);
    }

    /**
     * 前端页面查询结果
     *
     * @param gene
     * @param page
     * @return
     */
    public Map queryDNAGenesByGenes(String gene, Page<DNAGens> page) {
        Map result = new HashMap();
        result.put("gene", gene);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(gene)) {
            DNAGens dnaGens = new DNAGens();
            dnaGens.setKeywords(gene);
            dnaGens.setPage(page);
            List<DNAGens> list = dnaGensDao.findDNAGensList(dnaGens);
            System.out.println("Size:" + list.size());
            page.setList(list);
            for (DNAGens dnaGens1 : list) {
                data.add(dnaGens1.toJSON());
            }
        }
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }


    public List<String> getByRegion(String chr,String start, String end){
        List<String> list= dnaGensDao.getByRegion(chr,start,end);
        Map map= Maps.newHashMap();
        for(String gene:list){
            Integer geneSuffix= Integer.parseInt(StringUtils.substring(gene,9));
            map.put(geneSuffix,gene);
        }
        return  compareGeneIds(map);
    }

    private List<String> compareGeneIds(Map<Integer,String> map){
        ArrayList<Map.Entry<Integer,String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer,String>>() {
            @Override
            public int compare(Map.Entry<Integer,String> arg0,Map.Entry<Integer,String> arg1) {
                return arg0.getKey() - arg1.getKey();
            }
        });
        List<String> geneIds=Lists.newArrayList();
        for (int i = 0; i < list.size(); i++){
            geneIds.add(list.get(i).getValue());
        }
        return geneIds;
    }

}
