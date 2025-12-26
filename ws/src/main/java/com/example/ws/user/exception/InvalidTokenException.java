package com.example.ws.user.exception;

import org.springframework.context.i18n.LocaleContextHolder;

import com.example.ws.shared.Messages;

public class InvalidTokenException  extends RuntimeException
{
    public InvalidTokenException()
    {
        super(Messages.getMessageForLocale("hoafixy.activate.user.invalid.token", LocaleContextHolder.getLocale()));
    }
}
