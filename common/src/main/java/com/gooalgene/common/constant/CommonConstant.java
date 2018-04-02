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
    /**
     * 启动时缓存的SNP consequencetype、id Map
     */
    String CACHEDSNP = "cachedSNP";
    /**
     * 启动时缓存的INDEL consequencetype、id Map
     */
    String CACHEDINDEL = "cachedINDEL";
    /**
     * 排序缓存结果是否完成
     */
    String SUFFIX = "_done";
    /**
     * 排序结果
     */
    String SORTEDRESULT = "_sortedResult";
    /**
     * V1版本
     */
    String VERSIONONE = "Glycine_max.V1.0.23.dna.genome";
    /**
     * V2版本
     */
    String VERSIONTWO = "Gmax_275_v2.0";


    /**
     * 在security资源过滤器中校验用户所属的client（白名单）在session中的key
     */
    String CLIENT_ID="clientId";

    /**
     * rememberMe的key
     */
    String REMEMBER_ME_KEY="remember-me";

    /**
     * rememberMe的过期时间
     */
    Integer REMEMBER_ME_TIME_OUT=60*60*24*7;

    /**
     * 签发jwt秘钥
     */
    String JWT_KEY="guao";

    /**
     * 刷新token过期时间
     */
    Integer REFRESH_TOKEN_TIME_OUT=60*60*3;

    /**
     * clientId在cookie中的key
     */
    String CLIENT_ID_IN_COOKIE="checkoutWhite";
}
