package org.sid.security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder bCrypyePasswordEncode=getBC();
        auth.inMemoryAuthentication().withUser("admin").password(bCrypyePasswordEncode.encode("admin")).roles("ADMIN","USER");
         auth.inMemoryAuthentication().withUser("firas").password(bCrypyePasswordEncode.encode("firas")).roles("USER");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); cmm desactiver
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/categories/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/products/**").permitAll();
        http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN");// seuls les admins(pour les posts delete..=
        http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER"); // seuls les user (pour poste delete..)
        http.authorizeRequests().anyRequest().authenticated();// tt les requette que ce sois le role il faut autehnifier
        http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
   /* @Bean
    BCryptPasswordEncoder getBC(){
        return new BCryptPasswordEncoder();
    }*/
}
