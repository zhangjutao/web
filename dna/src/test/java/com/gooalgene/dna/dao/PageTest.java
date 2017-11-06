package com.gooalgene.dna.dao;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.dao.RoleDao;
import com.gooalgene.common.dao.UserDao;
import com.gooalgene.common.service.UserService;
import com.gooalgene.dna.entity.testG.UserExample;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context.xml"))
public class PageTest extends TestCase {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleDao roleDao;

    @Test
    public void testPage(){
        //Page page=new Page<User>(0,10);
        //List<User> users=userDao.querAllByPage(page);
        /*User user=new User();
        user.setPage();*/
        //Page<User> users=userService.queryByPage(new Page<User>(2,6));
        PageHelper.startPage(2,2);
        List<User> users=userDao.queryAll();
        PageInfo<User> pageInfo=new PageInfo<>(users,5);
        System.out.println("list:"+pageInfo.getList());
        System.out.println("pageSize:"+pageInfo.getPageSize());
        System.out.println("orderBy:"+pageInfo.getOrderBy());
        System.out.println("pageNum:"+pageInfo.getPageNum());
        System.out.println("pages:"+pageInfo.getPages());
        System.out.println("size:"+pageInfo.getSize());
        System.out.println("total:"+pageInfo.getTotal());
        System.out.println("isFirstPage:"+pageInfo.isIsFirstPage());
        System.out.println("isLastPage:"+pageInfo.isIsLastPage());

        System.out.println(pageInfo);
    }

    @Test
    public void testPageHelperWithGen(){
        UserExample example=new UserExample();
        UserExample.Criteria criteria=example.createCriteria();
        criteria.andIdBetween(26,48);
        //PageHelper.startPage(3,3);
        List<com.gooalgene.dna.entity.testG.User> users=userMapper.selectByExample(example);
        System.out.println(users.size());
    }

    @Test
    public void testRoleDao(){
        User user=userDao.getByUsername("huyao");
        //User user=userDao.findByUserName("huyao");
        List<Role> roles=roleDao.getByUserId(26);
        System.out.println(roles);
    }
}
