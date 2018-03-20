package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
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

    public DNAGens findByGeneId(String geneId) {
        return dnaGensDao.findByGeneId(geneId);
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

    /**
     * 根据基因ID,查询基因基本信息
     * @param gene 基因ID
     */
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
     * 侧边栏：根据输入基因，获取基因搜索结果
     */
    public Map<String, Object> queryDNAGenesByGenes(String gene, int pageNo, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        if (StringUtils.isNotBlank(gene)) {
            result.put("gene", gene);
            result.put("pageNo", pageNo);
            result.put("pageSize", pageSize);
            DNAGens dnaGens = new DNAGens();
            dnaGens.setKeywords(gene);
            PageHelper.startPage(pageNo, pageSize);
            List<DNAGens> list = dnaGensDao.findDNAGensList(dnaGens);
            result.put("total", ((com.github.pagehelper.Page<DNAGens>) list).getTotal());
            result.put("data", list);
        }
        return result;
    }

    public List<String> getByRegionNoCompare(String chr,long start, long end){
        List<String> list= dnaGensDao.getByRegion(chr,start,end);
        return  list;
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
