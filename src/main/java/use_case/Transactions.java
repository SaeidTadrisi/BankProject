package use_case;

import java.util.List;

public interface Transactions {
    List<TransactionDTO> getByAccountNumber(String accountNumber);

    void saveByAccountNumber(TransactionDTO transactionDTO);
}
