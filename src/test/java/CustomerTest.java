import exceptions.BalanceException;
import exceptions.CustomerDetailsException;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    private CustomerRepository customers;
    @BeforeEach
    void setUp() {
        customers = new FakeCustomers();
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
                "69236741", "091745668219", new BigDecimal(50_000_000));

        assertThrows(CustomerDetailsException.class, customer1::check);
        assertThrows(CustomerDetailsException.class, customer2::check);
        assertThrows(CustomerDetailsException.class, customer3::check);
        assertThrows(CustomerDetailsException.class, customer4::check);
        assertThrows(CustomerDetailsException.class, customer5::check);
        assertThrows(CustomerDetailsException.class, customer6::check);
        assertThrows(CustomerDetailsException.class, customer7::check);
    }

    @Test
    void should_make_deposit() {

        Deposit deposit = new Deposit(customers);
        GetBalance getBalance = new GetBalance(customers);

        deposit.makeDeposit("25874136", new BigDecimal(6_000_000));
        BigDecimal accountBalance = getBalance.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(11_000_000));
    }

    @Test
    void should_make_withdraw() {

        Withdraw withdraw = new Withdraw(customers);
        GetBalance getBalance = new GetBalance(customers);

        withdraw.makeWithdraw("25874136", new BigDecimal(4_000_000));
        BigDecimal accountBalance = getBalance.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(1_000_000));
    }

    @Test
    void should_get_account_balance() {

        GetBalance getBalance = new GetBalance(customers);

        BigDecimal accountBalance = getBalance.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(5_000_000));
    }

    @Test
    void should_find_customer_with_national_id() {

        Customer customersByNationalId = customers.findByNationalId("69236741");

        Customer expectedCustomer = new Customer("Omid", "Moradi",
                "69236741", "09174568219", new BigDecimal(50_000_000));

        assertThat(customersByNationalId).isEqualTo(expectedCustomer);
    }

    @Test
    void should_throw_balance_exception() {

        Withdraw withdraw = new Withdraw(customers);

        assertThrows(BalanceException.class,
                ()->withdraw.makeWithdraw("69236741", new BigDecimal(60_000_000)));
    }

    @Test
    void should_save_a_new_customer() {
        SaveCustomer saveCustomer = new SaveCustomer("Omid", "Moradi",
                "69236741", "091745668219", new BigDecimal(50_000_000));
        Customer customer = saveCustomer.saveCustomerMethod();

        Customer expectedCustomer = new Customer("Omid", "Moradi",
                "69236741", "091745668219", new BigDecimal(50_000_000));

        assertThat(customer).isEqualTo(expectedCustomer);
    }
}