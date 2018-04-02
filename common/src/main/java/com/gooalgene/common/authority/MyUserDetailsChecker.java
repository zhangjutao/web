package com.gooalgene.common.authority;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * create by Administrator on2018/3/2 0002
 */
public class MyUserDetailsChecker implements UserDetailsChecker {
    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();

    @Override
    public void check(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new LockedException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.locked", "User account is locked"));
        }

        if (!user.isEnabled()) {
            throw new DisabledException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.disabled", "账户未启用"));
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(
                    messages.getMessage("AccountStatusUserDetailsChecker.expired",
                            "账户已过期"));
        }

        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.credentialsExpired",
                    "User credentials have expired"));
        }
    }
}
