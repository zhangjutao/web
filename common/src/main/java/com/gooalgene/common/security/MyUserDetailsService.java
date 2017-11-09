package com.gooalgene.common.security;

import com.gooalgene.common.authority.Role;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.dao.RoleDao;
import com.gooalgene.common.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    Logger logger= LoggerFactory.getLogger(MyUserDetailsService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Boolean accountNonExpired=false;
        Boolean enabled=false;
        User user=userDao.getByUsername(username);

        //List<GrantedAuthority> authorities =new ArrayList<>();
        List<Role> roles=roleDao.getByUserId(user.getId());
        logger.debug("得到其权限：{}", roles);
        if(System.currentTimeMillis()<user.getDue_time().getTime()){
            accountNonExpired=true;
        }
        if(user.getEnabled()==1){
            enabled=true;
        }

        return new SecurityUser(user.getId(),user.getUsername(),user.getPassword(),
                enabled,accountNonExpired,true,true,roles);
    }
}
