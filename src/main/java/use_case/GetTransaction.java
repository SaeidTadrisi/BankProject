package use_case;

import model.Transactions;

import java.util.ArrayList;
import java.util.List;

public class GetTransaction {

    TransactionRepository transactionRepository;
    List<Transactions> transactionsList;

    public GetTransaction(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        transactionsList = new ArrayList<>();
    }

    public List<Transactions> getTransactionsList (String nationalId){

        List<Transactions> transactionsByNationalId = transactionRepository.findTransactionsByNationalId(nationalId);

        for (Transactions transactions : transactionsByNationalId){
            transactionsList.add(transactions);
        }
        return transactionsList;
    }
}
