import exceptions.BalanceException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class BankDAOTest {

    BankDAO bankDAO;
    List<Customer> customers;

    @BeforeEach
    void setUp() {
        bankDAO = new BankDAOImpl();
        customers = new ArrayList<>();
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
                ()-> bankDAO.transaction("77748855",new BigDecimal(5_000_000), TransactionType.DEPOSIT) );
    }

    @Test
    void should_save_two_customer_in_database() {

        customers.add(new Customer("Saeid","Tadrisi", "58662828", "09357374785",new BigDecimal(0)));
        customers.add(new Customer("Ahmad","Azad", "98547853", "09125848827",new BigDecimal(0)));

        for (Customer customer : customers){
            bankDAO.saveCustomer(customer);
        }
    }

    @Test
    void should_get_account_balance() {

        Customer customer = new Customer("Reza","Esmaeili", "69412784", "09125846654",new BigDecimal(100_000));
        bankDAO.saveCustomer(customer);

        BigDecimal accountBalance = bankDAO.getAccountBalance("69412784");

        Assertions.assertEquals(new BigDecimal(100_000), accountBalance);
    }

    @Test
    void should_make_deposit() {

        customers.add(new Customer("Ahmad","Momeni", "31287495", "09126547892",new BigDecimal(0)));
        customers.add(new Customer("Morteza","Arabi", "69874521", "09121236985",new BigDecimal(0)));

        for (Customer customer : customers){
            bankDAO.saveCustomer(customer);
        }
        bankDAO.transaction("69874521",new BigDecimal(5_000_000),TransactionType.DEPOSIT);

        Assertions.assertEquals(new BigDecimal(5_000_000),bankDAO.getAccountBalance("69874521"));
    }

    @Test
    void should_withdraw() {

        bankDAO.transaction("69412784",new BigDecimal(50_000),TransactionType.WITHDRAWAL);

        Assertions.assertEquals(new BigDecimal(50_000), bankDAO.getAccountBalance("69412784"));
    }

    @Test
    void should_throw_balance_exception() {

        Customer customer = new Customer("Ali","Rajabi", "25759831", "09324587845",new BigDecimal(100_000));
        bankDAO.saveCustomer(customer);

        Assertions.assertThrows(BalanceException.class, ()->
                bankDAO.transaction("25759831", new BigDecimal(120_000), TransactionType.WITHDRAWAL));
    }
}