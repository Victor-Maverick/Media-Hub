package com.maverick.maverickhub.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverick.maverickhub.dtos.requests.LoginRequest;
import com.maverick.maverickhub.dtos.responses.BaseResponse;
import com.maverick.maverickhub.dtos.responses.LoginResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Collection;

@AllArgsConstructor
public class CustomUsernamePasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest loginRequest = retrieveCredentials(request);
            String username = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            //3. Create an Authentication object that is not yet authenticated
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            //4a. Pass the unauthenticated Authentication
            // object to the AuthenticationManager
            //4b. Get back the Authentication result from the AuthenticationManager
            Authentication authenticationResult =
                    authenticationManager.authenticate(authentication);
            //Put the authentication result in the security context
            SecurityContextHolder.getContext().setAuthentication(authenticationResult);
            return authenticationResult;

        } catch (IOException e) {
            throw new BadCredentialsException(e.getMessage());
        }
    }

    private LoginRequest retrieveCredentials(HttpServletRequest request) throws IOException {
        InputStream requestBodyStream = request.getInputStream();
        LoginRequest loginRequest =
                objectMapper.readValue(requestBodyStream, LoginRequest.class);
        return loginRequest;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        LoginResponse loginResponse = new LoginResponse();
        String token = generateAccessToken(authResult);
        loginResponse.setToken(token);
        loginResponse.setMessage("Successful authentication");
        BaseResponse<LoginResponse> authenticationResponse = new BaseResponse<>();
        authenticationResponse.setCode(HttpStatus.OK.value());
        authenticationResponse.setData(loginResponse);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(authenticationResponse));
        response.flushBuffer();
        chain.doFilter(request, response);
    }

    private static String generateAccessToken(Authentication authResult) {
        return JWT.create()
               .withIssuer("MaverickHub")
               .withArrayClaim("roles",getClaimsFrom(authResult.getAuthorities()))
               .withExpiresAt(Instant.now().plusSeconds(24 * 60 * 60))
               .sign(Algorithm.HMAC512("secret"));
    }

    private static String[] getClaimsFrom(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map((grantedAuthority -> grantedAuthority.getAuthority()))
                .toArray(String[]::new);
    }



    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage(exception.getMessage());
        BaseResponse<LoginResponse> baseResponse = new BaseResponse<>();
        baseResponse.setData(loginResponse);
        baseResponse.setStatus(false);
        baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream()
                .write(objectMapper.writeValueAsBytes(baseResponse));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.flushBuffer();
    }
}
