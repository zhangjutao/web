package com.gooalgene.dna.service;

import com.github.pagehelper.PageHelper;
import com.gooalgene.dna.dao.DNAGensDao;
import com.gooalgene.dna.entity.ChromosomeList;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.result.GeneMinAndMax;
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

    /**
     * 查找该染色体该区间内基因的最新位置和最大位置，该方法仅使用于确定有基因的区间，否则调用报错
     * 如在按照区间搜索，如果该区间内有基因，这里会获取该区间内基因的起始位置极端值
     * @param chr 染色体
     * @param start 起点位置
     * @param end 终点位置
     * @return 两个值的集合，第一个为最小值，第二个为最大值
     */
    public GeneMinAndMax findMinAndMaxPos(String chr,long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("start arguments greater than end arguments");
        }
        GeneMinAndMax minAndMax = dnaGensDao.findMinAndMax(chr, start, end);
        if (minAndMax == null) {
            throw new IllegalArgumentException("findMinAndMaxPos only apply for those having genes method");
        }
        return minAndMax;
    }
}
