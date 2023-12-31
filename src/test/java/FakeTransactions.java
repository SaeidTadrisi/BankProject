import use_case.TransactionDTO;
import use_case.Transactions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static model.CurrencyTypes.EURO;

public class FakeTransactions implements Transactions {

    List<TransactionDTO> transactionList;

    public FakeTransactions() {
        transactionList = new ArrayList<>();

        transactionList.add(new TransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"DEPOSIT" ));

        transactionList.add(new TransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"Withdraw" ));

        transactionList.add(new TransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"Transfer" ));
    }

    @Override
    public List<TransactionDTO> getTransactions(String accountNumber) {
        return transactionList;
    }

    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {

    }
}
