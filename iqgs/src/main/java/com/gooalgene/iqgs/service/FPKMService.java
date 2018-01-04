package com.gooalgene.iqgs.service;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FPKMService {

    @Autowired
    private FPKMDao fpkmDao;

    public List<GeneFPKM> findProperGeneUnderSampleRun(int sampleRunId, double begin, double end){
        return null;
    }
}
