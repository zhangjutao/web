package com.gooalgene.dna.service;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.SNP;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ShiYun on 2017/8/22 0022.
 */
@Service
public class DNAMongoService {

    Logger logger = LoggerFactory.getLogger(DNAMongoService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

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
        String collectionName = type + "_" +chr;
        SNP oneData = new SNP();
        if (mongoTemplate.collectionExists(collectionName)) {
            oneData = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), SNP.class, collectionName);
            return oneData;
        } else {
            return oneData;
        }
    }

    public List<SNP> searchInRegin(String type, String ctype, String chr, String startPos, String endPos, Page page) {
        String collectionName = type + "_" + chr;
        long total = 0;
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
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInRegin:" + query.toString() + ",total:" + total);
            if(page!=null){
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
            // todo 优化mongodb查询速度
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        return result;
    }

    public List<SNP> searchInGene(String type, String ctype, String gene, String upsteam, String downsteam, Page page) {
        int index = gene.indexOf(".") + 1;//Glyma.17G187600
        String chr = "Chr" + gene.substring(index, index + 2);
        String collectionName = type + "_" + chr;
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
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInGene:" + query.toString() + ",total:" + total);
            Integer pageNo = page.getPageNo();
            Integer pageSize = page.getPageSize();
            int skip = (pageNo - 1) * pageSize;
            if (skip < 0) {
                skip = 0;
            }
            //todo 去掉分页？
            query.skip(skip);
            query.limit(pageSize);
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        page.setCount(total);
        return result;
    }

    public List<SNP> searchByGene(String type, String gene, Page<DNAGens> page) {
        int index = gene.indexOf(".") + 1;//Glyma.17G187600
        String chr = "Chr" + gene.substring(index, index + 2);
        String collectionName = type + "_" + chr;
        long total = 0;
        List<SNP> result = new ArrayList<SNP>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Criteria criteria = new Criteria();
//                criteria.and("gene").regex(Tools.getRegex(gene));//匹配基因
            criteria.and("gene").is(gene);//匹配基因
            Query query = new Query();
            query.addCriteria(criteria);
            logger.info("Query:" + query.toString());
            total = mongoTemplate.count(query, SNP.class, collectionName);//总记录数
            logger.info(collectionName + " searchInGene:" + query.toString() + ",total:" + total);
            Integer pageNo = page.getPageNo();
            Integer pageSize = page.getPageSize();
            int skip = (pageNo - 1) * pageSize;
            if (skip < 0) {
                skip = 0;
            }
            query.skip(skip);
            query.limit(pageSize);
            result = mongoTemplate.find(query, SNP.class, collectionName);
        } else {
            logger.info(collectionName + " is not exist.");
        }
        page.setCount(total);
        return result;
    }
}
