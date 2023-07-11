package use_case;

import model.Transactions;

import java.util.List;

public interface TransactionRepository {
    List<Transactions> findTransactionsByNationalId (String nationalId);
}
