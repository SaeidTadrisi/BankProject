import java.math.BigDecimal;
import java.util.List;

public interface BankService {

    void saveCustomer(Customer customer);

    BigDecimal getAccountBalance (String nationalId);

    void transaction (String nationalId, BigDecimal amount, TransactionType transactionType);

    List<Transaction> getAllTransactions (String nationalId);

}
