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
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = userService.findUserByUsername(username);
        if (account.getRoles() == null) {
            Role role = new Role();
            role.setRole("ADMIN");
            account.setRoles(Arrays.asList(role));
        }
        List<GrantedAuthority> authorities = getUserAuthority(account.getRoles());

        UserDetails userDetails = buildUserForAuthentication(account, authorities);
        System.out.println(userDetails);
        return userDetails;
    }

    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
        List<GrantedAuthority> roles = new ArrayList<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(Account account, List<GrantedAuthority> authorities) {
        return new User(account.getUsername(), account.getPassword(),
                true, true, true, true, authorities);
    }
}
