import model.Customer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FakeCustomers {

    Map<String,Customer> customers;

    public FakeCustomers() {

        customers = new HashMap<>();

        Customer aliRezaei = new Customer("Ali", "Rezaei",
                "25874136", "09123698514", new BigDecimal(5_000_000));
        Customer rezaJafari = new Customer("Reza", "Jafari",
                "76925413", "09136149285", new BigDecimal(900_000_000));
        Customer omidMoradi = new Customer("Omid", "Moradi",
                "69236741", "09174568219", new BigDecimal(50_000_000));

        customers.put("25874136",aliRezaei);
        customers.put("76925413",rezaJafari);
        customers.put("69236741",omidMoradi);
    }

    public Customer findByNationalId(String nationalId) {
        return customers.get(nationalId);
    }


    public BigDecimal getAccountBalance(String nationalId) {
        return customers.get(nationalId).getBalance();
    }
}
