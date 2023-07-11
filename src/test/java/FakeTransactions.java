import model.Transactions;
import use_case.TransactionRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FakeTransactions implements TransactionRepository {

    List<Transactions> transactionList;

    public FakeTransactions() {
        transactionList = new ArrayList<>();

        transactionList.add(new Transactions("Ali", "Rezaei", "25874136",
                "DEPOSIT", new BigDecimal(5000000), new BigDecimal(10000000),
                "2023-07-09 19:51:42"));
        transactionList.add(new Transactions("Ali", "Rezaei", "25874136",
                "DEPOSIT", new BigDecimal(20000000), new BigDecimal(70000000),
                "2023-07-10 22:59:43"));
        transactionList.add(new Transactions("Ali", "Rezaei", "25874136",
                "WITHDRAW", new BigDecimal(20000000), new BigDecimal(50000000),
                "2023-07-10 22:59:44"));
    }

    @Override
    public List<Transactions> findTransactionsByNationalId(String nationalId) {
        return transactionList;
    }
}
