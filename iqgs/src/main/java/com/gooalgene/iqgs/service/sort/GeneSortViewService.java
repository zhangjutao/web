package com.gooalgene.iqgs.service.sort;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gooalgene.iqgs.dao.GeneSortDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class GeneSortViewService {
    private static final Logger logger = LoggerFactory.getLogger(GeneSortViewService.class);
    @Autowired
    private GeneSortDao geneSortDao;

    @Autowired
    private SortService sortService;

    /**
     * 对传入的基因ID进行查询、排序，输出排序后的结果
     * @param geneIds 输入的基因ID
     * @param tissue 用户所选的组织，多种二级组织可以组成一个完整的Tissue对象
     * @param categoryId 用户所选性状对应ID值
     * @return 拍完序的基因基本信息集合
     */
    public PageInfo<SortedResult> findViewByGeneId(List<String> geneIds, Tissue tissue, Integer categoryId, int pageNo, int pageSize){
        String fields = getAllValidTissueProperties(tissue);
        List<SortedSearchResultView> views = geneSortDao.findViewByGeneId(geneIds, fields, categoryId);
        List<SortedResult> result = new ArrayList<>();
        try {
            List<SortedSearchResultView> sortResult = sortService.sort(views);
            Ordering<SortedSearchResultView> ordering = Ordering.natural().onResultOf(new Function<SortedSearchResultView, Double>() {
                @Override
                public Double apply(SortedSearchResultView input) {
                    return input.getScore();
                }
            });
            Collections.sort(sortResult, ordering);
            Collection<SortedResult> transform = Collections2.transform(sortResult, new Function<SortedSearchResultView, SortedResult>() {
                @Override
                public SortedResult apply(SortedSearchResultView input) {
                    DNAGenBaseInfo genBaseInfo = input.getBaseInfo();
                    return new SortedResult(genBaseInfo.getGeneId(), genBaseInfo.getGeneName(),
                            genBaseInfo.getDescription(), input.getChromosome(), input.getLocation());
                }
            });
            result.addAll(transform);
        } catch (IllegalAccessException e) {
            logger.error("排序过程中出错", e.getCause());
        }
        int size = result.size();
        int end = pageNo*pageSize > size ? size : pageNo*pageSize;
        Page<SortedResult> page = new Page<>(pageNo, pageSize, false);
        page.setTotal(size);
        page.addAll(result.subList((pageNo-1)*pageSize, end));
        return new PageInfo<SortedResult>(page);
    }

    /**
     * 通过反射获取组织中所有非空的属性值
     */
    private String getAllValidTissueProperties(Tissue tissue){
        checkNotNull(tissue);
        Class<? extends Tissue> tissueClass = tissue.getClass();
        Field[] declaredFields = tissueClass.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < declaredFields.length; i++){
            declaredFields[i].setAccessible(true);
            try {
                Object fieldValue = declaredFields[i].get(tissue);
                if (fieldValue != null){
                    builder.append(declaredFields[i].getName());
                    builder.append(",");
                }
            } catch (IllegalAccessException e) {
                logger.error("执行" + declaredFields[i].getName() + "出错", e.getCause());
            }
        }
        if (builder.length() == 0) {
            throw new IllegalArgumentException("传入的Tissue对象中必须包含二级组织");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
