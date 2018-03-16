package com.gooalgene.dna.service;

import com.gooalgene.common.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context-test.xml"))
public class UserServiceTest extends TestCase {
    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testCheckUserExists(){
        boolean exists = userService.exist("crabime");
        assertTrue(exists);
    }

    @Test
    public void checkMongoCountMethod() {
        String collectionName = "SNP_chr316";
        assertTrue(mongoTemplate.collectionExists(collectionName));
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("pos").gte(Long.parseLong("1000")), Criteria.where("pos").lte(Long.parseLong("120000")));
        Query query = new Query();
        query.addCriteria(criteria);
        query.fields().include("pos").include("consequencetype");
        long total = mongoTemplate.count(query, Integer.class, collectionName);
        assertNotSame(0, total);
    }

}
