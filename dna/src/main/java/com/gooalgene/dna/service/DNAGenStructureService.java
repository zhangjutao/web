package com.gooalgene.dna.service;

import com.gooalgene.dna.dao.DNAGensStructureDao;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@Service
public class DNAGenStructureService {

    @Autowired
    private DNAGensStructureDao dnaGensStructureDao;

    public List<DNAGenStructureDto> getByGeneId(String geneId){
        return dnaGensStructureDao.getByGeneId(geneId);
    }


    public List<DNAGenStructureDto> getByStartEnd(String chr, Integer startPos, Integer endPos,List<String> geneIds) {
        return dnaGensStructureDao.getByStartEnd(chr,startPos,endPos,geneIds);
    }

    public List<Integer> getGeneStructureId(String chr, Long start, Long end){
        return dnaGensStructureDao.getGeneStructureId(chr, start, end);
    }

    public Map<String, List<Integer>> fetchAllChromosomeAndID(){
        Map<String, List<Integer>> map = new HashMap<>();  //存在染色体与基因结构之间集合
        List<String> allChromosome = dnaGensStructureDao.fetchAllChromosome();
        for (String chromosome : allChromosome){
            List<Integer> idInChromosome = dnaGensStructureDao.fetchAllChromosomeAndID(chromosome);
            map.put(chromosome, idInChromosome);
        }
        return map;
    }
}
