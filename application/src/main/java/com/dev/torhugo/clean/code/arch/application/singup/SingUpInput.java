package com.dev.torhugo.clean.code.arch.application.singup;

public record SingUpInput(String name,
                          String email,
                          String cpf,
                          String carPlate,
                          boolean isPassenger,
                          boolean isDriver) {
    public static SingUpInput with(final String name, final String email, final String cpf, final String carPlate, final boolean isPassenger, final boolean isDriver){
        return new SingUpInput(name, email, cpf, carPlate, isPassenger, isDriver);
    }
}
