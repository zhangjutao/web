package com.gooalgene.iqgs.service.sort;

import com.gooalgene.iqgs.dao.GeneSortDao;
import com.gooalgene.iqgs.entity.FpkmDto;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.sort.IndelScore;
import com.gooalgene.iqgs.entity.sort.QtlScore;
import com.gooalgene.iqgs.entity.sort.SnpScore;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SortServiceImpl implements SortService, InitializingBean {

    @Autowired
    private CacheManager cacheManager;

    private Cache snpCache;

    private Cache indelCache;

    @Autowired
    private GeneSortDao geneSortDao;

    public int calculateSNPConsequenceTypeScore(List<String> consequenceTypes){
        int snpConsequenceTypeResult = 0;
        if (consequenceTypes == null || consequenceTypes.size() == 0){
            return 0;
        }
        for (int i = 0; i < consequenceTypes.size(); i++){
            //todo 增加错误consequencetype处理
            Cache.ValueWrapper num = snpCache.get(consequenceTypes.get(i));
            int currentScore = (int) num.get();
            snpConsequenceTypeResult += currentScore;
        }
        return snpConsequenceTypeResult;
    }

    public int calculateINDELConsequenceTypeScore(List<String> consequenceTypes){
        int snpConsequenceTypeResult = 0;
        if (consequenceTypes == null || consequenceTypes.size() == 0){
            return 0;
        }
        for (int i = 0; i < consequenceTypes.size(); i++){
            Cache.ValueWrapper num = indelCache.get(consequenceTypes.get(i));
            int currentScore = (int) num.get();
            snpConsequenceTypeResult += currentScore;
        }
        return snpConsequenceTypeResult;
    }

    @Override
    public List<SortedSearchResultView> sort(List<SortedSearchResultView> views,List<String> qtlNames) throws IllegalAccessException {
        for (SortedSearchResultView view:views){
            calculateScoreOfFpkm(view);
            calculateScoreOfQtl(view,qtlNames);
            calculateScoreOfSnpAndIndel(view);
        }
        return views;
    }

    /**
     * 计算fpkm得分
     * @param view
     * @return
     * @throws IllegalAccessException
     */
    public SortedSearchResultView calculateScoreOfFpkm(SortedSearchResultView view) throws IllegalAccessException {
        Integer count=0;
        Double score=0d;
        FpkmDto fpkmDto=new FpkmDto();
        BeanUtils.copyProperties(view.getFpkm(),fpkmDto);
        Field[] declaredFields = fpkmDto.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            f.setAccessible(true);
            if (f.get(fpkmDto) != null) { //判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
                if(f.getName().toLowerCase().contains("id")||f.getName().endsWith("All")){
                    System.out.println(f.getName());
                    continue;
                }
                ++count;
                Double value= (Double) f.get(fpkmDto);
                if(0<=value&&value<5){
                    score+=10;
                }else if(5<=value&&value<15){
                    score+=20;
                }else if(15<=value&&value<30){
                    score+=30;
                }else if(30<=value&&value<60){
                    score+=40;
                }else if(value>=60){
                    score+=50;
                }
            }
        }
        Double score1 = view.getScore();
        if(score1==null){
            score1=0d;
        }
        view.setScore(score1+score/count);
        return view;
    }

    /**
     * 计算QTL得分
     * @param view
     * @return
     */
    public SortedSearchResultView calculateScoreOfQtl(SortedSearchResultView view,List<String> qtlNames){
        Double score1 = view.getScore();
        if(score1==null){
            score1=0d;
        }
        Integer size=0;
        for(String qtlName:qtlNames){
            for(String qtlNameInView:view.getAllQtl()){
                if(qtlName.equals(qtlNameInView)){
                    size++;
                }
            }
        }
        view.setScore(score1+size*10);
        return view;
    }
    public SortedSearchResultView calculateScoreOfSnpAndIndel(SortedSearchResultView view){
        Double oldScore=view.getScore();
        Double sum=0d;
        for(SnpScore snpScore:view.getSnpConsequenceType()){
            Integer score = snpScore.getScore();
            if(score==null){
                score=0;
            }
            sum+=(score*snpScore.getCount());/**/
        }
        sum=sum/(view.getSnpConsequenceType().size());
        Integer sum2=0;
        for(IndelScore indelScore:view.getIndelConsequenceType()){
            Integer score = indelScore.getScore();
            if(score==null){
                score=0;
            }
            sum2+=(score*indelScore.getCount());/**/
        }
        sum2=sum2/(view.getIndelConsequenceType().size());
        view.setScore(oldScore+sum+sum2);
        return view;
    }
    public String getPrefixBeforeFields(String fields){
        String[] strings=fields.split(",");
        List<String> strings2= Lists.newArrayList();
        for (String str:strings){
            strings2.add("a."+str);
        }
        return StringUtils.join(strings2.toArray(),",");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        snpCache = cacheManager.getCache("snpCache");
        indelCache = cacheManager.getCache("indelCache");
    }
}
