package com.masarnovsky.popugjira.auth.service;

import com.masarnovsky.popugjira.auth.model.Account;
import com.masarnovsky.popugjira.auth.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AccountService accountService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        Account account = accountService.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (account != null && account.getRoles() == null) {
            account.setRoles(List.of(Role.ADMIN));
            authorities = getUserAuthority(account.getRoles());
            userDetails = buildUserForAuthentication(account, authorities);
        }

        return userDetails;
    }

    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
        List<GrantedAuthority> roles = new ArrayList<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(Account account, List<GrantedAuthority> authorities) {
        return new User(account.getUsername(), account.getPassword(),
                true, true, true, true, authorities);
    }
}
