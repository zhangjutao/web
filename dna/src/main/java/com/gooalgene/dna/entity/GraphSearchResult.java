package com.gooalgene.dna.entity;

import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.dto.SNPDto;

import java.util.List;

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
    private List<SNPDto> snpList;

    /**
     * 一个基因对应多个structure
     */
    private List<DNAGenStructureDto> structureList;

    public List<SNPDto> getSnpList() {
        return snpList;
    }

    public void setSnpList(List<SNPDto> snpList) {
        this.snpList = snpList;
    }

    public List<DNAGenStructureDto> getStructureList() {
        return structureList;
    }

    public void setStructureList(List<DNAGenStructureDto> structureList) {
        this.structureList = structureList;
    }
}
