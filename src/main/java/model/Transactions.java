package model;

import model.TransactionType;
import use_case.GetTransactionDTO;
import use_case.SaveTransactionDTO;

import java.util.List;

public interface Transactions {
    List<GetTransactionDTO> getTransactions(String accountNumber);
    void saveToDatabase (SaveTransactionDTO saveTransactionDTO, String accountNumber, TransactionType transactionType);

}
