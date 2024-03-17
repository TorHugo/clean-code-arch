package com.dev.torhugo.clean.code.arch.application.singup;

public record SignUpInput(String name,
                          String email,
                          String cpf,
                          String carPlate,
                          boolean isPassenger,
                          boolean isDriver) {
    public static SignUpInput with(final String name, final String email, final String cpf, final String carPlate, final boolean isPassenger, final boolean isDriver){
        return new SignUpInput(name, email, cpf, carPlate, isPassenger, isDriver);
    }
}
