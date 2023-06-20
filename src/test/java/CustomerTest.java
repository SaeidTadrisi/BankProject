import exceptions.EmptyOrNullException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {


    @Test
    void should_throw_null_exception() {

        BankService bankService = new BankServiceImpl(new BankDAODouble());

        Customer customer = new Customer("S", "T", "dd", null, new BigDecimal(0));

        assertThrows(EmptyOrNullException.class, customer::nullCheck);
    }

    private class BankDAODouble implements BankDAO {
        @Override
        public void saveCustomer(Customer customer) {
        }

        @Override
        public void withdraw(BigDecimal amount, String phoneNumber) {
        }

        @Override
        public void deposit(BigDecimal amount, String phoneNumber) {
        }

        @Override
        public BigDecimal getAccountBalance(String phoneNumber) {
            return null;
        }
    }
}