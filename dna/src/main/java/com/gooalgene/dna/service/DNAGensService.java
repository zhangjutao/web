package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
import com.gooalgene.dna.dao.DNAGensDao;
import com.gooalgene.dna.entity.ChromosomeList;
import com.gooalgene.dna.entity.DNAGens;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DNAGensService {

    @Autowired
    private DNAGensDao dnaGensDao;

    public DNAGens findByGeneId(String geneId) {
        return dnaGensDao.findByGeneId(geneId);
    }

    public List<ChromosomeList> fetchAllChromosome() {
        return dnaGensDao.fetchAllChromosome();
    }

    /**
     * 根据基因ID,查询基因基本信息
     * @param gene 基因ID
     */
    public DNAGens findByGene(String gene) {
        return dnaGensDao.findDNAGensInfoByGene(gene);
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

    public Set<String> getByRegionNoCompare(String chr,long start, long end){
        Set<String> list= dnaGensDao.getByRegion(chr,start,end);
        return list;
    }
}
