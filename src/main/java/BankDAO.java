import java.math.BigDecimal;
import java.util.List;

public interface BankDAO {

     void saveCustomer(Customer customer);

     BigDecimal getAccountBalance (String nationalId);

     void saveTransaction(String nationalId, BigDecimal amount, TransactionType transactionType);

     List<Transaction> getAllTransactions (String nationalId);
}
