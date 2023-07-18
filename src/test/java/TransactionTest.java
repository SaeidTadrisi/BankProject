import org.junit.jupiter.api.BeforeEach;
import use_case.Transactions;

public class TransactionTest {

    Transactions transactions;

    @BeforeEach
    void setUp() {
        transactions = new FakeTransactions();
    }

//    @Test
//    void should_get_transaction_history() {
//
//        RetrieveTransaction retrieveTransaction = new RetrieveTransaction(transactions);
//        List<Transaction> transactionList = retrieveTransaction.getTransactionsList("25874136");
//
//        List<Transaction> expectedList = new ArrayList<>();
//        expectedList.add(new Transaction("Ali", "Rezaei", "25874136",
//                accountNumber, "DEPOSIT", new BigDecimal(5000000), new BigDecimal(10000000),
//                "2023-07-09 19:51:42"));
//        expectedList.add(new Transaction("Ali", "Rezaei", "25874136",
//                accountNumber, "DEPOSIT", new BigDecimal(20000000), new BigDecimal(70000000),
//                "2023-07-10 22:59:43"));
//        expectedList.add(new Transaction("Ali", "Rezaei", "25874136",
//                accountNumber, "WITHDRAW", new BigDecimal(20000000), new BigDecimal(50000000),
//                "2023-07-10 22:59:44"));
//
//        assertThat(transactionList).isEqualTo(expectedList);
//    }
}
