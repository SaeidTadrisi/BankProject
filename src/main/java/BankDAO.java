import java.math.BigDecimal;
import java.util.List;

public interface BankDAO {

     void saveCustomer(Customer customer);

     void withdraw (BigDecimal amount, String phoneNumber);

     void deposit (BigDecimal amount, String phoneNumber);

     BigDecimal getAccountBalance (String phoneNumber);

}
