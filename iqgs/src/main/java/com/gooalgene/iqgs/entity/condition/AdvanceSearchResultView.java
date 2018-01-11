package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.iqgs.entity.GeneFPKM;

import java.util.ArrayList;
import java.util.List;

import static com.gooalgene.common.constant.CommonConstant.EXONIC_NONSYNONYMOUSE;

/**
 * 高级搜索数据表中查询结果临时视图
 * @author Crabime
 */
public class AdvanceSearchResultView extends GeneFPKM {
    /**
     * V1版基因ID
     */
    private String geneOldId;

    /**
     * 基因名字
     */
    private String geneName;

    /**
     * 基因功能
     */
    private String functions;

    /**
     * 大于30的组织
     */
    private List<String> largerThanThirtyTissue = new ArrayList<>();
    
    private final static int STANDARD = 30;

    public String getGeneOldId() {
        return geneOldId;
    }

    public void setGeneOldId(String geneOldId) {
        this.geneOldId = geneOldId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    public List<String> getLargerThanThirtyTissue() {
        if (super.getPodAll() != null && super.getPodAll() > STANDARD){
            largerThanThirtyTissue.add("豆荚");
        }
        if (super.getSeedAll() != null && super.getSeedAll() > STANDARD){
            largerThanThirtyTissue.add("种子");
        }
        if (super.getRootAll() != null && super.getRootAll() > STANDARD){
            largerThanThirtyTissue.add("根");
        }
        if (super.getShootAll() != null && super.getShootAll() > STANDARD){
            largerThanThirtyTissue.add("芽");
        }
        if (super.getLeafAll() != null && super.getLeafAll() > STANDARD){
            largerThanThirtyTissue.add("叶");
        }
        if (super.getSeedlingAll() != null && super.getSeedlingAll() > STANDARD){
            largerThanThirtyTissue.add("幼苗");
        }
        if (super.getFlowerAll() != null && super.getFlowerAll() > STANDARD){
            largerThanThirtyTissue.add("花");
        }
        if (super.getStemAll() != null && super.getStemAll() > STANDARD){
            largerThanThirtyTissue.add("茎");
        }
        return largerThanThirtyTissue;
    }

}
