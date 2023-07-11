package use_case;

import model.Customer;
import model.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface DAO {
    public void saveCustomer (String firstName, String lastName, String nationalId,
                              String phoneNumber, BigDecimal balance);

    public void makeDeposit(String nationalId, BigDecimal amount);

    public void makeWithdraw(String nationalId, BigDecimal amount);

    public BigDecimal getAccountBalance(String nationalId);

    public Customer findByNationalId(String nationalId);

    public List<Transactions> getTransactions(String nationalId);

}
