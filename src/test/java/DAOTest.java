import exceptions.RecordNotFoundException;
import model.Customer;
import model.Profile;
import model.Transactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.DAO;
import use_case.DAOImplementation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DAOTest {


    DAO dao;
    @BeforeEach
    void setUp() {
        dao = new DAOImplementation();
    }


    @Test

    void name() {
//        dao.makeDeposit("25874136", new BigDecimal(20_000_000));
//        dao.makeWithdraw("25874136", new BigDecimal(20_000_000));
//        System.out.println(dao.getAccountBalance("25874136"));
//
//        dao.saveCustomer("Ar", "Rezri",
//                "32233223", "09144448514", new BigDecimal(5_000_000));
//
//
//        System.out.println(dao.findByNationalId("25874136"));

        System.out.println(dao.getTransactions("25874136"));
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
                ()-> dao.findByNationalId("77748855") );
    }

    @Test
    void should_save_a_customer_and_return_that_from_database() {

        dao.saveCustomer("Ali", "Rezaei",
                "25874136", "09123698514", new BigDecimal(5_000_000));

        Customer customer = new Customer(new Profile("Ali", "Rezaei",
                "25874136", "09123698514"), new BigDecimal(5_000_000));

        Customer customerByNationalId = dao.findByNationalId("25874136");

        assertThat(customer).isEqualTo(customerByNationalId);
    }

    @Test
    void should_make_deposit() {
        dao.makeDeposit("25874136",new BigDecimal(5_000_000));

        BigDecimal accountBalance = dao.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(10_000_000));
    }

    @Test
    void should_withdraw() {
        dao.makeWithdraw("69874521",new BigDecimal(1_000_000));

        BigDecimal accountBalance = dao.getAccountBalance("69874521");

        assertThat(accountBalance).isEqualTo(new BigDecimal(4_000_000));
    }

    @Test
    void should_get_transaction() {

        List<Transactions> transactions = dao.getTransactions("69412784");

        List<Transactions> transactions1 =  new ArrayList<>();

        transactions1.add(new Transactions("Reza","Esmaeili","69412784",
                "WITHDRAW", new BigDecimal(50_000),new BigDecimal(50_000),
                "2023-06-21 19:37:06"));

        assertThat(transactions).isEqualTo(transactions1);
    }
}