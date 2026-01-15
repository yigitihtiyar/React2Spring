package com.example.ws.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.ws.auth.dto.AuthResponse;
import com.example.ws.auth.dto.Credentials;
import com.example.ws.auth.exception.AuthenticationException;
import com.example.ws.error.ApiError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class AuthController
{

    @Autowired 
    AuthService authservice;

   @PostMapping("/api/v1/auth")
   AuthResponse handleAuthentication(@RequestBody Credentials creds)
   {
    return authservice.authenticate(creds);
   }

   @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<?> handleAuthenticationException(AuthenticationException exception)
    {
        ApiError error = new ApiError();
        error.setPath("/api/v1/auth");
        error.setStatus(401);
        error.setMessage(exception.getMessage());
        return ResponseEntity.status(401).body(error);
    }
   
   
    
}
