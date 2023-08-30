package use_case;

import model.BankAccount;
import model.BankAccounts;
import model.Money;
import model.Transactions;

import static model.TransactionType.*;

public class Transfer {

    private final BankAccounts bankAccounts;
    private final Transactions transactions;

    public Transfer(BankAccounts bankAccounts, Transactions transactions) {
        this.bankAccounts = bankAccounts;
        this.transactions = transactions;
    }

    public void execute (String sourceBankAccountNumber, String destinationBankAccountNumber, Money money){
        BankAccount sourceBankAccount = bankAccounts.findByAccountNumber(sourceBankAccountNumber);
        BankAccount destinationBankAccount = bankAccounts.findByAccountNumber(destinationBankAccountNumber);

        sourceBankAccount.withdraw(money);
        destinationBankAccount.deposit(money);

        transactions.saveToDatabase(new SaveTransactionDTO(sourceBankAccount, money)
                ,sourceBankAccountNumber, TRANSFERS_SENT);
        transactions.saveToDatabase(new SaveTransactionDTO(destinationBankAccount, money)
                ,destinationBankAccountNumber, TRANSFERS_RECEIVED);

    }
}
