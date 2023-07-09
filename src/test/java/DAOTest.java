import exceptions.RecordNotFoundException;
import model.Customer;
import model.Transactions;
import org.junit.jupiter.api.Test;
import use_case.DAOImplementation;
import use_case.DAORepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class DAOTest {

    DAORepository daoRepository = new DAOImplementation();

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
                ()-> daoRepository.findCustomerByNationalId("77748855") );
    }

    @Test
    void should_save_a_customer_and_return_that_from_database() {
        Customer customer = new Customer("Ali", "Rezaei",
                "25874136", "09123698514", new BigDecimal(5_000_000));

        daoRepository.saveCustomer(customer);

        Customer customerByNationalId = daoRepository.findCustomerByNationalId("25874136");

        assertThat(customer).isEqualTo(customerByNationalId);
    }

    @Test
    void should_make_deposit() {
        daoRepository.deposit("25874136",new BigDecimal(5_000_000));

        BigDecimal accountBalance = daoRepository.getAccountBalance("25874136");

        assertThat(accountBalance).isEqualTo(new BigDecimal(10_000_000));
    }

    @Test
    void should_withdraw() {
        daoRepository.withdraw("69874521",new BigDecimal(1_000_000));

        BigDecimal accountBalance = daoRepository.getAccountBalance("69874521");

        assertThat(accountBalance).isEqualTo(new BigDecimal(4_000_000));
    }

    @Test
    void should_get_transaction() {

        List<Transactions> transactions = daoRepository.getTransactions("69412784");

        List<Transactions> transactions1 =  new ArrayList<>();

        transactions1.add(new Transactions("Reza","Esmaeili","69412784",
                "WITHDRAW", new BigDecimal(50_000),new BigDecimal(50_000),
                "2023-06-21 19:37:06"));

        assertThat(transactions).isEqualTo(transactions1);
    }
}