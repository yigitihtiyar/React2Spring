package com.example.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ws.auth.dto.AuthResponse;
import com.example.ws.auth.dto.Credentials;
import com.example.ws.auth.exception.AuthenticationException;
import com.example.ws.auth.token.Token;
import com.example.ws.auth.token.TokenService;
import com.example.ws.user.User;
import com.example.ws.user.UserService;
import com.example.ws.user.dto.UserDTO;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    TokenService tokenService;

    public AuthResponse authenticate(Credentials creds) 
    {
        User inDB = userService.findByEmail(creds.email());
        if(inDB == null) throw new AuthenticationException();
        if(!passwordEncoder.matches(creds.password(), inDB.getPassword())) throw new AuthenticationException();

        Token token =tokenService.createToken(inDB, creds);
         AuthResponse authResponse = new AuthResponse();
         authResponse.setToken(token);
         authResponse.setUser(new UserDTO(inDB));
         return authResponse;
       //
    }
    
}
