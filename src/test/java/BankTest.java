import exceptions.EmptyOrNullException;
import exceptions.RecordNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    BankDAO bankDAO;
    List<Customer> customers;

    @BeforeEach
    void setUp() {
        bankDAO = new BankDAOImpl();
        customers = new ArrayList<>();
    }

    @Test
    void should_throw_empty_or_null_exception() {

        Customer customer = new Customer("S", "T", "dd", null,new BigDecimal(0));

        assertThrows(EmptyOrNullException.class, customer::nullCheck);
    }

    @Test
    void should_connect_to_database() {

        try (final Connection connection = getConnection("jdbc:mysql://localhost:3306/bank", "bank", "Bank@1848")) {
            if (connection == null) {
                fail("Error");
            }
        } catch (SQLException e) {
            fail("Error");
        }
    }

    @Test
    void should_throw_exception_if_could_not_find_customer() {

        assertThrows(RecordNotFoundException.class,
                ()-> bankDAO.deposit(new BigDecimal(5_000_000), "09302521542") );

    }

    @Test
    void should_save_two_customer_in_database() {

        customers.add(new Customer("Saeid","Tadrisi", "S.tad@gmail.com", "09357374785",new BigDecimal(0)));
        customers.add(new Customer("Ahmad","Azad", "a.azad@gmail.com", "09125848827",new BigDecimal(0)));

        for (Customer customer : customers){
            bankDAO.saveCustomer(customer);
        }
    }

    @Test
    void should_get_account_balance() {

        Customer customer = new Customer("Reza","Esmaeili", "R.esm@gmail.com", "09125846654",new BigDecimal(100_000));
        bankDAO.saveCustomer(customer);

        BigDecimal accountBalance = bankDAO.getAccountBalance("09125846654");

        Assertions.assertEquals(new BigDecimal(100_000), accountBalance);
    }

    @Test
    void should_make_deposit() {

        customers.add(new Customer("Ahmad","Momeni", "A.mo@gmail.com", "09126547892",new BigDecimal(0)));
        customers.add(new Customer("Morteza","Arabi", "M.ara@gmail.com", "09121236985",new BigDecimal(0)));

        for (Customer customer : customers){
            bankDAO.saveCustomer(customer);
        }

        bankDAO.deposit(new BigDecimal(5_000_000), "09126547892");

        Assertions.assertEquals(new BigDecimal(5_000_000),bankDAO.getAccountBalance("09126547892"));
    }

    @Test
    void should_withdraw() {

        bankDAO.withdraw(new BigDecimal(50_000),"09125846654" );

        Assertions.assertEquals(new BigDecimal(50_000), bankDAO.getAccountBalance("09125846654"));
    }
}