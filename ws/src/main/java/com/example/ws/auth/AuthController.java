package com.example.ws.auth;

import org.springframework.web.bind.annotation.RestController;

import com.example.ws.auth.dto.AuthResponse;
import com.example.ws.auth.dto.Credentials;
import com.example.ws.shared.GenericMessage;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class AuthController {

    @Autowired
    AuthService authservice;

    @PostMapping("/api/v1/auth")
    ResponseEntity<AuthResponse> handleAuthentication(@Valid @RequestBody Credentials creds) {
        var authResponse = authservice.authenticate(creds);
        var cookie = ResponseCookie.from("hoax-token", authResponse.getToken().getToken()).path("/")
                .httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(authResponse);
    }

    @PostMapping("/api/v1/logout")
    ResponseEntity<GenericMessage> handleLogout(
            @RequestHeader(name = "Authorization", required = false) String authorizationHeader,
            @CookieValue(name = "hoax-token", required = false) String cookieValue) {
        var tokenWithPrefix = authorizationHeader;
        if (cookieValue != null) {
            tokenWithPrefix = "AnyPrefix" + cookieValue;
        }
        authservice.logout(tokenWithPrefix);
        var cookie = ResponseCookie.from("hoax-token", "").path("/").maxAge(0)
                .httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new GenericMessage("Logout Success"));
    }

}
