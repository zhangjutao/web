package com.gooalgene.common.security;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class JdbcRequestMapBuilder extends JdbcDaoSupport {

    private String resourceQuery = "";

    public String getResourceQuery() {
        return resourceQuery;
    }

    public void setResourceQuery(String resourceQuery) {
        this.resourceQuery = resourceQuery;
    }

    //找到所有用户ROLE_NAME与URL之间的对应关系
    public List<RoleUrlResource> findResources(){
        ResourceMapping resourceMapping = new ResourceMapping(getDataSource(), resourceQuery);
        return resourceMapping.execute();
    }

    /**
     * 对每个URL可允许访问的ConfigAttribute，也就是允许访问的用户
     * @return URL与用户的映射
     */
    public LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap(){
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
        List<RoleUrlResource> resources = this.findResources();
        //这个地方遍历有问题
        for (RoleUrlResource resource : resources) {
            //拿到匹配资源路径
            RequestMatcher requestMatcher = this.getRequestMatcher(resource.getUrl());
            //该路径下所有合法的用户
            List<ConfigAttribute> list = null;
            Collection<ConfigAttribute> previousConfigAttributeList = requestMap.get(requestMatcher); //拿到原始某一个URL下的权限集合
            if (previousConfigAttributeList == null || previousConfigAttributeList.size() == 0){
                list = new ArrayList<ConfigAttribute>();
            }else {
                list = new ArrayList<ConfigAttribute>(previousConfigAttributeList); //一个URL对应多个ROLE_情况
            }
            list.add(new SecurityConfig(resource.getRole()));
            requestMap.put(requestMatcher, list);
        }
        return requestMap;
    }

    //通过一个字符串地址构建一个AntPathRequestMatcher，这里也可以是模糊匹配
    public RequestMatcher getRequestMatcher(String url){
        return new AntPathRequestMatcher(url);
    }
}
