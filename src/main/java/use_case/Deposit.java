package use_case;

import model.Customer;

import java.math.BigDecimal;

public class Deposit {
    private final CustomerRepository customerRepository;

    public Deposit(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
    }

    public void makeDeposit (String nationalId, BigDecimal amount){
        Customer customerById = customerRepository.findByNationalId(nationalId);
        customerById.deposit(amount);
    }
}
