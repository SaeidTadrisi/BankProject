import model.Customer;
import model.Profile;
import use_case.CustomerRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FakeCustomerRepository implements CustomerRepository {

    Map<String, Customer> customers;

    public FakeCustomerRepository() {

        customers = new HashMap<>();

        Customer aliRezaei = new Customer(new Profile("Ali", "Rezaei",
                "25874136", "09123698514"), new BigDecimal(5_000_000));
        Customer rezaJafari = new Customer(new Profile("Reza", "Jafari",
                "76925413", "09136149285"), new BigDecimal(900_000_000));
        Customer omidMoradi = new Customer(new Profile("Omid", "Moradi",
                "69236741", "09174568219"), new BigDecimal(50_000_000));

        customers.put("25874136",aliRezaei);
        customers.put("76925413",rezaJafari);
        customers.put("69236741",omidMoradi);
    }

    public Customer findByNationalId(String nationalId) {
        return customers.get(nationalId);
    }
}
