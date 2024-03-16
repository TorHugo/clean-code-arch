package com.dev.torhugo.clean.code.arch.application.singup;

public record SignUpMail(
        String email,
        String message
) {
    public static SignUpMail with(final String email, final String message){
        return new SignUpMail(email, message);
    }
}
