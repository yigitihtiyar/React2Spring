package com.example.ws.auth.token;

import com.example.ws.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class Token {

    @Id
    String token;

    @Transient // bu alan db de saklanmaz
    String prefix = "Bearer";


    @JsonIgnore
    @ManyToOne // bir kullanıcı birden fazla token'a sahip olabilir.
    User user;

    public Token(String prefix, String token) {
        this.prefix = prefix;
        this.token = token;
    }

    public Token() {} // Jpa için boş constructor

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
