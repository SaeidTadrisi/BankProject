import model.TransactionType;
import use_case.GetTransactionDTO;
import use_case.SaveTransactionDTO;
import model.Transactions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static model.CurrencyTypes.EURO;

public class FakeTransactions implements Transactions {

    List<GetTransactionDTO> transactionList;

    public FakeTransactions() {
        transactionList = new ArrayList<>();

        transactionList.add(new GetTransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"DEPOSIT" ));

        transactionList.add(new GetTransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"Withdraw" ));

        transactionList.add(new GetTransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                                               , new BigDecimal(50_000),"Transfer" ));
    }

    @Override
    public List<GetTransactionDTO> getTransactions(String accountNumber) {
        return transactionList;
    }

    @Override
    public void saveToDatabase(SaveTransactionDTO saveTransactionDTO, String accountNumber
                               , TransactionType transactionType) {

    }


}
