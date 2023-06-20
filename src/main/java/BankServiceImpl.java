import java.math.BigDecimal;

public class BankServiceImpl implements BankService{

    final BankDAO bankDAO;

    public BankServiceImpl(BankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    @Override
    public void saveCustomer(Customer customer) {
        customer.nullCheck();
        bankDAO.saveCustomer(customer);
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
