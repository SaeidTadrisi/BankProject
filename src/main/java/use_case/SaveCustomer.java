package use_case;

import model.Customer;

import java.math.BigDecimal;

public class SaveCustomer {

    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final String phoneNumber;
    private final BigDecimal balance;

    public SaveCustomer(String firstName, String lastName, String nationalId,
                        String phoneNumber, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public Customer saveCustomerMethod(){
        return new Customer(firstName,lastName,nationalId,phoneNumber,balance);
    }
}