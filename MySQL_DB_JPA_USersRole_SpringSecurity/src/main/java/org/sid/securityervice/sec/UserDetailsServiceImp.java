package org.sid.securityervice.sec;

import org.sid.securityervice.dao.AppUserRepository;
import org.sid.securityervice.entities.AppUser;
import org.sid.securityervice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByUserName(userName);
        if(appUser==null) throw new UsernameNotFoundException("UserName Not Found");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(appUser.getUserName(),appUser.getPassword(),authorities);
    }
}
