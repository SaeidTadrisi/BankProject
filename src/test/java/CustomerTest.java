import exceptions.BalanceException;
import exceptions.CustomerDetailsException;
import model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    private FakeCustomers fakeCustomers;
    @BeforeEach
    void setUp() {
        fakeCustomers = new FakeCustomers();
    }



    @Test
    void should_check_customer() {

        Customer customer1 = new Customer("", "Moradi",
                "69236741", "09174568219", new BigDecimal(50_000_000));
        Customer customer2 = new Customer("Omid", "",
                "69236741", "09174568219", new BigDecimal(50_000_000));
        Customer customer3 = new Customer("Omid", "Moradi",
                "", "09174568219", new BigDecimal(50_000_000));
        Customer customer4 = new Customer("Omid", "Moradi",
                "69236741", "", new BigDecimal(50_000_000));
        Customer customer5 = new Customer("Omid", "Moradi",
                "69236741", "09174568219", null);
        Customer customer6 = new Customer("Omid", "Moradi",
                "6926741", "09174568219", new BigDecimal(50_000_000));
        Customer customer7 = new Customer("Omid", "Moradi",
                "6926741", "09174568219", new BigDecimal(50_000_000));
        Customer customer8 = new Customer("Omid", "Moradi",
                "6926741", "091745685219", new BigDecimal(50_000_000));

        Assertions.assertThrows(CustomerDetailsException.class, customer1::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer2::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer3::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer4::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer5::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer6::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer7::check);
        Assertions.assertThrows(CustomerDetailsException.class, customer8::check);


    }

    @Test
    void should_find_customer_with_national_id() {
        Customer customer = fakeCustomers.findByNationalId("69236741");

        Customer expectedCustomer = new Customer("Omid", "Moradi",
                "69236741", "09174568219", new BigDecimal(50_000_000));
        assertThat(customer).isEqualTo(expectedCustomer);
    }

    @Test
    void should_return_account_balance_with_national_id() {
        BigDecimal accountBalance = fakeCustomers.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(5_000_000));
    }

    @Test
    void should_make_deposit() {

        Customer customer = fakeCustomers.findByNationalId("69236741");
        customer.deposit(new BigDecimal(10_000_000));

        assertThat(customer.getBalance()).isEqualTo(new BigDecimal(60_000_000));
    }

    @Test
    void should_make_withdraw() {

        Customer customer = fakeCustomers.findByNationalId("69236741");
        customer.withdraw(new BigDecimal(40_000_000));

        assertThat(customer.getBalance()).isEqualTo(new BigDecimal(10_000_000));
    }

    @Test
    void should_throw_balance_exception() {
        Customer customer = fakeCustomers.findByNationalId("69236741");

        assertThrows(BalanceException.class,
                ()->customer.withdraw(new BigDecimal(70_000_000)));
    }
}