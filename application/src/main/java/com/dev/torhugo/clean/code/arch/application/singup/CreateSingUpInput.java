package com.dev.torhugo.clean.code.arch.application.singup;

public record CreateSingUpInput(String name,
                                String email,
                                String cpf,
                                String carPlate,
                                boolean isPassenger,
                                boolean isDriver) {
    public static CreateSingUpInput with(final String name, final String email, final String cpf, final String carPlate, final boolean isPassenger, final boolean isDriver){
        return new CreateSingUpInput(name, email, cpf, carPlate, isPassenger, isDriver);
    }
}
