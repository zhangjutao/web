package com.gooalgene.iqgs.dao;

import com.gooalgene.iqgs.entity.sort.UserAssociateTraitFpkm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * create by Administrator on2018/1/26 0026
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class UserAssociateTraitFpkmDaoTest {
    @Autowired
    private UserAssociateTraitFpkmDao userAssociateTraitFpkmDao;

    @Test
    public void testInsertSelective(){
        UserAssociateTraitFpkm userAssociateTraitFpkm = new UserAssociateTraitFpkm();
        userAssociateTraitFpkm.setCreateTime(new Date());
        userAssociateTraitFpkm.setFpkmStr("saas2as,asasa");
        userAssociateTraitFpkm.setTraitCategoryId(12);
        userAssociateTraitFpkm.setUserId("jojo1");
        int i = userAssociateTraitFpkmDao.insertSelective(userAssociateTraitFpkm);
        System.out.println(i);
    }
}
