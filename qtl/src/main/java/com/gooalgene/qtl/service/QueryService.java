package com.gooalgene.qtl.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.eventbus.EventBusRegister;
import com.gooalgene.entity.*;
import com.gooalgene.qtl.dao.*;
import com.gooalgene.qtl.entity.QtlSearchResult;
import com.gooalgene.qtl.entity.QtlTableEntity;
import com.gooalgene.qtl.listeners.events.QtlSearchResultEvent;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.eventbus.AsyncEventBus;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.List;

/**
 * Created by ShiYun on 2017/7/10 0010.
 */
@Service
public class QueryService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(QueryService.class);

    @Autowired
    private TraitCategoryDao traitCategoryDao;

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

    @Autowired(required = false)
    private EventBusRegister register;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("sortCache");
    }

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

    /**
     * 所有大组织对应小组织
     * @return name、desc、data(每一个大性状对应小的trait_list性状信息)
     */
    public List<TraitGroup> queryAll() {
        List<TraitCategoryWithinMultipleTraitList> allTraitCategoryAndItsTraitList =
                traitCategoryDao.findAllTraitCategoryAndItsTraitList();
        // 从原始查询结果中对结果集进行过滤
        Collection<TraitGroup> resultGroup = Collections2.transform(allTraitCategoryAndItsTraitList, new Function<TraitCategoryWithinMultipleTraitList, TraitGroup>() {
            @Override
            public TraitGroup apply(TraitCategoryWithinMultipleTraitList input) {
                String maxTraitName = input.getMaxTraitName();
                String maxTraitDesc = input.getQtlDesc();
                List<TraitList> associatedTraitList = input.getTraitLists();
                return new TraitGroup(maxTraitName, maxTraitDesc, associatedTraitList);
            }
        });
        return new ArrayList<>(resultGroup);
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
     */
    public Map<String, Collection<String>> queryChrlg(String version) {
        Map<String, Collection<String>> result = new HashMap<>(2);
        Chrlg c = new Chrlg();
        c.setVersion(version);
        List<Chrlg> chrlgs = chrlgDao.findList(c);
        Collection<String> allChr = Collections2.transform(chrlgs, new Function<Chrlg, String>() {
            @Override
            public String apply(Chrlg input) {
                return input.getChr();
            }
        });
        Collection<String> allLg = Collections2.transform(chrlgs, new Function<Chrlg, String>() {
            @Override
            public String apply(Chrlg input) {
                return input.getLg();
            }
        });
        result.put("All_Chr", allChr);
        result.put("All_Lg", allLg);
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
        result.put("chrs", queryChrlg(version));
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
        List<QtlSearchResult> list = null;
        list = qtlDao.findByCondition(qtl);
        Map lgAndMarkerlg = lgAndMarkerlg();
        for (QtlSearchResult qtlSearchResult : list) {
            String qtlName = (String) qtlSearchResult.getQtlName();
            String genes = null;
            Associatedgenes associatedgenes = associatedgenesDao.getByNameAndVersion(qtlName, version);
            if (associatedgenes != null) {
                genes = associatedgenes.getAssociatedGenes();
            }
            String lg = (String) qtlSearchResult.getLg();
            if (lgAndMarkerlg.containsKey(lg)) {
                qtlSearchResult.setMarkerlg((String) lgAndMarkerlg.get(lg));
            }
            if (version.equals("Gmax_275_v2.0") && genes != null) {
                genes = genes.replaceAll("g", "G");
            }
            qtlSearchResult.setAssociateGenes(genes == null ? "" : genes);
            qtlSearchResult.setGenesNum(genes == null ? 0 : genes.split(",").length);
            data.add(qtlSearchResult);
        }
        // page.setList(list1);
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

    public QtlTableEntity qtlSearchByResult(String version, String type, String keywords, String param, int pageNo, int pageSize, String checkedOption) {
        Map<String, Collection<String>> resultMap = queryChrlg(version);
        // 获取所有chr字段
        Collection<String> allChromosome = resultMap.get("All_Chr");
        // 获取所有lg字段
        Collection<String> allLg = resultMap.get("All_Lg");
        QtlTableEntity qtlTableEntity = new QtlTableEntity();
        qtlTableEntity.setType(type);
        qtlTableEntity.setKeywords(keywords == null ? "" : keywords);
        qtlTableEntity.setCondition(param);
        qtlTableEntity.setPageNo(pageNo);
        qtlTableEntity.setPageSize(pageSize);
        qtlTableEntity.setChrs(new ArrayList<>(allChromosome));
        qtlTableEntity.setLgs(new ArrayList<>(allLg));
        // 所有QTL查询结果
        List<QtlSearchResult> data = new ArrayList<>();
        // 构建QTL查询条件
        Qtl qtl = constructQtlSearchCondition(version, type, keywords, param);
        PageHelper.startPage(pageNo, pageSize);
        List<QtlSearchResult> list = qtlDao.findByCondition(qtl);
        // 发布异步事件，预加载查询下载结果
        QtlSearchResultEvent<Qtl> event = new QtlSearchResultEvent<>(qtl);
        event.setCheckedOption(checkedOption);
        // 预构建缓存key值
        String preConstructCachedKey = event.getClass().getSimpleName() + "-" + keywords;
        Cache.ValueWrapper valueWrapper = cache.get(preConstructCachedKey);
        // 如果缓存中不存在该值，发布异步事件重新查询该值，有效控制后台预加载性能消耗
        if (valueWrapper == null){
            AsyncEventBus asyncEventBus = register.getAsyncEventBus();
            asyncEventBus.post(event);
        }
        qtlTableEntity.setTotal((int) new PageInfo<>(list).getTotal());
        for (QtlSearchResult qtlSearchResult : list) {
            int associateGeneId = qtlSearchResult.getAssociateGeneId();
            Associatedgenes associatedGene = associatedgenesDao.findAssociatedgenesById(associateGeneId);
            String genes = null;
            if (associatedGene != null){
                genes = associatedGene.getAssociatedGenes();
            }
            qtlSearchResult.setGenesNum(genes == null ? 0 : genes.split(",").length);
            if (version.equals("Gmax_275_v2.0") && genes != null) {
                genes = genes.replaceAll("g", "G");
            }
            qtlSearchResult.setAssociateGenes(genes == null ? "" : genes);
            data.add(qtlSearchResult);
        }
        qtlTableEntity.setData(data);
        return qtlTableEntity;
    }

    /**
     * 查询数据导出接口，目前在用户查询时已启动数据预加载，这里可以直接从缓存中取值
     */
    public List<QtlSearchResult> downloadQtlSearchResult(String version, String type, String keywords, String param, String checkedOption) {
        Qtl qtl = constructQtlSearchCondition(version, type, keywords, param);
        QtlSearchResultEvent<Qtl> event = new QtlSearchResultEvent<>(qtl);
        event.setCheckedOption(checkedOption);
        String key = event.getClass().getSimpleName() +"-" + keywords;
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null) {
            logger.warn("该数据未缓存，请重新查询");
            return null;
        }
        List<QtlSearchResult> cachedResult = (List<QtlSearchResult>) valueWrapper.get();
        return cachedResult;
    }

    private String fixParam(String type, String keywords, String param) {
        JSONObject jsonObject = new JSONObject();
        // 页面传递过来的param也可能是空
        if (StringUtils.isNotBlank(param)) {
            try {
                jsonObject = JSONObject.fromObject(param);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 如果type不为空且关键字也不为空
        if (!"All".equalsIgnoreCase(type) && StringUtils.isNotBlank(keywords)) {
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
     */
    private Qtl getByPara(Qtl qtl, String parameters) {
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

    public Map<String, ?> qtlSearchbyGene(String gene, Page<Qtl> page) {
        String version = "Gmax_275_v2.0";
        Map para = new HashMap();
        para.put("version", version);
        para.put("gene", gene);
        Map result = new HashMap();
        result.put("gene", gene);
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        PageHelper.startPage(page.getPageNo(),page.getPageSize());
        List<Map> list = qtlDao.findListByGene(para);
        PageInfo pageInfo=new PageInfo(list);
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
        //result.put("total", page.getCount());
        result.put("total", pageInfo.getTotal());
        result.put("data", data);
        return result;
    }

    private Qtl constructQtlSearchCondition(String version, String type, String keywords, String param) {
        Qtl qtl = new Qtl();
        // 空白查询所有
        if ("all".equalsIgnoreCase(type)) {
            qtl.setVersion(version);
            if (!StringUtils.isBlank(keywords)) {
                qtl.setKeywords(keywords);
            }
        }
        getByPara(qtl, fixParam(type, keywords, param));
        qtl.setVersion(version);
        return qtl;
    }

    /**
     * qtl数据库侧边栏
     */
    public static class TraitGroup {
        private String name;
        private String desc;
        private List<TraitList> data;

        public TraitGroup(String name, String desc, List<TraitList> data) {
            this.name = name;
            this.desc = desc;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<TraitList> getData() {
            return data;
        }

        public void setData(List<TraitList> data) {
            this.data = data;
        }
    }
}
