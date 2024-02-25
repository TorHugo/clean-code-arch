package com.dev.torhugo.clean.code.arch.domain.vo;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

public class Email {
    private final String value;

    public Email(final String email) {
        if (this.validate(email)) throw new InvalidArgumentError("Invalid email!");
        this.value = email;
    }

    public String getValue() {
        return value;
    }

    private boolean validate(final String valueInput){
        return !valueInput.matches("^(.+)@(.+)$");
    }
}
