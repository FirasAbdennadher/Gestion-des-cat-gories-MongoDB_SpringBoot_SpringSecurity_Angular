package org.sid.securityervice.services;

import org.sid.securityervice.dao.AppRoleRepository;
import org.sid.securityervice.dao.AppUserRepository;
import org.sid.securityervice.entities.AppRole;
import org.sid.securityervice.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImp implements AccountService{
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private BCryptPasswordEncoder bcr;

    /*public AccountServiceImp(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bcr) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bcr = bcr;
    }*/

    @Override
    public AppUser saveUser(String userName, String password, String confirmedPassword) {
        AppUser user=appUserRepository.findByUserName(userName);
        if(user!=null) throw  new RuntimeException("User Already exists");
        if(!password.equals(confirmedPassword)) throw  new RuntimeException("Please Confirm your Pasword");
        AppUser appuser = new AppUser();
        appuser.setUserName(userName);
        appuser.setPassword(bcr.encode(password));
        appuser.setActivated(true);
        appUserRepository.save(appuser);
        addRoleToUser(userName,"User");
        return appuser;
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return appUserRepository.findByUserName(userName);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser user =appUserRepository.findByUserName(userName);
        AppRole approle =appRoleRepository.findByRoleName(roleName);
        user.getRoles().add(approle);
    }
}
