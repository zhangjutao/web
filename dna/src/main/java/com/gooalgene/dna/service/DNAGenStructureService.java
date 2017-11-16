package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNAGensDao;
import com.gooalgene.dna.dao.DNAGensStructureDao;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.dna.entity.DNAGens;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    List<DNAGenStructure> getByGeneId(String geneId){
        return dnaGensStructureDao.getByGeneId(geneId);
    }


}
