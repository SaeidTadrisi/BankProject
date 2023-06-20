import java.math.BigDecimal;

public interface BankService {

    void saveCustomer(Customer customer);

    void withdraw (BigDecimal amount, String phoneNumber);

    void deposit (BigDecimal amount, String phoneNumber);

    BigDecimal getAccountBalance (String phoneNumber);
}
