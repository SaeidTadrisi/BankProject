package use_case;

import model.BankAccount;
import model.Money;

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

        bankAccounts.saveBalance(new BankAccountDTO(sourceBankAccountNumber,sourceBankAccount.getBalance()));
        bankAccounts.saveBalance(new BankAccountDTO(destinationBankAccountNumber, destinationBankAccount.getBalance()));
        transactions.saveTransaction(new TransactionDTO(sourceBankAccountNumber
                ,money.getAmount(),money.getCurrencyType().name()
                ,sourceBankAccount.getBalance(),TRANSFERS_SENT.name()));
        transactions.saveTransaction(new TransactionDTO(destinationBankAccountNumber
                ,money.getAmount(),money.getCurrencyType().name()
                ,destinationBankAccount.getBalance(), TRANSFERS_RECEIVED.name()));
    }
}
