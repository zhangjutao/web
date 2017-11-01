package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.entity.*;
import com.gooalgene.qtl.dao.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@Service
public class QueryService {

    @Autowired
    private TraitCategoryDao traitCategoryDao;

    @Autowired
    private TraitListDao traitListDao;

    @Autowired
    private SoybeanDao soybeanDao;

    @Autowired
    private QtlDao qtlDao;

    @Autowired
    private QtlrefDao qtlrefDao;

    @Autowired
    private ChrlgDao chrlgDao;

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    @Autowired
    private AdvanceInfoDao advanceInfoDao;

    @Autowired
    private MarkerDao markerDao;

    @Autowired
    private MarkerPositionDao markerPositionDao;


    /**
     * 获取染色体编号和版本集合
     *
     * @return
     */
    public JSONArray queryChrAndVersion() {
        JSONArray result = new JSONArray();
        List<Chrlg> chrlgs = chrlgDao.findList(new Chrlg());
        Map<String, Set<String>> version_chr = new HashMap<String, Set<String>>();
        for (Chrlg chrlg : chrlgs) {
            String version = chrlg.getVersion();
            String chr = chrlg.getChr();
            if (version_chr.containsKey(version)) {
                version_chr.get(version).add(chr);
            } else {
                Set<String> chrs = new HashSet<String>();
                chrs.add(chr);
                version_chr.put(version, chrs);
            }
        }
        for (String s : version_chr.keySet()) {
            JSONObject one = new JSONObject();
            one.put("version", s);
            one.put("data", version_chr.get(s));
            result.add(one);
        }
        return result;
    }

    /**
     * 获取版本集合
     *
     * @return
     */
    public JSONArray queryVersions() {
        JSONArray result = new JSONArray();
        List<Chrlg> chrlgs = chrlgDao.findList(new Chrlg());
        Map<String, Set<String>> version_chr = new HashMap<String, Set<String>>();
        for (Chrlg chrlg : chrlgs) {
            String version = chrlg.getVersion();
            String chr = chrlg.getChr();
            if (version_chr.containsKey(version)) {
                version_chr.get(version).add(chr);
            } else {
                Set<String> chrs = new HashSet<String>();
                chrs.add(chr);
                version_chr.put(version, chrs);
            }
        }
        for (String s : version_chr.keySet()) {
            result.add(s);
        }
        return result;
    }

    public Map queryChrsByVersion(String version) {
        Chrlg chrlg = new Chrlg();
        chrlg.setVersion(version);
        List<Chrlg> list = chrlgDao.findList(chrlg);
        Map m = new HashMap();
        for (Chrlg c : list) {
            m.put(c.getLg(), c.getChr());
        }
        return m;
    }

    private Map lgAndMarkerlg() {
        Map m = new HashMap();
        m.put("D1a", "D1a(1)");
        m.put("D1b", "D1b(2)");
        m.put("N", "N(3)");
        m.put("C1", "C1(4)");
        m.put("A1", "A1(5)");
        m.put("C2", "C2(6)");
        m.put("M", "M(7)");
        m.put("A2", "A2(8)");
        m.put("K", "K(9)");
        m.put("O", "O(10)");
        m.put("B1", "B1(11)");
        m.put("H", "H(12)");
        m.put("F", "F(13)");
        m.put("B2", "B2(14)");
        m.put("E", "E(15)");
        m.put("J", "J(16)");
        m.put("D2", "D2(17)");
        m.put("G", "G(18)");
        m.put("L", "L(19)");
        m.put("I", "I(20)");
        return m;
    }

    public JSONArray queryAll() {
        JSONArray result = new JSONArray();
        List<TraitCategory> traitCategories = traitCategoryDao.findList(new TraitCategory());
        List<TraitList> one = null;
        for (TraitCategory t : traitCategories) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            one = traitListDao.getTraitListsByQtlId(t.getId());
            for (TraitList traitList : one) {
                jsonArray.add(traitList.getTraitName());
            }
//            System.out.println(t.getQtlDesc() + "\t" + one.size());
            jsonObject.put("name", t.getQtlName());
            jsonObject.put("desc", t.getQtlDesc());
            jsonObject.put("data", jsonArray);
            result.add(jsonObject);
        }
        return result;
    }

    public JSONObject queryBySoybeanName(String name) {
        JSONObject result = new JSONObject();
        Soybean soybean = soybeanDao.get(name);
        result.put("name", name);
        if (soybean != null) {
            result.put("desc", soybean.getCategory());
            result.put("list", Arrays.asList(soybean.getListName().split("\\|")));
            result.put("qtls", Arrays.asList(soybean.getQtlName().split("\\|")));
        }
        return result;
    }

    /**
     * 获取染色体编号集合
     *
     * @return
     */
    public JSONArray queryChrs(String version) {
        JSONArray result = new JSONArray();
        Chrlg c = new Chrlg();
        c.setVersion(version);
        List<Chrlg> chrlgs = chrlgDao.findList(c);
        for (Chrlg chrlg : chrlgs) {
            String chr = chrlg.getChr();
            result.add(chr);
        }
        return result;
    }

    /**
     * 获取染色体LG集合
     *
     * @return
     */
    public JSONArray queryLgs() {
        JSONArray result = new JSONArray();
        List<Chrlg> chrlgs = chrlgDao.findList(new Chrlg());
        for (Chrlg chrlg : chrlgs) {
            String lg = chrlg.getLg();
            result.add(lg);
        }
        return result;
    }

    public Map qtlSearchbyKeywords(String version, String type, String keywords, Page<Qtl> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        Map result = new HashMap();
        result.put("type", type);
        result.put("keywords", keywords == null ? "" : keywords);
        result.put("condition", "{}");
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        result.put("chrs", queryChrs(version));
        result.put("lgs", queryLgs());
        JSONArray data = new JSONArray();
        Qtl qtl = new Qtl();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setKeywords(keywords);
            }//空白查询所有
        } else if ("Trait".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setTrait(keywords);
            }
        } else if ("QTL Name".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setQtlName(keywords);
            }
        } else if ("marker".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setMarker(keywords);
            }
        } else if ("parent".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setParent(keywords);
            }
        } else if ("reference".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setRef(keywords);
            }
        } else {
            result.put("total", page.getCount());
            result.put("data", data);
            return result;
        }
        qtl.setVersion(version);//匹配版本信息
        qtl.setPage(page);
        List<Map> list = null;
        List<Qtl> list1 = null;
//        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
//            list = qtlDao.findByTypeAll(qtl);
//            list1 = qtlDao.findByTypeAllList(qtl);
//        } else {//其他的字段查询是对应的and关联
        list = qtlDao.findByCondition(qtl);
        list1 = qtlDao.findList(qtl);
//        }
        Map lgAndMarkerlg = lgAndMarkerlg();
        for (Map m : list) {
            String qtlName = (String) m.get("qtlName");
            String genes = null;
            Associatedgenes associatedgenes = associatedgenesDao.getByNameAndVersion(qtlName, version);
            if (associatedgenes != null) {
                genes = associatedgenes.getAssociatedGenes();
            }
            String lg = (String) m.get("lg");
            if (lgAndMarkerlg.containsKey(lg)) {
                m.put("markerlg", lgAndMarkerlg.get(lg));
            }
            if (version.equals("Gmax_275_v2.0") && genes != null) {
                genes = genes.replaceAll("g", "G");
            }
            m.put("genes", genes == null ? "" : genes);
            m.put("genesNum", genes == null ? 0 : genes.split(",").length);
            data.add(m);
        }
        page.setList(list1);
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    public Map qtlDetailByName(String name, String version) {
        Map result = new HashMap();
        result.put("name", name);
        result.put("version", version);
        result.put("basicInfo", getQlt(name, version));
        result.put("referenceInfo", getReferenceInfoByQtlName(name));
        result.put("advanceInfo", getAdvanceInfoByQtlName(name));
        return result;
    }

    /**
     * 获取qtl详情页基本信息
     *
     * @param name
     * @param version
     * @return
     */
    public JSONObject getQlt(String name, String version) {
        JSONObject qtlObject = new JSONObject();
        qtlObject.put("qtlname", "");
        qtlObject.put("type", "");
        qtlObject.put("trait", "");
        qtlObject.put("marker1", "");
        qtlObject.put("marker2", "");
        JSONObject gens = new JSONObject();
        gens.put("size", 0);
        gens.put("data", "[]");
        qtlObject.put("genes", gens);
        qtlObject.put("lg", "");
        qtlObject.put("chr", "");
        qtlObject.put("method", "");
        qtlObject.put("lod", "");
        qtlObject.put("parent1", "");
        qtlObject.put("parent2", "");
        qtlObject.put("geneStart", "");
        qtlObject.put("geneEnd", "");
        Qtl qtl = qtlDao.getByQtlNameAndVersion(name, version);
        if (qtl != null) {
            qtlObject.put("qtlname", qtl.getQtlName());
            qtlObject.put("type", qtl.getType());
            qtlObject.put("trait", qtl.getTrait());
            qtlObject.put("marker1", qtl.getMarker1());
            qtlObject.put("marker2", qtl.getMarker2());
            if (qtl.getChrlgId() != null) {
                Map lgAndMarkerlg = lgAndMarkerlg();
                int chrlgId = qtl.getChrlgId();
                Chrlg chrlg = chrlgDao.get(chrlgId + "");
                qtlObject.put("lg", chrlg.getLg());
                qtlObject.put("chr", chrlg.getChr());
                qtlObject.put("markerlg", lgAndMarkerlg.get(chrlg.getLg()));
                Associatedgenes ass = associatedgenesDao.getByNameAndVersion(qtl.getQtlName(), version);
                if (ass != null) {
                    gens = new JSONObject();
                    String genes = ass.getAssociatedGenes();
                    if (genes != null) {
                        if (version.equals("Gmax_275_v2.0")) {
                            genes = genes.replaceAll("g", "G");
                        }
                        String[] ss = genes.split(",");
                        gens.put("size", ss.length);
                        gens.put("data", genes.split(","));
                    } else {
                        gens.put("size", 0);
                        gens.put("data", "");
                    }
                    qtlObject.put("genes", gens);
                }
            } else {
                qtlObject.put("lg", "");
                qtlObject.put("chr", "");
            }
            qtlObject.put("method", qtl.getMethod());
            qtlObject.put("lod", qtl.getLod());
            qtlObject.put("parent1", qtl.getParent1());
            qtlObject.put("parent2", qtl.getParent2());
            qtlObject.put("geneStart", qtl.getGenomeStart());
            qtlObject.put("geneEnd", qtl.getGenomeEnd());
        }
        return qtlObject;
    }

    /**
     * 获取qtl详情页引用信息
     *
     * @param name
     * @return
     */
    public JSONObject getReferenceInfoByQtlName(String name) {
        JSONObject refferenceInfo = new JSONObject();
        refferenceInfo.put("authors", "");
        refferenceInfo.put("title", "");
        refferenceInfo.put("source", "");
        refferenceInfo.put("pubmed", "");
        refferenceInfo.put("abs", "");
        Qtlref qtlref = qtlrefDao.get(name);
        if (qtlref != null) {
            refferenceInfo.put("authors", qtlref.getAuthors());
            refferenceInfo.put("title", qtlref.getTitle());
            refferenceInfo.put("source", qtlref.getSource());
            refferenceInfo.put("pubmed", qtlref.getPubmed());
            refferenceInfo.put("abs", qtlref.getSummary());
        }
        return refferenceInfo;
    }

    /**
     * 获取qtl详情页高级信息
     *
     * @param name
     * @return
     */
    public JSONObject getAdvanceInfoByQtlName(String name) {
        JSONObject advanceInfo = new JSONObject();
        JSONObject one = new JSONObject();
        one.put("TO", "");
        one.put("webUrl", "");
        advanceInfo.put("plantTraitOntology", one);
        one = new JSONObject();
        one.put("PO", "");
        one.put("webUrl", "");
        advanceInfo.put("plantOntology", one);
        advanceInfo.put("geneOntology", "");
        one = new JSONObject();
        one.put("qtls", "");
        advanceInfo.put("otherRelatedQtls", one);
        one = new JSONObject();
        one.put("names", "");
        advanceInfo.put("otherNamesQtl", one);
        AdvanceInfo adv = advanceInfoDao.get(name);
        if (adv != null) {
            String plantTraitOntology = adv.getPlantTraitOntology();
            String[] plTraiOnto = plantTraitOntology.split("\t");
            if (plTraiOnto.length == 3) {
                String to = plTraiOnto[1];
                String url = plTraiOnto[2];
                one.put("TO", to);
                one.put("webUrl", url);
            }
            advanceInfo.put("plantTraitOntology", one);
            String plantOntology = adv.getPlantOntology();
            String[] plOnto = plantOntology.split("\t");
            one = new JSONObject();
            if (plOnto.length == 3) {
                String po = plOnto[1];
                String url = plOnto[2];
                one.put("PO", po);
                one.put("webUrl", url);
            }
            advanceInfo.put("plantOntology", one);
            advanceInfo.put("geneOntology", adv.getGeneOntology());
            String otherRelatedQtls = adv.getOtherRelatedQtls();
            String[] other = otherRelatedQtls.split("\t");
            one = new JSONObject();
            if (other.length == 2) {
                if (!name.contains(",")) {//qtlname自己不包含逗号
                    String[] qtls = other[1].split(",");
                    Set<String> result = new HashSet<String>();//发现数据库数据存在重复，此处去重
                    for (String s : qtls) {
                        if (qtlDao.findQtlByNameNum(s) > 0) {
                            result.add(s);
                        }
                    }
                    one.put("qtls", result);
                } else {
                    //Other related QTL's	Root length, primary 2-1,Root length, primary 2-2
                    String prefix = name.split(",")[0];
                    String[] qtls = other[1].split(",");
                    Set<String> result = new HashSet<String>();//发现数据库数据存在重复，此处去重
                    for (String s : qtls) {
                        if (!s.equals(prefix)) {
                            String a = prefix + "," + s;
                            if (qtlDao.findQtlByNameNum(a) > 0) {
                                result.add(a);
                            }
                        }
                    }
                    one.put("qtls", result);
                }
            }
            advanceInfo.put("otherRelatedQtls", one);
            String otherNames = adv.getOtherNamesQtl();
            String[] otherN = otherNames.split("\t");
            one = new JSONObject();
            if (otherN.length == 2) {
                String names = otherN[1];
                one.put("names", names);
            }
            advanceInfo.put("otherNamesQtl", one);
        }
        return advanceInfo;
    }


    public JSONArray getMarkerLeftData(String chr, String version) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(chr) || StringUtils.isBlank(version)) {
            return jsonArray;
        }
        if (chr.startsWith("0")) {//针对版本Glycine_max.V1.0.23.dna.genome 前10个以0开头的数字：01，02，03。。。
            chr = chr.substring(1);
        }
        if (chr.startsWith("Chr")) {
            version = "Gmax_275_v2.0";
        } else {
            version = "Glycine_max.V1.0.23.dna.genome";
        }
        Chrlg chrlg = chrlgDao.selectByChrAndVersion(chr, version);
        if (chrlg != null) {
            List<String> markers = getMarkersFromQtlByLg(chrlg.getLg());

            MarkerPosition markerPosition = new MarkerPosition();
            markerPosition.setChr(chr);
            markerPosition.setVersion(version);
            List<MarkerPosition> list = markerPositionDao.findList(markerPosition);

            for (MarkerPosition m : list) {
                JSONArray one = new JSONArray();
                String markerName = m.getMarkerName();
                if (markers.contains(markerName)) {//只有出现过的才返回给前端
                    one.add(m.getMarkerName());
                    one.add(m.getChr());
                    one.add(m.getMarkerType());
                    one.add(m.getStartPos());
                    one.add(m.getEndPos());
                    jsonArray.add(one);
                }
            }
        }
        return jsonArray;
    }

    public JSONArray getMarkerLeftDataNotIn(String chr, String version) {
        JSONArray jsonArray = new JSONArray();
        if (StringUtils.isBlank(chr) || StringUtils.isBlank(version)) {
            return jsonArray;
        }

        Chrlg chrlg = chrlgDao.selectByChrAndVersion(chr, version);
        List<String> markers = getMarkersFromQtlByLg(chrlg.getLg());

        MarkerPosition markerPosition = new MarkerPosition();
        markerPosition.setChr(chr);
        markerPosition.setVersion(version);
        List<MarkerPosition> list = markerPositionDao.findList(markerPosition);

        for (MarkerPosition m : list) {
            JSONArray one = new JSONArray();
            String markerName = m.getMarkerName();
            if (!markers.contains(markerName)) {//查询不出现的数据
                one.add(m.getMarkerName());
                one.add(m.getChr());
                one.add(m.getMarkerType());
                one.add(m.getStartPos());
                one.add(m.getEndPos());
                jsonArray.add(one);
            }
        }
        return jsonArray;
    }

    public JSONArray getMarkerRightData(String markerlg) {
        JSONArray jsonArray = new JSONArray();

        if (StringUtils.isBlank(markerlg)) {
            return jsonArray;
        }

        String lg = markerlg.substring(0, markerlg.indexOf("("));
        List<String> markers = getMarkersFromQtlByLg(lg);

        Marker marker = new Marker();
        marker.setMarkerLg(markerlg);
        List<Marker> list = markerDao.findList(marker);


        for (Marker m : list) {
            JSONArray one = new JSONArray();
            String markerName = m.getMarkerName();
            if (markers.contains(markerName)) {//只有在对应的qtl里面出现过的才发送给前端
                one.add(m.getMarkerName());
                one.add(m.getMarkerType());
                one.add(m.getMarkerLg());
                one.add(m.getPosition());
                one.add(m.getAmplificationInfo());
                one.add(m.getProvider());
                one.add(m.getRef());
                jsonArray.add(one);
            }
        }
        return jsonArray;
    }

    public JSONArray getMarkerRightDataNotIn(String markerlg) {
        JSONArray jsonArray = new JSONArray();

        if (StringUtils.isBlank(markerlg)) {
            return jsonArray;
        }

        String lg = markerlg.substring(0, markerlg.indexOf("("));
        List<String> markers = getMarkersFromQtlByLg(lg);

        Marker marker = new Marker();
        marker.setMarkerLg(markerlg);
        List<Marker> list = markerDao.findList(marker);

        for (Marker m : list) {
            JSONArray one = new JSONArray();
            String markerName = m.getMarkerName();
            if (!markers.contains(markerName)) {//查询没出现的数据
                one.add(m.getMarkerName());
                one.add(m.getMarkerType());
                one.add(m.getMarkerLg());
                one.add(m.getPosition());
                one.add(m.getAmplificationInfo());
                one.add(m.getProvider());
                one.add(m.getRef());
                jsonArray.add(one);
            }
        }
        return jsonArray;
    }

    public List<String> getMarkersFromQtlByLg(String lg) {
        List<String> markers = new ArrayList<String>();//该染色体对应qtl中出现的marker集合
        if (StringUtils.isBlank(lg)) {
            return markers;
        }
        List<Map> list = qtlDao.queryQtlByLg(lg);
        for (Map m : list) {
            String m1 = (String) m.get("marker1");
            String m2 = (String) m.get("marker2");
            if (StringUtils.isNotBlank(m1)) {
                markers.add(m1);
            }
            if (StringUtils.isNotBlank(m2)) {
                markers.add(m2);
            }
        }
        return markers;
    }

    public JSONArray getQTLData(String lg) {

        JSONArray arr = new JSONArray();
        if (StringUtils.isBlank(lg)) {
            return arr;
        }
        List<Map> list = qtlDao.queryQtlByLg(lg);
        for (Map m : list) {
            arr.add(m);
        }
        return arr;
    }

    public JSONObject getMarkerByName(String name) {
        JSONObject jsonObject = new JSONObject();
        Marker marker = new Marker();
        marker.setMarkerName(name);
        List<Marker> list = markerDao.findList(marker);
        if (list != null && list.size() > 0) {
            Marker m = list.get(0);
            jsonObject.put("name", m.getMarkerName());
            jsonObject.put("type", m.getMarkerType());
            jsonObject.put("lg", m.getMarkerLg());
            jsonObject.put("position", m.getPosition());
            jsonObject.put("amplificationInfo", m.getAmplificationInfo() == null ? "" : m.getAmplificationInfo());
            jsonObject.put("provider", m.getProvider() == null ? "" : m.getProvider());
            jsonObject.put("refference", m.getRef() == null ? "" : m.getRef());
        } else {
            jsonObject.put("name", name);
            jsonObject.put("type", "");
            jsonObject.put("lg", "");
            jsonObject.put("position", "");
            jsonObject.put("amplificationInfo", "");
            jsonObject.put("provider", "");
            jsonObject.put("refference", "");
        }
        return jsonObject;
    }

    public Map qtlSearchbyResult(String version, String type, String keywords, String param, Page<Qtl> page) {
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.fromObject(param);
        } catch (JSONException e) {
            e.printStackTrace();
            jsonObject = new JSONObject();
        }
        Map result = new HashMap();
        result.put("type", type);
        result.put("keywords", keywords == null ? "" : keywords);
        result.put("condition", jsonObject);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        result.put("chrs", queryChrs(version));
        result.put("lgs", queryLgs());
        JSONArray data = new JSONArray();
        Qtl qtl = new Qtl();
        if ("all".equalsIgnoreCase(type)) {
            qtl.setVersion(version);
            if (!StringUtils.isBlank(keywords)) {
                qtl.setKeywords(keywords);
            }//空白查询所有
        }

        getByPara(qtl, fixParam(type, keywords, param));

        qtl.setVersion(version);//匹配版本信息
        qtl.setPage(page);
        List<Map> list = null;
        List<Qtl> list1 = null;
//        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
//            list = qtlDao.findByTypeAll(qtl);
//            list1 = qtlDao.findByTypeAllList(qtl);
//        } else {//其他的字段查询是对应的and关联
        list = qtlDao.findByCondition(qtl);
        list1 = qtlDao.findList(qtl);
//        }
        Map lgAndMarkerlg = lgAndMarkerlg();
        for (Map m : list) {
            String qtlName = (String) m.get("qtlName");
            String genes = null;
            Associatedgenes associatedgenes = associatedgenesDao.getByNameAndVersion(qtlName, version);
            if (associatedgenes != null) {
                genes = associatedgenes.getAssociatedGenes();
            }
            m.put("genesNum", genes == null ? 0 : genes.split(",").length);
            String lg = (String) m.get("lg");
            if (lgAndMarkerlg.containsKey(lg)) {
                m.put("markerlg", lgAndMarkerlg.get(lg));
            }
            if (version.equals("Gmax_275_v2.0") && genes != null) {
                genes = genes.replaceAll("g", "G");
            }
            m.put("genes", genes == null ? "" : genes);
            data.add(m);
        }
        page.setList(list1);
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    /**
     * 查询数据导出接口
     *
     * @param type
     * @param keywords
     * @param param
     * @return
     */
    public List<Map> qtlSearchbyResultExport(String version, String type, String keywords, String param) {
        Qtl qtl = new Qtl();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                qtl.setKeywords(keywords);
            }//空白查询所有
        }
        getByPara(qtl, fixParam(type, keywords, param));
        qtl.setVersion(version);//匹配版本信息
        List<Map> list = null;
//        if ("all".equalsIgnoreCase(type)) {//all的时候需要全文匹配，查询字段使用or进行关联
//            list = qtlDao.findByTypeAll(qtl);
//        } else {//其他的字段查询是对应的and关联
        list = qtlDao.findByCondition(qtl);
//        }
        for (Map m : list) {
            String qtlName = (String) m.get("qtlName");
            String genes = null;
            Associatedgenes associatedgenes = associatedgenesDao.getByNameAndVersion(qtlName, version);
            if (associatedgenes != null) {
                genes = associatedgenes.getAssociatedGenes();
            }
            m.put("genesNum", genes == null ? 0 : genes.split(",").length);
        }
        return list;
    }

    private String fixParam(String type, String keywords, String param) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = JSONObject.fromObject(param);//页面传递过来的param也可能是空。
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if ("All".equalsIgnoreCase(type) || StringUtils.isBlank(keywords)) {
            //啥也不做

        } else {
            if ("Trait".equalsIgnoreCase(type)) {
                jsonObject.put("trait", keywords);
            }
            if ("QTL Name".equalsIgnoreCase(type)) {
                jsonObject.put("qtlName", keywords);
            }
            if ("marker".equalsIgnoreCase(type)) {
                jsonObject.put("marker", keywords);
            }
            if ("parent".equalsIgnoreCase(type)) {
                jsonObject.put("parent", keywords);  //此处要考虑marker1、marker2联合查询
            }
            if ("reference".equalsIgnoreCase(type)) {
                jsonObject.put("ref", keywords);
            }
        }
        return jsonObject.toString();
    }

    /**
     * 根据搜索页表头字段封装查询条件
     *
     * @param parameters
     * @return
     */
    private Qtl getByPara(Qtl qtl, String parameters) {
//        {
//            "qtlName": "Al tolerance 1-1",
//            "trait": "Aluminum Tolerance",
//            "type": "QTL_inorganic",
//            "chr": "8",
//            "lg": "A2",
//            "version": "Glycine_max.V1.0.23.dna.genome",
//            "method": "ANOVA",
//            "marker1": "mO103_1",
//            "marker2": "",
//            "lod": "1",
//            "parent1": "Young",
//            "parent2": "PI229358",
//            "geneStart": 117.5,
//            "geneEnd": 119.5,
//            "ref": "sh",
//            "author": ""
//        }
        JSONObject jsonObject = JSONObject.fromObject(parameters);
        if (hasValue(jsonObject, "qtlName")) {
            qtl.setQtlName(jsonObject.getString("qtlName"));
        }
        if (hasValue(jsonObject, "trait")) {
            qtl.setTrait(jsonObject.getString("trait"));
        }
        if (hasValue(jsonObject, "type")) {
            qtl.setType(jsonObject.getString("type"));
        }
        if (hasValue(jsonObject, "chr")) {
            qtl.setChr(jsonObject.getString("chr"));
        }
        if (hasValue(jsonObject, "lg")) {
            qtl.setLg(jsonObject.getString("lg"));
        }
        if (hasValue(jsonObject, "version")) {
            qtl.setVersion(jsonObject.getString("version"));
        }
        if (hasValue(jsonObject, "method")) {
            qtl.setMethod(jsonObject.getString("method"));
        }
        if (hasValue(jsonObject, "marker")) {
            qtl.setMarker(jsonObject.getString("marker"));
        }
        if (hasValue(jsonObject, "marker1")) {
            qtl.setMarker1(jsonObject.getString("marker1"));
        }
        if (hasValue(jsonObject, "marker2")) {
            qtl.setMarker2(jsonObject.getString("marker2"));
        }
        if (hasValue(jsonObject, "lod")) {
            String value = jsonObject.getString("lod");
            try {
                String num = value.substring(1);
                qtl.setLod(Float.valueOf(num));
                if (value.startsWith(">")) {
                    qtl.setLodType(Qtl.NUM_TYPE_MORE);
                }
                if (value.startsWith("<")) {
                    qtl.setLodType(Qtl.NUM_TYPE_LESS);
                }
                if (value.startsWith("=")) {
                    qtl.setLodType(Qtl.NUM_TYPE_EQUAL);
                }
            } catch (NumberFormatException e) {

            }

        }
        if (hasValue(jsonObject, "parent")) {
            qtl.setParent(jsonObject.getString("parent"));
        }
        if (hasValue(jsonObject, "parent1")) {
            qtl.setParent1(jsonObject.getString("parent1"));
        }
        if (hasValue(jsonObject, "parent2")) {
            qtl.setParent2(jsonObject.getString("parent2"));
        }
        if (hasValue(jsonObject, "geneStart")) {
            String value = jsonObject.getString("geneStart");
            try {
                String num = value.substring(1);
                qtl.setGenomeStart(Float.valueOf(num));
                if (value.startsWith(">")) {
                    qtl.setGenomeStartType(Qtl.NUM_TYPE_MORE);
                }
                if (value.startsWith("<")) {
                    qtl.setGenomeStartType(Qtl.NUM_TYPE_LESS);
                }
                if (value.startsWith("=")) {
                    qtl.setGenomeStartType(Qtl.NUM_TYPE_EQUAL);
                }
            } catch (NumberFormatException e) {

            }

        }
        if (hasValue(jsonObject, "geneEnd")) {
            String value = jsonObject.getString("geneEnd");
            try {
                String num = value.substring(1);
                qtl.setGenomeEnd(Float.valueOf(num));
                if (value.startsWith(">")) {
                    qtl.setGenomeEndType(Qtl.NUM_TYPE_MORE);
                }
                if (value.startsWith("<")) {
                    qtl.setGenomeEndType(Qtl.NUM_TYPE_LESS);
                }
                if (value.startsWith("=")) {
                    qtl.setGenomeEndType(Qtl.NUM_TYPE_EQUAL);
                }
            } catch (NumberFormatException e) {

            }
        }
        if (hasValue(jsonObject, "ref")) {
            qtl.setRef(jsonObject.getString("ref"));
        }
        if (hasValue(jsonObject, "author")) {
            qtl.setAuthor(jsonObject.getString("author"));
        }
        return qtl;
    }


    private boolean hasValue(JSONObject jsonObject, String key) {
        try {
            String value = (String) jsonObject.get(key);
            return StringUtils.isNotBlank(value);
        } catch (JSONException e) {
            return false;
        }
    }
}
