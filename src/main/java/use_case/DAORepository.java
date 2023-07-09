package use_case;

import model.Customer;
import model.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface DAORepository {
    void saveCustomer(Customer customer);

    Customer findCustomerByNationalId(String nationalId);

    BigDecimal getAccountBalance (String nationalId);

    void deposit(String nationalId, BigDecimal amount);

    void withdraw(String nationalId,BigDecimal amount);

    List<Transactions> getTransactions (String nationalId);

}
