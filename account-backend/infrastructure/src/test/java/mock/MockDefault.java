package mock;

import com.dev.torhugo.clean.code.arch.domain.entity.Account;

public class MockDefault {
    private final static String name = "Test Test";
    private final static String email = "test@example.com";
    private final static String cpf = "648.808.745-23";
    private final static String carPlate = "ABC1234";
    private final static Boolean isPassengerTrue = true;
    private final static Boolean isPassengerFalse = false;
    private final static Boolean isDriverTrue = true;
    private final static Boolean isDriverFalse = false;

    public static Account getAccountDriver(){
        return Account.create(name, email, cpf, isPassengerFalse, isDriverTrue, carPlate);
    }

    public static Account getAccountPassenger(){
        return Account.create(name, email, cpf, isPassengerTrue, isDriverFalse, carPlate);
    }
}
