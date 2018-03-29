package com.gooalgene.dna.entity;

import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.entity.result.MinimumSNPResult;

import java.util.List;
import java.util.Set;

/**
 * DNA中SNP/INDEL搜索返回前台的图标位点数据，
 * 包含基因/区间内的所有SNP位点，以及选中的当前位置基因的基因结构数据
 *
 * @author crabime
 */
public class GraphSearchResult {

    /**
     * 该区间内的所有SNP位点
     */
    private List<MinimumSNPResult> snpList;

    /**
     * 一个基因对应多个structure
     */
    private List<DNAGenStructureDto> structureList;

    /**
     * 该区间内的所有基因ID
     */
    private Set<String> geneInsideRegion;

    /**
     * 当前SNP位点图形中的基因ID
     */
    private String geneId;

    /**
     * 图中该基因实际的上游位点
     */
    private Long upstream;

    /**
     * 途中该基因实际的下游位点
     */
    private Long downstream;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public Long getUpstream() {
        return upstream;
    }

    public void setUpstream(Long upstream) {
        this.upstream = upstream;
    }

    public Long getDownstream() {
        return downstream;
    }

    public void setDownstream(Long downstream) {
        this.downstream = downstream;
    }

    public Set<String> getGeneInsideRegion() {
        return geneInsideRegion;
    }

    public void setGeneInsideRegion(Set<String> geneInsideRegion) {
        this.geneInsideRegion = geneInsideRegion;
    }

    public List<MinimumSNPResult> getSnpList() {
        return snpList;
    }

    public void setSnpList(List<MinimumSNPResult> snpList) {
        this.snpList = snpList;
    }

    public List<DNAGenStructureDto> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<DNAGenStructureDto> structureList) {
        this.structureList = structureList;
    }
}
