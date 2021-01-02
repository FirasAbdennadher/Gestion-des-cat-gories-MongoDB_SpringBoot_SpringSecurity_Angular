package org.sid.securityervice;

import org.sid.securityervice.entities.AppRole;
import org.sid.securityervice.entities.AppUser;
import org.sid.securityervice.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SecurityerviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityerviceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args->{
            System.out.println("*********************************");
                accountService.saveRole(new AppRole(null,"USER"));
                accountService.saveRole(new AppRole(null,"ADMIN"));
            Stream.of("user1","user2","user3","admin").forEach(un->{
                accountService.saveUser(un,"0","0");
            });
            accountService.addRoleToUser("admin","ADMIN");
        };
    }

    @Bean
    BCryptPasswordEncoder getPw(){
        return new BCryptPasswordEncoder();
    }

}
