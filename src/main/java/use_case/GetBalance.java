package use_case;

import model.Customer;

import java.math.BigDecimal;

public class GetBalance {

    private final CustomerRepository customer;

    public GetBalance(CustomerRepository customer) {
        this.customer = customer;
    }

    public BigDecimal getAccountBalance (String nationalId) {
        Customer customerBylId = customer.findByNationalId(nationalId);
        return customerBylId.getBalance();
    }
}
