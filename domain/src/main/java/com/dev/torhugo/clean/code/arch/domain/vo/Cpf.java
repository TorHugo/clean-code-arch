package com.dev.torhugo.clean.code.arch.domain.vo;

import com.dev.torhugo.clean.code.arch.domain.error.exception.InvalidArgumentError;

public class Cpf {
    private final String value;

    public Cpf(final String cpf) {
        if (!this.validate(cpf)) throw new InvalidArgumentError("Invalid cpf!");
        this.value = cpf;
    }

    public String getValue() {
        return value;
    }

    private boolean validate(final String rawCpf) {
        if (rawCpf == null || rawCpf.isEmpty()) {
            return false;
        }
        String cpf = this.removeNonDigits(rawCpf);
        if (this.isInvalidLength(cpf) || this.hasAllDigitsEqual(cpf)) {
            return false;
        }
        int digit1 = this.calculateDigit(cpf, 10);
        int digit2 = this.calculateDigit(cpf, 11);
        return this.extractDigit(cpf).equals(digit1 + "" + digit2);
    }

    private String removeNonDigits(final String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean isInvalidLength(final String cpf) {
        final int CPF_LENGTH = 11;
        return cpf.length() != CPF_LENGTH;
    }

    private boolean hasAllDigitsEqual(final String cpf) {
        char firstCpfDigit = cpf.charAt(0);
        return cpf.chars().allMatch(digit -> digit == firstCpfDigit);
    }

    private int calculateDigit(final String cpf, int factor) {
        int total = 0;
        for (char digit : cpf.toCharArray()) {
            if (factor > 1) {
                total += Character.getNumericValue(digit) * factor--;
            }
        }
        int rest = total % 11;
        return (rest < 2) ? 0 : 11 - rest;
    }

    private String extractDigit(String cpf) {
        return cpf.substring(9);
    }
}
