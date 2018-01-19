package com.gooalgene.common.constant;

public interface CommonConstant {

    String RUN_DNA="run_dna";
    Integer ADMIN_VALUE=1;
    Integer USER_VALUE=2;
    String SNP = "SNP";
    String INDEL = "INDEL";
    String EXONIC_NONSYNONYMOUSE = "exonic_nonsynonymous SNV";
    String FIRSTSNPCHROMOSOME = "SNP_Chr01"; //第一条SNP染色体
    String FIRSTINDELCHROMOSOME = "INDEL_Chr01"; //第一条INDEL染色体
    String CONSEQUENCETYPE = "consequencetype"; //序列类型
    String CLASSIFY = "classifys";  //组织分类集合


    /**
     * 缓存中的KEY
     */
    String DEFAULTRESULTVIEW = "defaultResultView";
    /**
     * 高级搜索中QTL二级联动内容
     */
    String ADVANCESEARCHQTL = "advanceSearchQTL";
    /**
     * 高级搜索中下拉列表中组织选项
     */
    String ADVANCESEARCHORGANIC = "advanceSearchOrganic";
    /**
     * 高级搜索SNP checkbox选项
     */
    String ADVANCESEARCHSNP = "advanceSearchSNP";
    /**
     * 高级搜索INDEL checkbox选项
     */
    String ADVANCESEARCHINDEL = "advanceSearchINDEL";
}
