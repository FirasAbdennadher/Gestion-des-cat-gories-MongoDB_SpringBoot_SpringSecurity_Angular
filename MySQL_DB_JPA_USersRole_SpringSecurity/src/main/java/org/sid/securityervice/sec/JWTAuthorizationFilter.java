package org.sid.securityervice.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //pour cahque requette envoyé par client ( cette méthode va executé )
        httpServletResponse.setContentType( "application/json; charset=utf-8" );

        httpServletResponse.addHeader("Access-Control-Allow-Origin","*");//pour chaque requette, dans  chaque reponse  j'autorise tt les page les page de quelel domaine
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,Authorization");//j'autorise authoezation
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization"); // javascritp, angular ;==> vous avez le droit de lire l'netete autozation..
        httpServletResponse.addHeader("Access-Control-Allow-Methods","GET,POST,DELETE,PUT,PATCH");
        httpServletResponse.addHeader("Access-Control-Max-Age", "1728000");

        if(httpServletRequest.getMethod().equals("OPTIONS")){ // si une reqettte envoyé avec 'option' pas la paine de chercher le jwt ==> req envyé avec opiotn rien a faire
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }else if(httpServletRequest.getRequestURI().equals("/login")) {// lors de /login==> pas la pein de cherche le token ( pas lire le header exclure cette cas de notre filer chain)
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        }else{
            //

        String jwtToken =httpServletRequest.getHeader(SecurityParams.JWT_HEADER_NAME);
        System.out.println("Token "+ jwtToken);
        if(jwtToken ==null || !jwtToken.startsWith(SecurityParams.HEADERPREFIX)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }
        JWTVerifier verifier =JWT.require(Algorithm.HMAC256(SecurityParams.SECRET)).build();
        String jwt=jwtToken.substring(SecurityParams.HEADERPREFIX.length());
        DecodedJWT decodedJWT= verifier.verify(jwt);
        System.out.println(jwt);
        String userName=decodedJWT.getSubject();
        List<String> roles=decodedJWT.getClaims().get("roles").asList(String.class);
        System.out.println("UserName "+userName);
        System.out.println("Role "+roles);

        Collection<GrantedAuthority> authorities= new ArrayList<>();
        roles.forEach(rn->{
            authorities.add(new SimpleGrantedAuthority(rn));
        });
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userName,null,authorities); // user de spring , et authenfiier moi ce user
        SecurityContextHolder.getContext().setAuthentication(user); // a parti du jwtToken  je recuper les info ( user w pw ) pas bseoin d'aller au BD ( puisjr stocker les roles dans jwtToken)
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
    }
}
