package com.dev.torhugo.clean.code.arch.domain;

import static com.dev.torhugo.clean.code.arch.domain.validator.CpfValidatorUtils.validateCpf;

public abstract class Validator {

    private static final String PATTERN_CAR_PLATE = "[A-Z]{3}\\d{4}";
    private static final String PATTERN_NAME = "[a-zA-Z]+ [a-zA-Z]+";
    private static final String PATTERN_EMAIL = "^(.+)@(.+)$";

    public static boolean validateRegexCarPlate(final String carPlate){
        return !carPlate.matches(PATTERN_CAR_PLATE);
    }
    public static boolean validateRegexName(final String name){
        return !name.matches(PATTERN_NAME);
    }
    public static boolean validateRegexEmail(final String email){
        return !email.matches(PATTERN_EMAIL);
    }
    public static boolean validateInvalidCpf(final String cpf){
        return validateCpf(cpf);
    }
}
