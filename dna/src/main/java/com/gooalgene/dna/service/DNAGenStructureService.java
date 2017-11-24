package com.gooalgene.dna.service;

import com.gooalgene.dna.dao.DNAGensStructureDao;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@Service
public class DNAGenStructureService {

    @Autowired
    private DNAGensStructureDao dnaGensStructureDao;

    List<DNAGenStructureDto> getByGeneId(String geneId){
        return dnaGensStructureDao.getByGeneId(geneId);
    }


    public List<DNAGenStructureDto> getByStartEnd(String chr, Integer startPos, Integer endPos) {
        return dnaGensStructureDao.getByStartEnd(chr,startPos,endPos);
    }
}
