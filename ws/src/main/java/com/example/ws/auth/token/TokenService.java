package com.example.ws.auth.token;

import com.example.ws.auth.dto.Credentials;
import com.example.ws.user.User;


public interface TokenService 
{
    public Token createToken(User user , Credentials creds);

    public User verifyToken(String authorizationHeader);
}
