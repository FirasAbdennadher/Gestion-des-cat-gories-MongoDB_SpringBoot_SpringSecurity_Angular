package org.sid.securityervice.services;

import org.sid.securityervice.entities.AppRole;
import org.sid.securityervice.entities.AppUser;

public interface AccountService {

    public AppUser saveUser(String userName,String password,String confirmedPassword);
    public AppRole saveRole(AppRole role);
    public AppUser loadUserByUserName(String userName);
    public void addRoleToUser(String userName,String roleName);
}
