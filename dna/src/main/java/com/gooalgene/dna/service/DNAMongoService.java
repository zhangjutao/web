package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.common.handler.DocumentCallbackHandlerImpl;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.utils.CommonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by ShiYun on 2017/8/22 0022.
 */
@Service
public class DNAMongoService {

    Logger logger = LoggerFactory.getLogger(DNAMongoService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DNAGensService geneService;

    /**
     * 批量写入数据到某个collection
     *
     * @param batchTosave
     * @param collectionName
     */
    public void insertBatch(List<SNP> batchTosave, String collectionName, IndexDefinition indexDefinition) {
        long start = System.currentTimeMillis();
        mongoTemplate.indexOps(collectionName).ensureIndex(indexDefinition);
        mongoTemplate.insert(batchTosave, collectionName);
        long end = System.currentTimeMillis();
        logger.info("insert " + batchTosave.size() + " records costs " + (end - start) / 1000 + " s.");
    }

    /**
     * 写入一条记录到某个collection
     *
     * @param object
     * @param collectionName
     */
    public void insert(Object object, String collectionName) {
        long start = System.currentTimeMillis();
        mongoTemplate.insert(object, collectionName);
        long end = System.currentTimeMillis();
        logger.info("insert 1 record costs " + (end - start) / 1000 + " s.");
    }


    /**
     * 清空某个collection数据，不会删除索引
     *
     * @param collectionName
     */
    public void clearCollection(String collectionName) {
        mongoTemplate.remove(new Query(), collectionName);
        logger.info("remove all documents from collection " + collectionName + " .");
    }


    /**
     * 删除某个collection
     *
     * @param collectionName
     */
    public void dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
        logger.info("drop collection " + collectionName + " .");
    }

    /**
     * 变更collectionName
     *
     * @param oldName
     * @param newName
     */
    public void renameCollectionName(String oldName, String newName) {
        if (isCollectionExist(newName)) {
            dropCollection(newName);
            logger.info("drop collection " + newName);
        }
        mongoTemplate.getCollection(oldName).rename(newName);
        logger.info("remane collection " + oldName + " to " + newName);
    }

    /**
     * 判断某个collection是否存在
     *
     * @param collectionName
     * @return
     */
    public boolean isCollectionExist(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }


    /**
     * 写入大表数据--废弃
     *
     * @param fileName
     * @return
     */
    public boolean insertSNP(String fileName) {
        boolean flag = false;
        Index index = new Index();
        index.on("type", Sort.Direction.ASC);
        index.on("gene", Sort.Direction.ASC);
        mongoTemplate.indexOps("snp").ensureIndex(index);
        try {
            List<String> list = FileUtils.readLines(new File(fileName));
            int total = list.size();
            String title = list.get(0);
            //i [10,154]   ----样品基因型
            // (154 --- run
            String[] titles = title.split("\t");
            System.out.println(titles.length);
            int num = 0;
            long start = System.currentTimeMillis();
            List<SNP> toInsert = new ArrayList<SNP>();
            for (int i = 1; i < total; i++) {
                SNP snp = new SNP();
                String line = list.get(i);
                String[] ss = line.split("\t");
                int len = ss.length;
                if (len >= 10) {
                    snp.setId(ss[0]);
                    snp.setChr(ss[1]);
                    snp.setPos(Long.valueOf(ss[2]));
                    snp.setRef(ss[3]);
                    snp.setAlt(ss[4]);
                    snp.setQual(Double.valueOf(ss[5]));
                    snp.setMaf(Double.valueOf(ss[6]));
                    snp.setType(ss[7]);
                    snp.setGene(ss[8]);
                    snp.setEffect(ss[9]);
                    String consequencetype = ss[7];
                    if (!ss[9].equals("---")) {
                        consequencetype = consequencetype + "_" + ss[9];
                    }

                    long start1 = System.currentTimeMillis();
                    Map<String, String> map1 = new HashMap<String, String>();
                    String line_key = ss[0];//SNP ID
                    int num_0_0 = 0;
                    int num_0_1 = 0;
                    int num_1_1 = 0;
                    int num_total = 0;
                    //sample从第10列开始
                    for (int j = 10; j < ss.length; j++) {
                        String key = titles[j];
                        String value = ss[j];
//                      System.out.println(key + "\t" + value);
                        map1.put(key, value);
                        if (value.equals("0/0")) {
                            num_0_0++;
                            num_total++;
                        } else if (value.equals("0/1")) {
                            num_0_1++;
                            num_total++;
                        } else if (value.equals("1/1")) {
                            num_1_1++;
                            num_total++;
                        }
                    }
                    Double a = (num_0_0 + 0.5 * num_0_1) / num_total;
                    Double b = (num_1_1 + 0.5 * num_0_1) / num_total;
                    double major, minor;//频率高的为Major,低的为Minor
                    if (a.compareTo(b) >= 0) {
                        major = a;
                        minor = b;
                        snp.setMajorallen("A");
                        snp.setMinorallen("G");
                    } else {
                        major = b;
                        minor = a;
                        snp.setMajorallen("G");
                        snp.setMinorallen("A");
                    }
                    snp.setSamples(map1);
                    snp.setMajor(major);
                    snp.setMinor(minor);
                    long end1 = System.currentTimeMillis();
                    num++;
                    System.out.println(num + ":" + line_key + " : cost:" + (end1 - start1) / 1000 + "s.");
//                    toInsert.add(snp);
                    mongoTemplate.insert(snp);
                }
//                if (toInsert.size() == 20) {
//                    insertBatch(toInsert, "snp", index);
//                    toInsert = new ArrayList<SNP>();
//                    System.out.println("INSERT;;;");
//                }
            }
//            if (toInsert.size() > 0) {
//                insertBatch(toInsert, "snp", index);
//            }
            long end = System.currentTimeMillis();
            logger.info("insertSNP total:" + num + ",title size:" + titles.length + ",cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public SNP findDataById(String type, String chr, String id) {
        String collectionName = type + "_" + chr;
        SNP oneData = new SNP();
        if (mongoTemplate.collectionExists(collectionName)) {
            oneData = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), SNP.class, collectionName);

            //对consequencetype为“UTR3”、“UTR5”及“UTR5；UTR3”的数据进行处理
            if (oneData != null) {
                if (oneData.getConsequencetype().equalsIgnoreCase("UTR3")) {
                    oneData.setConsequencetype("3'UTR");
                } else if (oneData.getConsequencetype().equalsIgnoreCase("UTR5")) {
                    oneData.setConsequencetype("5'UTR");
                } else if (oneData.getConsequencetype().equalsIgnoreCase("UTR5;UTR3")) {
                    oneData.setConsequencetype("UTR5;UTR3");
                }
            }

            return oneData;
        } else {
            return oneData;
        }
    }

    public List<SNP> findDataByIndexInGene(String type, String gene, String id, Integer index, Integer pageSize, String upstream, String downstream, String ctype) {
        int i = gene.indexOf(".") + 1;//Glyma.17G187600
        String chr = "Chr" + gene.substring(i, i + 2);
        String collectionName = type + "_" + chr;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            if (StringUtils.isBlank(upstream)) {
                if (StringUtils.isNotBlank(downstream)) {
                    criteria.and("pos").lte(Long.parseLong(downstream));
                }
            } else {
                if (StringUtils.isBlank(downstream)) {
                    criteria.and("pos").gte(Long.parseLong(upstream));
                } else {
                    criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(upstream)), Criteria.where("pos").lte(Long.parseLong(downstream)));
                }
            }
            if (StringUtils.isNotBlank(gene)) {//不用匹配基因了--只是确认染色体和坐标
//                criteria.and("gene").regex(Tools.getRegex(gene));//匹配基因
//                criteria.and("gene").is(gene);//匹配基因
            }
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = ctype.replace("_", ".*");
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            // 先查询中共个数,对分页有基本了解
            long all = mongoTemplate.count(query, SNP.class, collectionName);
            logger.info("all number : " + all);
            Integer pageNum = index / pageSize;
            Pageable pageable = new PageRequest(pageNum, pageSize);
            query.with(pageable);
            // 去除掉无用的samples字段,极为影响实体bean反射性能
            query.fields().exclude("samples");
            logger.info("Query:{},pageNum:{}, offect:{},pageSize:{}", query.toString(), pageable.getPageNumber(), pageable.getOffset(), pageable.getPageSize());
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    // TODO: 11/27/17 为什么这个地方传入的是分页对象,结果也应该是分页的形式,而这里返回的确实一个list集合???
    public List<SNP> findDataByIndexInRegion(String type, String chr, String id, Integer index, Integer pageSize, String startPos, String endPos, String ctype) {
        String collectionName = type + "_" + chr;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(startPos)), Criteria.where("pos").lte(Long.parseLong(endPos)));
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = ctype.replace("_", ".*");
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            // 先查询中共个数,对分页有基本了解
            long all = mongoTemplate.count(query, SNP.class, collectionName);
            logger.info("all number : " + all);
            Integer pageNum = index / pageSize;
            Pageable pageable = new PageRequest(pageNum, pageSize);
            query.with(pageable);
            // 去除掉无用的samples字段,极为影响实体bean反射性能
            query.fields().exclude("samples");
            logger.info("Query:" + query.toString());
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    public List<SNP> searchIdAndPosInRegin(String type, String ctype, String chr, String startPos, String endPos, Page page) {
        String collectionName = type + "_" + chr;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            DBObject queryObject = new BasicDBObject();
            BasicDBObject[] array = {
                    new BasicDBObject("pos", new BasicDBObject("$gte", Long.parseLong(startPos))),
                    new BasicDBObject("pos", new BasicDBObject("$lte", Long.parseLong(endPos)))
            };
            queryObject.put("$and", array);
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = ctype.replace("_", ".*");
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                queryObject.put("consequencetype", pattern);
            }

            DBObject fieldsObject = new BasicDBObject();
            fieldsObject.put("pos", true);
            Query query = new BasicQuery(queryObject, fieldsObject);
            logger.info("Query:" + query.toString());
            if (page != null) {
                Integer pageNo = page.getPageNo();
                Integer pageSize = page.getPageSize();
                int skip = (pageNo - 1) * pageSize;
                if (skip < 0) {
                    skip = 0;
                }
                query.skip(skip);
                query.limit(pageSize);
            }
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    /**
     * 使用mongodb projection方式进行快速查询
     */
    public List<SNP> searchIdAndPosInRegion(String type, String ctype, String chr, String startPos, String endPos, Page page) {
        String collectionName = type + "_" + chr;
        long total = 0;
        List<SNP> result = new ArrayList<>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(startPos)), Criteria.where("pos").lte(Long.parseLong(endPos)));
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = ctype.replace("_", ".*");
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            query.fields().include("pos").include("consequencetype");
            total = mongoTemplate.count(query, Integer.class, collectionName);
            if (page != null) {
                Integer pageNo = page.getPageNo();
                Integer pageSize = page.getPageSize();
                int skip = (pageNo - 1) * pageSize;
                if (skip < 0) {
                    skip = 0;
                }
                query.skip(skip);
                query.limit(pageSize);
                page.setCount(total);
            }
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    public List<SNP> searchIdAndPosInGene(String type, String ctype, String gene, String upsteam, String downsteam, Page page) {
        DNAGens dnaGens = geneService.findByGeneId(gene);
        String chromosome = dnaGens.getChromosome();
        // 获取到MongoDB中集合名字
        String collectionName = type + "_" + chromosome;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            if (StringUtils.isBlank(upsteam)) {
                if (StringUtils.isNotBlank(downsteam)) {
                    criteria.and("pos").lte(Long.parseLong(downsteam));
                }
            } else {
                if (StringUtils.isBlank(downsteam)) {
                    criteria.and("pos").gte(Long.parseLong(upsteam));
                } else {
                    criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(upsteam)), Criteria.where("pos").lte(Long.parseLong(downsteam)));
                }
            }
            if (StringUtils.isNotBlank(gene)) {//不用匹配基因了--只是确认染色体和坐标
//                criteria.and("gene").regex(Tools.getRegex(gene));//匹配基因
//                criteria.and("gene").is(gene);//匹配基因
            }
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = ctype.replace("_", ".*");
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            query.fields().include("pos").include("consequencetype");
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchIdAndPosInGene:" + query.toString() + ",total:" + total);
            if (page != null) {
                Integer pageNo = page.getPageNo();
                Integer pageSize = page.getPageSize();
                int skip = (pageNo - 1) * pageSize;
                if (skip < 0) {
                    skip = 0;
                }
                query.skip(skip);
                query.limit(pageSize);
            }
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    public List<SNP> searchInRegin(String type, String ctype, String chr, String startPos, String endPos, Page page) {
        String collectionName = type + "_" + chr;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(startPos)), Criteria.where("pos").lte(Long.parseLong(endPos)));
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = "";
                if (ctype.indexOf(' ') != -1) {
                    keywords = ctype.replace("_", ".*_");
                } else if (ctype.indexOf(';') == -1 && ctype.endsWith("_")) {
                    keywords = ctype.replace("_", "");
                } else {
                    keywords = ctype.replace("_", ".*");
                }
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInRegin:" + query.toString() + ",total:" + total);
            if (page != null) {
                Integer pageNo = page.getPageNo();
                Integer pageSize = page.getPageSize();
                int skip = (pageNo - 1) * pageSize;
                if (skip < 0) {
                    skip = 0;
                }
                query.skip(skip);
                query.limit(pageSize);
                page.setCount(total);
            }
            logger.info("Query By Page:" + query.toString());
            query.fields().exclude("samples");
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    /**
     * 根据传入的染色体类型，查询region区间内的所有SNP
     * @param type SNP/INDEL
     * @param ctype consequence type
     * @param chromosome 染色体名字
     * @param upstream 染色体上游区间
     * @param downstream 染色体下游区间
     * @param total 待写入的数目总值
     * @return 该范围内的所有SNP
     */
    public List<SNP> querySNPByRegion(String type, String ctype, String chromosome, String upstream, String downstream,
                                      int pageNo, int pageSize, long total) {
        String collectionName = type + "_" + chromosome;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(upstream)), Criteria.where("pos").lte(Long.parseLong(downstream)));
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = "";
                if (ctype.indexOf(' ') != -1) {
                    keywords = ctype.replace("_", ".*_");
                } else if (ctype.indexOf(';') == -1 && ctype.endsWith("_")) {
                    keywords = ctype.replace("_", "");
                } else {
                    keywords = ctype.replace("_", ".*");
                }
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            int skip = (pageNo - 1) * pageSize;
            if (skip < 0) {
                skip = 0;
            }
            query.skip(skip);
            query.limit(pageSize);
            logger.info("Query By Page:" + query.toString());
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    /**
     * 整合searchInGene与searchInRegion两个方法，不管是在区间还是根据基因来搜索，最终到mongodb中查询都是根据pos来查询
     * 这里需要传入geneId，通过基因ID来确定该基因染色体，然后到mongodb中查询
     */
    public List<SNP> searchInGene(String type, String ctype, String gene, String upstream, String downstream, Page page) {
        DNAGens dnaGens = geneService.findByGeneId(gene);
        String chromosome = dnaGens.getChromosome();
        // 获取到MongoDB中集合名字
        String collectionName = type + "_" + chromosome;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("pos").gte(Long.parseLong(upstream)), Criteria.where("pos").lte(Long.parseLong(downstream)));
            if (StringUtils.isNotBlank(ctype) && (!ctype.startsWith("all"))) {
                String keywords = "";
                if (ctype.indexOf(' ') != -1) {
                    keywords = ctype.replace("_", ".*_");
                } else if (ctype.indexOf(';') == -1 && ctype.endsWith("_")) {
                    keywords = ctype.replace("_", "");
                } else {
                    keywords = ctype.replace("_", ".*");
                }
                Pattern pattern = Pattern.compile("^" + keywords + "$", Pattern.CASE_INSENSITIVE);
                criteria.and("consequencetype").regex(pattern);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInGene:" + query.toString() + ",total:" + total);
            if (page != null) {
                Integer pageNo = page.getPageNo();
                Integer pageSize = page.getPageSize();
                int skip = (pageNo - 1) * pageSize;
                if (skip < 0) {
                    skip = 0;
                }
                query.skip(skip);
                query.limit(pageSize);
            }
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.error(collectionName + " is not exist.");
        }
        page.setCount(total);
        return result;
    }

    public List<SNP> searchByGene(String type, String[] ctypeList, String gene, Page<DNAGens> page) {
        int index = gene.indexOf(".") + 1;//Glyma.17G187600
        String chr = "Chr" + gene.substring(index, index + 2);
        String collectionName = type + "_" + chr;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.and("gene").is(gene);//匹配基因
            if (ctypeList!=null && (!ArrayUtils.contains(ctypeList, "all"))){
                criteria.and("consequencetype").in(ctypeList);
            }
            Query query = new Query();
            query.addCriteria(criteria);
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInGene:" + query.toString() + ",total:" + total);
            Integer pageNo = page.getPageNo();
            Integer pageSize = page.getPageSize();
            //spring data mongodb正确分页方式
            final Pageable pageable = new PageRequest(pageNo-1, pageSize);
            query.with(pageable);
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        page.setCount(total);
        return result;
    }

    /**
     * 输入一个geneId，拿到所有SNP中distinct consequenceType
     * @param geneId 基因ID
     * @return 所有不同consequenceType的集合
     */
    public Set<String> getAllConsequenceTypeByGeneId(String geneId, String type){
        Set<String> allDistinctConsequenceType = null;
        int index = geneId.indexOf(".") + 1;
        String chr = "Chr" + geneId.substring(index, index + 2);
        String collectionName = type + "_" + chr;
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
            criteria.and("gene").is(geneId);
            Query query = new Query();
            query.addCriteria(criteria);
            query.fields().include("consequencetype").exclude("_id");
            List<String> allConsequenceType = new ArrayList<>();
            mongoTemplate.executeQuery(query, collectionName, new DocumentCallbackHandlerImpl<String>("consequencetype", allConsequenceType));
            allDistinctConsequenceType = new HashSet<>(allConsequenceType);
        }
        return allDistinctConsequenceType;
    }

    /**
     * 检查输入基因是否具有该种序列类型
     * @param geneId 输入基因ID
     * @param type {@value com.gooalgene.common.constant.CommonConstant#SNP}
     *             {@value com.gooalgene.common.constant.CommonConstant#SNP}
     * @param consequenceType 基因序列类型
     * @return 该基因是否存在该种序列类型
     */
    public boolean checkGeneConsequenceType(String geneId, String type, List<String> consequenceType){
        boolean result = false;
        String chromosome = CommonUtil.getChromosomeByGene(geneId, type);  //拿到该基因所在染色体
        if (mongoTemplate.collectionExists(chromosome)){
            Criteria criteria = Criteria.where("consequencetype").in(consequenceType);
            criteria.and("gene").is(geneId);
            Query query = new Query();
            query.addCriteria(criteria);
            result = mongoTemplate.exists(query, chromosome);
        }
        return result;
    }
}
