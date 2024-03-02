package com.dev.torhugo.clean.code.arch.domain.vo;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

public class Name {
    private final String value;

    public Name(final String name) {
        if (this.validate(name)) throw new InvalidArgumentError("Invalid name!");
        this.value = name;
    }

    public String getValue() {
        return value;
    }

    private boolean validate(final String valueInput){
        return !valueInput.matches("[a-zA-Z]+ [a-zA-Z]+");
    }
}
