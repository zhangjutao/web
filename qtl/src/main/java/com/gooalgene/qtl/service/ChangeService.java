package com.gooalgene.qtl.service;

import com.gooalgene.common.dao.MrnaGensDao;
import com.gooalgene.common.dao.StudyDao;
import com.gooalgene.entity.*;
import com.gooalgene.qtl.dao.*;
import com.gooalgene.utils.FileType;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@Service
public class ChangeService {

    @Autowired
    private ChrlgDao chrlgDao;

    @Autowired
    private MarkerPositionDao markerPositionDao;

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    @Autowired
    private AdvanceInfoDao advanceinfoDao;

    @Autowired
    private QtlDao qtlDao;

    @Autowired
    private QtlrefDao qtlrefDao;

    @Autowired
    private TraitCategoryDao traitCategoryDao;

    @Autowired
    private TraitListDao traitListDao;

    @Autowired
    private SoybeanDao soybeanDao;

    @Autowired
    private MarkerDao markerDao;

    @Autowired
    private MrnaGensDao mrnaGensDao;

    @Autowired
    private StudyDao studyDao;


    /**
     * chrlg表
     * `chr` varchar(45) DEFAULT NULL COMMENT '染色体编号',
     * `lg` varchar(45) DEFAULT NULL COMMENT '连锁群编号',
     * `version` varchar(40) DEFAULT NULL COMMENT '对应染色体版本',
     * `bps` int(11) DEFAULT NULL COMMENT '染色体物理距离总长度(碱基个数)',
     * `cMs` float DEFAULT NULL COMMENT '染色体遗传距离总长度(cMs)',
     * `cm_bp` int(11) DEFAULT NULL COMMENT '每单位遗传距离(cM)的碱基个数'
     */
    public int insertChrlg(List<String> list, String mes) {
        int num = 0;
        Chrlg chrlg = null;
        List<Chrlg> toInsert = new ArrayList<Chrlg>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 6) {
                chrlg = new Chrlg();
                num++;
                chrlg.setChr(strings[0]);
                chrlg.setLg(strings[1]);
                chrlg.setVersion(strings[2]);
                chrlg.setBps(Integer.valueOf(strings[3]));
                chrlg.setcMs(Float.valueOf(strings[4]));
                chrlg.setCmBp(Integer.valueOf(strings[5]));
                toInsert.add(chrlg);
//                chrlgDao.insert(chrlg);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += chrlgDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Chrlg>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += chrlgDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

    public void insertChrlg_Length() {
        String path = "E:\\古奥科技资料\\genome\\Glycine_max.V1.0.23.dna.genome\\Glycine_max.V1.0.23.dna.genome-length.csv";
        List<String> list = null;
        try {
            list = FileUtils.readLines(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 0;
        Chrlg chrlg = null;
        for (String str : list) {
            String[] strings = str.split("\t");
            if (strings.length == 3) {
                chrlg = new Chrlg();
                num++;
                String id = strings[0];
                chrlg.setChr(id);
                if (id.equals("1")) {
                    chrlg.setLg("D1a");
                } else if (id.equals("2")) {
                    chrlg.setLg("D1b");
                } else if (id.equals("3")) {
                    chrlg.setLg("N");
                } else if (id.equals("4")) {
                    chrlg.setLg("C1");
                } else if (id.equals("5")) {
                    chrlg.setLg("A1");
                } else if (id.equals("6")) {
                    chrlg.setLg("C2");
                } else if (id.equals("7")) {
                    chrlg.setLg("M");
                } else if (id.equals("8")) {
                    chrlg.setLg("A2");
                } else if (id.equals("9")) {
                    chrlg.setLg("K");
                } else if (id.equals("10")) {
                    chrlg.setLg("O");
                } else if (id.equals("11")) {
                    chrlg.setLg("B1");
                } else if (id.equals("12")) {
                    chrlg.setLg("H");
                } else if (id.equals("13")) {
                    chrlg.setLg("F");
                } else if (id.equals("14")) {
                    chrlg.setLg("B2");
                } else if (id.equals("15")) {
                    chrlg.setLg("E");
                } else if (id.equals("16")) {
                    chrlg.setLg("J");
                } else if (id.equals("17")) {
                    chrlg.setLg("D2");
                } else if (id.equals("18")) {
                    chrlg.setLg("G");
                } else if (id.equals("19")) {
                    chrlg.setLg("L");
                } else if (id.equals("20")) {
                    chrlg.setLg("I");
                } else {
                }
                chrlg.setVersion("Glycine_max.V1.0.23.dna.genome");
                chrlg.setBps(Integer.valueOf(strings[1]));
                chrlg.setcMs(Float.valueOf(strings[2]));
                chrlgDao.insert(chrlg);
            }
        }
        System.out.println("list num:" + list.size() + ",right num:" + num);
    }

    /**
     * marker_position表
     * `marker_name` varchar(100) DEFAULT NULL COMMENT 'marker名',
     * `chr` varchar(20) DEFAULT NULL COMMENT '染色体编号',
     * `marker_type` varchar(100) DEFAULT NULL COMMENT 'marker类型',
     * `version` varchar(50) DEFAULT NULL COMMENT '基因组版本号', -----取自文件名
     * `start_pos` int(11) DEFAULT NULL COMMENT 'Marker序列在染色体上的起始位置',
     * `end_pos` int(11) DEFAULT NULL COMMENT 'Marker序列在染色体上的终止位置',
     */
    public int insertMarkerPosition(List<String> list, String mes) {
//        String path = "E:\\古奥科技资料\\genome\\Glycine_max.V1.0.23.dna.genome\\Glycine_max.V1.0.23.dna.genome-blast.csv";
//        List<String> list = null;
//        try {
//            list = FileUtils.readLines(new File(path));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int num = 0;
        List<MarkerPosition> toInsert = new ArrayList<MarkerPosition>();
        int total = 0;
        MarkerPosition markerPosition = null;
        for (int i = 1; i < list.size(); i++) {
            String str = list.get(i);
            String[] strings = str.split("\t");
            if (strings.length == 6) {
                markerPosition = new MarkerPosition();
                num++;
                markerPosition.setMarkerName(strings[0]);
                markerPosition.setChr(strings[1]);
                markerPosition.setMarkerType(strings[2]);
                markerPosition.setVersion(strings[3]);
                markerPosition.setStartPos(Integer.valueOf(strings[4]));
                markerPosition.setEndPos(Integer.valueOf(strings[5]));
                toInsert.add(markerPosition);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += markerPositionDao.insertBatch(toInsert);
                    toInsert = new ArrayList<MarkerPosition>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += markerPositionDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;

    }

    /**
     * associatedgenes表
     * `qtl_name` varchar(100) DEFAULT NULL COMMENT 'qtl名',---文件名
     * `version` varchar(40) DEFAULT NULL COMMENT '版本',
     * `associated_genes` text COMMENT '基因列表',
     */
    public int insertAssocitedgenes(List<String> list, String mes) {
//        String path0 = "E:\\古奥科技资料\\genome\\Glycine_max.V1.0.23.dna.genome\\Glycine_max.V1.0.23.dna.genome-genes.csv";
//        List<String> list = null;
//        try {
//            list = FileUtils.readLines(new File(path0));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //此处应该对文件记录去重
        int num = 0;
        List<Associatedgenes> toInsert = new ArrayList<Associatedgenes>();
        int total = 0;
        Associatedgenes associatedgenes = null;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length >= 2) {
                associatedgenes = new Associatedgenes();
                num++;
                String qlt_name = strings[0];
                String version = strings[1];
                String genes = "";
                if (strings.length == 3) {
                    genes = strings[2];
                }
                associatedgenes.setQtlName(qlt_name);
                associatedgenes.setAssociatedGenes(genes);
                associatedgenes.setVersion(version);
//              associatedgenesDao.insert(associatedgenes);
                toInsert.add(associatedgenes);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += associatedgenesDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Associatedgenes>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {//500条记录
            total += associatedgenesDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

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
    public int insertQTL(List<String> list, String mes) {
//        String path0 = "E:\\古奥科技资料\\data_main.csv";
//        List<String> list = null;
//        try {
//            list = FileUtils.readLines(new File(path0));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int num = 0;
        int renum = 0;
        Qtl qtl = null;
        Map<String, Chrlg> chrlgMap = getAllchrlg();
//      Map<String, Associatedgenes> associatedgenesMap = getAllGenes();
//      System.out.println("All chrlg:" + chrlgMap.size() + ", all genes:" + associatedgenesMap.size());
        List<Qtl> toInsert = new ArrayList<Qtl>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            String qlt_name = strings[1];
            if ((strings.length == 17) && (qtlDao.findQtlByNameNum(qlt_name) == 0)) {
                qtl = new Qtl();
                num++;
                String id = strings[0];
                if (id.equals("id")) {
                    continue;//首行为标题行
                }
                qtl.setQtlName(qlt_name);
                String trait = strings[2];
                qtl.setTrait(trait);
                String type = strings[3];
                qtl.setType(type);
                String lg = strings[4];
                String chr = strings[5];
                String key = chr + "-" + lg;
                if (chrlgMap.containsKey(key)) {//--此处可以确定对应版本信息
                    Chrlg chrlg = chrlgMap.get(key);
                    qtl.setChrlgId(Integer.valueOf(chrlg.getId()));
                    qtl.setVersion(chrlg.getVersion());
                    //确认版本信息
//                    String genesKey = qlt_name + "-" + chrlg.getVersion();
//                    if (associatedgenesMap.containsKey(genesKey)) {
//                        Associatedgenes associatedgenes = associatedgenesMap.get(genesKey);
//                        qtl.setAssociatedGenesId(Integer.valueOf(associatedgenes.getId()));
//                    }
                } else {
                    //不包含，字段为空。
                }
                String marker1 = strings[6];
                qtl.setMarker1(marker1);
                String marker2 = strings[7];
                qtl.setMarker2(marker2);
                String genes = strings[8];//此字段取genes个数（查数据库）
                String method = strings[9];
                qtl.setMethod(method);
                try {
                    String genome_start = strings[10];
                    qtl.setGenomeStart(Float.valueOf(genome_start));
                } catch (NumberFormatException e) {

                }
                try {
                    String genome_end = strings[11];
                    qtl.setGenomeEnd(Float.valueOf(genome_end));
                } catch (NumberFormatException e) {

                }
                try {
                    String lod = strings[12];
                    qtl.setLod(Float.valueOf(lod));
                } catch (NumberFormatException e) {

                }
                String parent1 = strings[13];
                qtl.setParent1(parent1);
                String parent2 = strings[14];
                qtl.setParent2(parent2);
                String ref = strings[15];
                qtl.setRef(ref);
                String author = strings[16];
                qtl.setAuthor(author);
                qtl.setCreatetime(new Date());
                toInsert.add(qtl);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += qtlDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Qtl>();//重新添加一批数据
                }
            } else {
                renum++;
                continue;
            }
        }
        if (!toInsert.isEmpty()) {
            total += qtlDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",repeat num:" + renum + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",重复行数:" + num + ",入库数：" + total;
        return total;
    }

    /*
    * mrna_gens表
    *`gene` varchar(100) DEFAULT NULL COMMENT '基因',
    *`geneName` varchar(100) DEFAULT NULL COMMENT '基因名称',
    *`functions` text COMMENT '基因功能',
    * */
    public int insertMrnaGens(List<String> list, String mes) {
        int num = 0;
        MrnaGens mrnaGens = null;
        List<MrnaGens> toInsert = new ArrayList<MrnaGens>();
        List<MrnaGens> toUpdate = new ArrayList<MrnaGens>();
        int total = 0;
        int update = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 3) {
                mrnaGens = new MrnaGens();
                num++;
                String gene = strings[0];
                mrnaGens.setGene(gene);
                mrnaGens.setGeneName(strings[1]);
                mrnaGens.setFunctions(strings[2]);
                if (mrnaGensDao.findMrnaGensInfoByGene(gene) > 0){
                    toUpdate.add(mrnaGens);
                } else {
                    toInsert.add(mrnaGens);
                }
                if (toInsert.size() == 1000) {//1000条记录 提交一次
                    total += mrnaGensDao.insertBatch(toInsert); //replace--先删除重复关键字的记录，再添加新的记录，故影响的行数为2，此处计数就不准了。total可以弃用。
                    toInsert = new ArrayList<MrnaGens>();//重新添加一批数据
                }
                if (toUpdate.size() == 500) {
                    update += mrnaGensDao.updateBatch(toUpdate);
                    toUpdate = new ArrayList<MrnaGens>();
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += mrnaGensDao.insertBatch(toInsert);
        }
        if (!toUpdate.isEmpty()) {
            update += mrnaGensDao.updateBatch(toUpdate);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + (num - update) + ",toUpdate num:" + update);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + (num - update) + ",更新数：" + update;
        return total;
    }

    public int insertStudy(List<String> list, String mes) {
        int num = 0;
        Study study = null;
        List<Study> toInsert = new ArrayList<Study>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 32) {
                study = new Study();
                num++;
                study.setSampleRun(strings[0]);
                study.setSampleName(strings[1]);
                if ("expression".equals(strings[2])) {
                    study.setIsExpression(1);
                } else {
                    study.setIsExpression(0);
                }
                study.setSraStudy(strings[3]);
                study.setStudy(strings[4]);
                study.setTissueForClassification(strings[5]);
                study.setTissue(strings[6]);
                study.setPreservation(strings[7]);
                study.setTreat(strings[8]);
                study.setStage(strings[9]);
                study.setGeneType(strings[10]);
                study.setPhenoType(strings[11]);
                study.setEnvironment(strings[12]);
                study.setGeoLoc(strings[13]);
                study.setEcoType(strings[14]);
                study.setCollectionDate(strings[15]);
                study.setCoordinates(strings[16]);
                study.setCcultivar(strings[17]);
                study.setScientificName(strings[18]);
                study.setPedigree(strings[19]);
                study.setReference(strings[20]);
                study.setInstitution(strings[21]);
                study.setSubmissionTime(strings[22]);
                study.setInstrument(strings[23]);
                study.setLibraryStrategy(strings[24]);
                study.setLibrarySource(strings[25]);
                study.setLibraryLayout(strings[26]);
                study.setInsertSize(strings[27]);
                study.setReadLength(strings[28]);
                study.setSpots(Integer.valueOf(strings[29]));
                study.setExperiment(strings[30]);
                study.setLinks(strings[31]);
                toInsert.add(study);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += studyDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Study>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += studyDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

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
    public int insertMarker(List<String> list, String mes) {
        int num = 0;
        Marker marker = null;
        List<Marker> toInsert = new ArrayList<Marker>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            int len = strings.length;
            if (len >= 4) {
                if (strings.length == 7) {
                    marker = new Marker();
                    num++;
                    String marker_name = strings[0];
                    String marker_type = strings[1];
                    String marker_lg = strings[2];
                    String position = strings[3];
                    if (!marker_name.equals("") && !marker_type.equals("") && !marker_lg.equals("") && !position.equals("")) {
                        marker.setMarkerName(marker_name);
                        marker.setMarkerType(marker_type);
                        marker.setMarkerLg(marker_lg);
                        marker.setPosition(Float.valueOf(position));
                        String amplification_info = strings[4];
                        marker.setAmplificationInfo(amplification_info);
                        String provider = strings[5];
                        marker.setProvider(provider);
                        String ref = strings[6];
                        marker.setRef(ref);
                        toInsert.add(marker);
                    } else {
                        continue;
                    }
                    if (toInsert.size() == 500) {//500条记录 提交一次
                        total += markerDao.insertBatch(toInsert);
                        toInsert = new ArrayList<Marker>();//重新添加一批数据
                    }
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += markerDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

    /**
     * qtlref表
     * `title` varchar(500) DEFAULT NULL COMMENT '标题名',
     * `authors` varchar(500) DEFAULT NULL COMMENT '作者',
     * `source` varchar(500) DEFAULT NULL COMMENT '所发表的杂志信息',
     * `abstract` text COMMENT '文章摘要',
     * `pubmed` varchar(500) DEFAULT NULL COMMENT '链接',
     * `qtl_name` varchar(500) DEFAULT NULL COMMENT '对应qtl名',---取文件名
     */
    public int insertQTLREF(List<String> list, String mes) {
        int num = 0;
        Qtlref qtlref = null;
        List<Qtlref> toInsert = new ArrayList<Qtlref>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 6) {
                qtlref = new Qtlref();
                num++;
                qtlref.setTitle(strings[0]);
                qtlref.setAuthors(strings[1]);
                qtlref.setSource(strings[2]);
                qtlref.setSummary(strings[3]);
                qtlref.setPubmed(strings[4]);
                qtlref.setQtlName(strings[5]);
                toInsert.add(qtlref);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += qtlrefDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Qtlref>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += qtlrefDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

    /**
     * soybean表
     * `category_name` varchar(45) DEFAULT NULL COMMENT '分类名称',  ---分类名
     * `category` varchar(45) DEFAULT NULL COMMENT '分类说明（根\\茎\\叶片\\花豆荚\\豆子）',---中文意思
     * `list_name` text,
     * `qtl_name` text,
     */
    public int insertSoybean(List<String> list, String mes) {
        int num = 0;
        Soybean soybean = null;
        List<Soybean> toInsert = new ArrayList<Soybean>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 4) {
                soybean = new Soybean();
                num++;
                soybean.setCategoryName(strings[0]);
                soybean.setCategory(strings[1]);
                soybean.setListName(strings[2]);
                soybean.setQtlName(strings[3]);
                toInsert.add(soybean);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += soybeanDao.insertBatch(toInsert);
                    toInsert = new ArrayList<Soybean>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += soybeanDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

    /**
     * advanceinfo表
     * `qtl_name` varchar(100) DEFAULT NULL,---取自文件名
     * `plant_trait_ontology` text COMMENT '文件第一行',
     * `plant_ontology` text COMMENT '文件第二行',
     * `gene_ontology` text COMMENT '文件第三行',
     * `other_related_qtls` text COMMENT '文件第四行',
     * `other_names_qtl` text COMMENT '文件第五行',
     */
    public int insertAdvanceinfo(List<String> list, String mes) {
        int num = 0;
        AdvanceInfo advanceinfo = null;
        List<AdvanceInfo> toInsert = new ArrayList<AdvanceInfo>();
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length >= 2) {
                advanceinfo = new AdvanceInfo();
                num++;
                advanceinfo.setQtlName(strings[0]);
                advanceinfo.setPlantTraitOntology(strings[1]);
                advanceinfo.setPlantOntology(strings[2]);
                advanceinfo.setGeneOntology(strings[3]);
                advanceinfo.setOtherRelatedQtls(strings[4]);
                advanceinfo.setOtherNamesQtl(strings[5]);
                System.out.println("1111111");
                System.out.println(strings[1]);
                System.out.println(strings[2]);
                System.out.println(strings[3]);
                System.out.println(strings[4]);
                System.out.println(strings[5]);
                System.out.println("1111111");
                System.out.println(advanceinfo.toString());
                toInsert.add(advanceinfo);
                if (toInsert.size() == 500) {//500条记录 提交一次
                    total += advanceinfoDao.insertBatch(toInsert);
                    toInsert = new ArrayList<AdvanceInfo>();//重新添加一批数据
                }
            }
        }
        if (!toInsert.isEmpty()) {
            total += advanceinfoDao.insertBatch(toInsert);
        }
        System.out.println("list num:" + list.size() + ",right num:" + num + ",toInsert num:" + total);
        mes = "总行数：" + list.size() + ",正确行数:" + num + ",入库数：" + total;
        return total;
    }

    private Map<String, Chrlg> getAllchrlg() {
        Map<String, Chrlg> map = new HashMap<String, Chrlg>();
        List<Chrlg> list = chrlgDao.findList(new Chrlg());
        for (Chrlg chrlg : list) {
            map.put(chrlg.getChr() + "-" + chrlg.getLg(), chrlg);
        }
        return map;
    }

    private Map<String, Associatedgenes> getAllGenes() {
        Map<String, Associatedgenes> map = new HashMap<String, Associatedgenes>();
        List<Associatedgenes> list = associatedgenesDao.findList(new Associatedgenes());
        for (Associatedgenes associatedgenes : list) {
            map.put(associatedgenes.getQtlName() + "-" + associatedgenes.getVersion(), associatedgenes);
        }
        return map;
    }

    /**
     * trait_category
     * 大分类表
     * 文件内容为：分类名称\t中文说明\t别名（别名可能不提供）
     * 1 qtl_name` varchar(100) DEFAULT NULL COMMENT 'qtl大类',
     * 2 qtl_desc` varchar(45) DEFAULT NULL COMMENT '中文描述',
     * 3 qtl_othername` varchar(100) DEFAULT NULL COMMENT '别名(soybase上的给出缩写)',
     * <p/>
     * trait_list
     * 小分类表
     * 文件内容：大类名称\t小类名称
     * 4 trait_name` varchar(200) DEFAULT NULL,
     */
    public int insertTraitCategoryAndList(List<String> list, String mes) {
        int num = 0;
        TraitCategory traitCategory = null;
        TraitList traitList = null;
        int total = 0;
        for (int i = 1; i < list.size(); i++) {
            String string = list.get(i);
            String[] strings = string.split("\t");
            if (strings.length == 4) {
                traitCategory = new TraitCategory();
                traitCategory.setQtlName(strings[0]);
                traitCategory.setQtlDesc(strings[1]);
                traitCategory.setQtlOthername(strings[2]);
                if (null == traitCategoryDao.get(strings[0])) {
                    traitCategoryDao.add(traitCategory);
                    traitList = new TraitList();
                    TraitCategory traitCategoryN = traitCategoryDao.get(strings[0]);
                    traitList.setQtlId(Integer.valueOf(traitCategoryN.getId()));
                    traitList.setTraitName(strings[3]);
                    traitListDao.add(traitList);
                    num++;
                } else {
                    traitList = new TraitList();
                    TraitCategory traitCategoryN = traitCategoryDao.get(strings[0]);
                    traitList.setQtlId(Integer.valueOf(traitCategoryN.getId()));
                    traitList.setTraitName(strings[3]);
                    traitListDao.add(traitList);
                    num++;
                }
            }
        }
        System.out.println("list num:" + list.size() + ",right num:" + num);
        mes = "总行数：" + list.size() + ",正确行数:" + num;
        return num;
    }

    public void insertTraitList() {
        String path0 = "E:\\古奥科技资料\\数据+说明\\trait分类2.0\\trait_tree_list-2.0";
        File file = new File(path0);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.endsWith(".txt")) {
                    try {
                        String trait_name = fileName.replace(".txt", "");
                        List<String> strings = FileUtils.readLines(f);
                        TraitCategory t = traitCategoryDao.get(trait_name);
                        System.out.println("Trait:" + trait_name + "\t,size:" + strings.size() + "\t,id:" + t.getId());
                        if (t != null) {
                            for (String s : strings) {
                                TraitList traitList = new TraitList();
                                traitList.setQtlId(Integer.valueOf(t.getId()));
                                traitList.setTraitName(s);
                                traitListDao.add(traitList);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updateSoybean() {

        String path0 = "E:\\古奥科技资料\\数据+说明\\trait分类2.0\\点击图片list-2.0";
        File file = new File(path0);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String fileName = f.getName();
                if (fileName.endsWith(".txt")) {
                    try {
                        String trait_name = fileName.replace(".txt", "");
                        List<String> strings = FileUtils.readLines(f);
                        Soybean soybean = soybeanDao.get(trait_name);
                        System.out.println("Id:" + soybean.getId() + "\tTrait:" + trait_name + "\t,size:" + strings.size() + "\t" + getContent(strings));
                        if (soybean != null) {
                            soybean.setListName(getContent(strings));
                            soybeanDao.updateSoybean(soybean);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String getContent(List<String> list) {
        StringBuffer sb = new StringBuffer();
        int total = list.size();
        for (int i = 0; i < total; i++) {
            sb.append(list.get(i));
            if (i != (total - 1)) {
                sb.append("|");
            }
        }
        return sb.toString();
    }

    public void updateMarker() {
        String path0 = "E:\\古奥科技资料\\数据+说明\\marker_info.csv";
        File file = new File(path0);
        List<String> strings = null;
        try {
            int num = 0;
            int mum = 0;
            int j = 0;
            strings = FileUtils.readLines(file);
            Marker one = null;
            List<Marker> list = new ArrayList<Marker>();
            for (String s : strings) {
                String[] strings1 = s.split("\t");
                int len = strings1.length;
                if (len >= 4) {//前4位不能缺省,后三位可缺省
                    num++;
                    System.out.println(strings1[0] + "," + len);
                    if (len != 7) {
                        String markName = strings1[0];
                        String markType = strings1[1];
                        String markerLg = strings1[2];
                        String markerPosition = strings1[3];
                        if (!markName.equals("") && !markType.equals("") && !markerLg.equals("") && !markerPosition.equals("")) {
                            one = new Marker();
                            one.setMarkerName(markName);
                            one.setMarkerType(markType);
                            one.setMarkerLg(markerLg);
                            one.setPosition(Float.valueOf(markerPosition));
                            if (len == 5) {
                                one.setAmplificationInfo(strings1[4]);
                            }
                            if (len == 6) {
                                one.setAmplificationInfo(strings1[4]);
                                one.setProvider(strings1[5]);
                            }
                            mum++;
                            Marker m = markerDao.get(strings1[0]);
                            if (m == null) {
                                list.add(one);
                            }
                        } else {
                            j++;
                            System.out.println(s);
                        }
                    }
                }
            }
            markerDao.insertBatch(list);
            System.out.println("all:" + strings.size() + ",num:" + num + ",mum:" + mum + ",insert size:" + list.size() + ",j:" + j);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param list(传入的数据集合)
     * @param type(区分数据表)
     * @param mes(此内容主要用于出错时返回)
     * @return
     */
    public Boolean insertBatch(List<String> list, String type, String mes) {
        boolean flag = false;
        if (FileType.TYPE_TRAIT_CATEGORY.equals(type)) {
            int num = insertTraitCategoryAndList(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_ADVANCEINFO.equals(type)) {
            int num = insertAdvanceinfo(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_CHRLG.equals(type)) {
            int num = insertChrlg(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_ASSOCIATEDGENES.equals(type)) {
            int num = insertAssocitedgenes(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_MARKER.equals(type)) {
            int num = insertMarker(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_MARKER_POSITION.equals(type)) {
            int num = insertMarkerPosition(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_QTL.equals(type)) {
            int num = insertQTL(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_QTL_REF.equals(type)) {
            int num = insertQTLREF(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_SOYBEAN.equals(type)) {
            int num = insertSoybean(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_MRNA_GENS.equals(type)) {
            int num = insertMrnaGens(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else if (FileType.TYPE_STUDY.equals(type)) {
            int num = insertStudy(list, mes);
            if (num > 0) {
                flag = true;
            }
        } else {

        }
        return flag;
    }
}
