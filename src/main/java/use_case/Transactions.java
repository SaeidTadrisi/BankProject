package use_case;

import java.util.List;

public interface Transactions {
    List<TransactionDTO> getTransactions(String accountNumber);
    void saveTransaction(TransactionDTO transactionDTO);

}
