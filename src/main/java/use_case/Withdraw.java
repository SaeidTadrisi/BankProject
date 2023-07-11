package use_case;

import model.Customer;

import java.math.BigDecimal;

public class Withdraw {

    private final CustomerRepository customer;

    public Withdraw(CustomerRepository customer) {
        this.customer = customer;
    }

    public void makeWithdraw (String nationalId, BigDecimal amount){

        Customer customerById = customer.findByNationalId(nationalId);

        customerById.withdraw(amount);
    }
}
