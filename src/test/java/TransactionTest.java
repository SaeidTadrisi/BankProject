import model.Transactions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.GetTransaction;
import use_case.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTest {

    TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new FakeTransactions();
    }

    @Test
    void should_get_transaction_history() {

        GetTransaction getTransaction = new GetTransaction(transactionRepository);
        List<Transactions> transactionsList = getTransaction.getTransactionsList("25874136");

        List<Transactions> expectedList = new ArrayList<>();
        expectedList.add(new Transactions("Ali", "Rezaei", "25874136",
                "DEPOSIT", new BigDecimal(5000000), new BigDecimal(10000000),
                "2023-07-09 19:51:42"));
        expectedList.add(new Transactions("Ali", "Rezaei", "25874136",
                "DEPOSIT", new BigDecimal(20000000), new BigDecimal(70000000),
                "2023-07-10 22:59:43"));
        expectedList.add(new Transactions("Ali", "Rezaei", "25874136",
                "WITHDRAW", new BigDecimal(20000000), new BigDecimal(50000000),
                "2023-07-10 22:59:44"));

        assertThat(transactionsList).isEqualTo(expectedList);
    }
}
