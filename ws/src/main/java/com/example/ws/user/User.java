package com.example.ws.user;

import java.util.List;



import com.example.ws.auth.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity 
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = {"email"})) 
public class User {

    @Id
    @GeneratedValue

    long id;
    String username;
    String email;

    @JsonIgnore
    String password;
    @JsonIgnore
    boolean active = false;
    @JsonIgnore
    String activationToken;

    @Lob
    String image;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)
    List<Token> tokens;

    String passwordResetToken;



    public String getPasswordResetToken() {
        return passwordResetToken;
    }
    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getActivationToken() {
        return activationToken;
    }
    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
   
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
   
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

     public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
