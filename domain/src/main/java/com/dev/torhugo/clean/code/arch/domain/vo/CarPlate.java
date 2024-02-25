package com.dev.torhugo.clean.code.arch.domain.vo;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

import java.util.Objects;

public class CarPlate {
    private final String value;

    public CarPlate(final String carPlate) {
        if (this.validate(carPlate)) throw new InvalidArgumentError("Invalid car plate!");
        this.value = carPlate;
    }

    public String getValue() {
        return value;
    }

    private boolean validate(final String valueInput){
        if (Objects.isNull(valueInput)) return false;
        return !valueInput.matches("[A-Z]{3}\\d{4}");
    }
}
