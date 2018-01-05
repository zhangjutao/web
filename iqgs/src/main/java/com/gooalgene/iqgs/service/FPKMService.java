package com.gooalgene.iqgs.service;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FPKMService {

    @Autowired
    private FPKMDao fpkmDao;

    public List<String> findProperGeneUnderSampleRun(List<GeneExpressionConditionEntity> condition){
        return fpkmDao.findGeneThroughGeneExpressionCondition(condition);
    }
}
