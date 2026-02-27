package com.example.ws.email;

import jakarta.validation.constraints.Email;

public record PasswordResetRequest(@Email String email) {
    
}
