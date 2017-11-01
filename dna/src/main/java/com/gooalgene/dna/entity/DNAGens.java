package com.gooalgene.dna.entity;

import com.gooalgene.common.DataEntity;
import net.sf.json.JSONObject;

/**
 * DNA 搜索基因实体类
 * Created by 陈冬 on 2017/8/22.
 */
public class DNAGens extends DataEntity<DNAGens> {

    private String geneId;
    private String geneName;
    private String geneFunction;

    private Long geneStart;//开始位置
    private Long geneEnd;//结束位置

    private String keywords;//当all查询时才存在

    public String getGeneFunction() {
        return geneFunction;
    }

    public void setGeneFunction(String geneFunction) {
        this.geneFunction = geneFunction;
    }

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

    public Long getGeneStart() {
        return geneStart;
    }

    public void setGeneStart(Long geneStart) {
        this.geneStart = geneStart;
    }

    public Long getGeneEnd() {
        return geneEnd;
    }

    public void setGeneEnd(Long geneEnd) {
        this.geneEnd = geneEnd;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", getId());
        jsonObject.put("gene", geneId);
        jsonObject.put("geneName", geneName == null ? "" : geneName);
        jsonObject.put("geneFunction", geneFunction == null ? "" : geneFunction);
        jsonObject.put("geneStart", geneStart);
        jsonObject.put("geneEnd", geneEnd);
        return jsonObject;
    }

    @Override
    public String toString() {
        return "DNAGens{" +
                "geneId='" + geneId + '\'' +
                ", geneName='" + geneName + '\'' +
                ", geneFunction='" + geneFunction + '\'' +
                ", geneStart=" + geneStart +
                ", geneEnd=" + geneEnd +
                ", keywords='" + keywords + '\'' +
                '}';
    }
}
