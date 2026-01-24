package com.example.ws.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.ws.auth.dto.AuthResponse;
import com.example.ws.auth.dto.Credentials;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController
{

    @Autowired 
    AuthService authservice;

   @PostMapping("/api/v1/auth")
   AuthResponse handleAuthentication(@Valid @RequestBody Credentials creds)
   {
    return authservice.authenticate(creds);
   }

 
    
}
