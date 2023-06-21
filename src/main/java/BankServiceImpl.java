import java.math.BigDecimal;
import java.util.List;

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
    public BigDecimal getAccountBalance(String nationalId) {
        return bankDAO.getAccountBalance(nationalId);
    }

    @Override
    public void transaction(String nationalId, BigDecimal amount, TransactionType transactionType) {
        bankDAO.saveTransaction(nationalId, amount, transactionType);
    }

    @Override
    public List<Transaction> getAllTransactions(String nationalId) {
        return bankDAO.getAllTransactions(nationalId);
    }
}
