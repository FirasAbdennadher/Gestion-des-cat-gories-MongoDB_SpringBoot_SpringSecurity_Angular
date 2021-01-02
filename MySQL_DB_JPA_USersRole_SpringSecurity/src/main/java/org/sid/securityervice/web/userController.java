package org.sid.securityervice.web;

import lombok.Data;
import lombok.ToString;
import org.sid.securityervice.entities.AppUser;
import org.sid.securityervice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    public AppUser register(@RequestBody UserForm userform){ // donnés envoyer format json dans le corps du requeete
    return accountService.saveUser(userform.getUserName(),userform.getPassword(),userform.getConfirmedPassword());
    }
}
@Data @ToString
class UserForm {
    private String userName;
    private String password;
    private String confirmedPassword;


}
