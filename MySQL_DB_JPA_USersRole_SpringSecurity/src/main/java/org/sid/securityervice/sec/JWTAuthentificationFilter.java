package org.sid.securityervice.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sid.securityervice.entities.AppUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //text json devient objet java
        //desrialisation json vers java
        try {
            AppUser   appUser=new ObjectMapper().readValue(request.getInputStream(),AppUser.class);
            return authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(appUser.getUserName(),appUser.getPassword()));

        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException ("Problem in request");
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User Springuser = (User) authResult.getPrincipal(); //retouner Springuser authentifié
        List<String> roles = new ArrayList<>();
        Springuser.getAuthorities().forEach(ro->{
            roles.add(ro.getAuthority()); // dans jwt ( username du Springuser et ces roles)
        });

        String jwt= JWT.create().
                withIssuer(request.getRequestURI()).
                withSubject(Springuser.getUsername()).
                withArrayClaim("roles",roles.toArray(new String[roles.size()])).
                withExpiresAt(new Date(System.currentTimeMillis()+SecurityParams.DATEEXPIRATION)).
                sign(Algorithm.HMAC256(SecurityParams.SECRET));
        response.addHeader(SecurityParams.JWT_HEADER_NAME,SecurityParams.HEADERPREFIX+jwt);


    }


}
