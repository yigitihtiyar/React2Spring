package com.example.ws.user.dto;

import com.example.ws.user.User;

public class UserDTO 
{
  
    long id;

    String username;

    String email;

    String image;



    public UserDTO(User user) //constructor
    {
        setId(user.getId());
        setUsername(user.getUsername());
        setEmail(user.getEmail());
        setImage(user.getImage());
       
    }
  
   
    public String getImage() {
        return image == null ? "default.png" : image;
    }
    public void setImage(String image) {
        this.image = image;
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

     public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
