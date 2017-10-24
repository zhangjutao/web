package com.gooalgene.utils;

/**
 * 用于导入csv文件判断是那一类文件
 * Created by ShiYun on 2017/7/27 0027.
 */
public class FileType {

    /**
     * trait_category
     * 大分类表
     * 文件内容为：分类名称\t中文说明\t别名（别名可能不提供）
     * qtl_name` varchar(100) DEFAULT NULL COMMENT 'qtl大类',
     * qtl_desc` varchar(45) DEFAULT NULL COMMENT '中文描述',
     * qtl_othername` varchar(100) DEFAULT NULL COMMENT '别名(soybase上的给出缩写)',
     */
    public static final String TYPE_TRAIT_CATEGORY = "T01";
    /**
     * trait_list
     * 小分类表
     * 文件内容：大类名称\t小类名称
     * qtl_id` int(11) DEFAULT NULL,（查询大类id--如果不存在，提示先上传大类，再上传小类）
     * trait_name` varchar(200) DEFAULT NULL,
     */
    public static final String TYPE_TRAIT_LIST = "T02";

    /**
     * qtl表
     * qtl_name` varchar(100) DEFAULT NULL COMMENT 'qtl名',
     * `trait` varchar(100) DEFAULT NULL COMMENT '所属trait',
     * `type` varchar(100) DEFAULT NULL COMMENT '所属大分类',
     * `chrlg_id` int(11) DEFAULT NULL COMMENT '位置信息', （由lg和chr字段共同决定）
     * `marker1` varchar(100) DEFAULT NULL COMMENT '定位区间的第一个marker',
     * `marker2` varchar(100) DEFAULT NULL COMMENT '定位区间的第二个marker(可能缺省)',
     * `associatedGenes_id` int(11) DEFAULT NULL COMMENT '区间所包含的所有基因',----（不提供）
     * `version` varchar(100) DEFAULT NULL COMMENT '版本信息',---（不提供）
     * `method` varchar(500) DEFAULT NULL COMMENT '定位该区间所用的试验方法',
     * `genome_start` float DEFAULT NULL COMMENT '该区间的起始位置(遗传距离)',
     * `genome_end` float DEFAULT NULL COMMENT '该区间的结束位置(遗传距离)',
     * `lod` float DEFAULT NULL COMMENT '对实验定位的区间的准确程度',
     * `parent1` varchar(100) DEFAULT NULL COMMENT '父本',
     * `parent2` varchar(100) DEFAULT NULL COMMENT '母本',
     * `ref` text COMMENT '参考文献',
     * `author` varchar(200) DEFAULT NULL COMMENT '文献作者',
     */
    public static final String TYPE_QTL = "T03";
    /**
     * qtlref表
     * `title` varchar(500) DEFAULT NULL COMMENT '标题名',
     * `authors` varchar(500) DEFAULT NULL COMMENT '作者',
     * `source` varchar(500) DEFAULT NULL COMMENT '所发表的杂志信息',
     * `abstract` text COMMENT '文章摘要',
     * `pubmed` varchar(500) DEFAULT NULL COMMENT '链接',
     * `qtl_name` varchar(500) DEFAULT NULL COMMENT '对应qtl名',---取文件名
     */
    public static final String TYPE_QTL_REF = "T04";
    /**
     * chrlg表
     * `id` int(11) NOT NULL AUTO_INCREMENT,
     * `chr` varchar(45) DEFAULT NULL COMMENT '染色体编号',
     * `lg` varchar(45) DEFAULT NULL COMMENT '连锁群编号',
     * `version` varchar(40) DEFAULT NULL COMMENT '对应染色体版本',
     * `bps` int(11) DEFAULT NULL COMMENT '染色体物理距离总长度(碱基个数)',
     * `cMs` float DEFAULT NULL COMMENT '染色体遗传距离总长度(cMs)',
     */
    public static final String TYPE_CHRLG = "T05";
    /**
     * marker表  前4列必存在，后3列不一定存在
     * `marker_name` varchar(45) DEFAULT NULL COMMENT 'Marker名',
     * `marker_type` varchar(45) DEFAULT NULL COMMENT 'Marker类型',
     * `marker_lg` varchar(45) DEFAULT NULL COMMENT '连锁群编号，括号内为染色体编号',
     * `position` float DEFAULT NULL COMMENT 'Marker的遗传距离',
     * `amplification_info` varchar(500) DEFAULT NULL COMMENT 'Marker序列信息',
     * `provider` varchar(45) DEFAULT NULL COMMENT 'Marker信息提供者',
     * `ref` varchar(500) DEFAULT NULL COMMENT '参考文献',
     */
    public static final String TYPE_MARKER = "T06";
    /**
     * marker_position表
     * `marker_name` varchar(100) DEFAULT NULL COMMENT 'marker名',
     * `chr` varchar(20) DEFAULT NULL COMMENT '染色体编号',
     * `marker_type` varchar(100) DEFAULT NULL COMMENT 'marker类型',
     * `version` varchar(50) DEFAULT NULL COMMENT '基因组版本号', -----取自文件名
     * `start_pos` int(11) DEFAULT NULL COMMENT 'Marker序列在染色体上的起始位置',
     * `end_pos` int(11) DEFAULT NULL COMMENT 'Marker序列在染色体上的终止位置',
     */
    public static final String TYPE_MARKER_POSITION = "T07";
    /**
     * advanceinfo表
     * `qtl_name` varchar(100) DEFAULT NULL,---取自文件名
     * `plant_trait_ontology` text COMMENT '文件第一行',
     * `plant_ontology` text COMMENT '文件第二行',
     * `gene_ontology` text COMMENT '文件第三行',
     * `other_related_qtls` text COMMENT '文件第四行',
     * `other_names_qtl` text COMMENT '文件第五行',
     */
    public static final String TYPE_ADVANCEINFO = "T08";
    /**
     * associatedgenes表
     * `qtl_name` varchar(100) DEFAULT NULL COMMENT 'qtl名',---文件名
     * `version` varchar(40) DEFAULT NULL COMMENT '版本',
     * `associated_genes` text COMMENT '基因列表',
     */
    public static final String TYPE_ASSOCIATEDGENES = "T09";
    /**
     * soybean表
     * `category_name` varchar(45) DEFAULT NULL COMMENT '分类名称',  ---分类名
     * `category` varchar(45) DEFAULT NULL COMMENT '分类说明（根\\茎\\叶片\\花豆荚\\豆子）',---中文意思
     * `list_name` text,
     * `qtl_name` text,
     */
    public static final String TYPE_SOYBEAN = "T10";
    /*
    * mrna_gens表
    *`gene` varchar(100) DEFAULT NULL COMMENT '基因',
    *`geneName` varchar(100) DEFAULT NULL COMMENT '基因名称',
    *`functions` text COMMENT '基因功能',
    * */
    public static final String TYPE_MRNA_GENS = "T11";

    /*
    * study表
    *
    *`sraStudy` varchar(30) DEFAULT NULL COMMENT '研究课题信息检索号',
    *`study` varchar(500) DEFAULT NULL COMMENT '研究课题',
    *`sampleName` varchar(50) DEFAULT NULL COMMENT '样本名称',
    *`isExpression` int(11) DEFAULT NULL COMMENT '是否表达（或者差异）',
    *`sampleRun` varchar(50) DEFAULT NULL COMMENT '测序数据检索号',
    *`tissue` varchar(100) DEFAULT NULL COMMENT '组织',
    *`tissueForClassification` varchar(100) DEFAULT NULL COMMENT '组织分类',
    *`preservation` varchar(50) DEFAULT NULL,
    *`treat` varchar(500) DEFAULT NULL COMMENT '处理方式',
    *`stage` varchar(100) DEFAULT NULL COMMENT '生长阶段',
    *`geneType` varchar(100) DEFAULT NULL COMMENT '基因型',
    *`phenoType` varchar(100) DEFAULT NULL COMMENT '表型',
    *`environment` varchar(500) DEFAULT NULL COMMENT '生长环境',
    *`geoLoc` varchar(200) DEFAULT NULL,
    *`ecoType` varchar(50) DEFAULT NULL,
    *`collectionDate` varchar(50) DEFAULT NULL,
    *`coordinates` varchar(50) DEFAULT NULL,
    *`ccultivar` varchar(100) DEFAULT NULL COMMENT '品种',
    *`scientificName` varchar(200) DEFAULT NULL COMMENT '拉丁文名称',
    *`pedigree` varchar(50) DEFAULT NULL,
    *`reference` varchar(500) DEFAULT NULL COMMENT '参考文献',
    *`institution` varchar(300) DEFAULT NULL COMMENT '研究机构',
    *`submissionTime` varchar(50) DEFAULT NULL,
    *`instrument` varchar(100) DEFAULT NULL,
    *`libraryStrategy` varchar(100) DEFAULT NULL,
    *`librarySource` varchar(100) DEFAULT NULL,
    *`libraryLayout` varchar(100) DEFAULT NULL COMMENT '建库方式',
    *`insertSize` varchar(20) DEFAULT NULL,
    *`readLength` varchar(20) DEFAULT NULL,
    *`spots` int(20) DEFAULT NULL,
    *`experiment` varchar(100) DEFAULT NULL COMMENT '实验信息检索号',
    *`links` varchar(80) DEFAULT NULL COMMENT '链接地址',
    * */
    public static final String TYPE_STUDY = "T12";

    /**
     * mrna总表：fpkm
     */
    public static final String TYPE_FPKM = "T13";

    /**
     * mrna:表达基因表
     */
    public static final String TYPE_EXPRESSION = "T14";

    /**
     * mrna:差异基因表
     */
    public static final String TYPE_COMPARISON = "T15";

}
