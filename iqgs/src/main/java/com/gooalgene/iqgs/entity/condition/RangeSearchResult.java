package com.gooalgene.iqgs.entity.condition;

import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.Tissue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * iqgs查询返回列表对应实体类
 * @author crabime
 */
public class RangeSearchResult extends Tissue {
    private String geneId;
    private String geneName;

    private String description;

    /**
     * 是否存在非同义突变，默认为false
     */
    private boolean existsSNP = false;

    /**
     * 关联的所有QTL
     */
    private List<Associatedgenes> associateQTLs;

    /**
     * 大于30的组织
     */
    private Set<String> rootTissues = new HashSet<>();

    private final static int STANDARD = 30;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExistsSNP() {
        return existsSNP;
    }

    public void setExistsSNP(boolean existsSNP) {
        this.existsSNP = existsSNP;
    }

    public List<Associatedgenes> getAssociateQTLs() {
        return associateQTLs;
    }

    public void setAssociateQTLs(List<Associatedgenes> associateQTLs) {
        this.associateQTLs = associateQTLs;
    }

    public Set<String> getRootTissues() {
        if (super.getPodAll() != null && super.getPodAll() > STANDARD){
            rootTissues.add("豆荚");
        }
        if (super.getSeedAll() != null && super.getSeedAll() > STANDARD){
            rootTissues.add("种子");
        }
        if (super.getRootAll() != null && super.getRootAll() > STANDARD){
            rootTissues.add("根");
        }
        if (super.getShootAll() != null && super.getShootAll() > STANDARD){
            rootTissues.add("芽");
        }
        if (super.getLeafAll() != null && super.getLeafAll() > STANDARD){
            rootTissues.add("叶");
        }
        if (super.getSeedlingAll() != null && super.getSeedlingAll() > STANDARD){
            rootTissues.add("幼苗");
        }
        if (super.getFlowerAll() != null && super.getFlowerAll() > STANDARD){
            rootTissues.add("花");
        }
        if (super.getStemAll() != null && super.getStemAll() > STANDARD){
            rootTissues.add("茎");
        }
        return rootTissues;
    }
}
