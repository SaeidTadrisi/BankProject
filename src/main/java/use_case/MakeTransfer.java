package use_case;

import model.BankAccount;
import model.Money;

import static model.TransactionType.TRANSFERS;

public class MakeTransfer {

    private final BankAccounts bankAccounts;
    private final Transactions transactions;

    public MakeTransfer(BankAccounts bankAccounts, Transactions transactions) {
        this.bankAccounts = bankAccounts;
        this.transactions = transactions;
    }

    public void execute (String sourceBankAccountNumber, String destinationBankAccountNumber, Money money){
        BankAccount sourceBankAccount = bankAccounts.findByAccountNumber(sourceBankAccountNumber);
        BankAccount destinationBankAccount = bankAccounts.findByAccountNumber(destinationBankAccountNumber);

        sourceBankAccount.withdraw(money);
        destinationBankAccount.deposit(money);

        bankAccounts.saveBalance(new BankAccountDTO(sourceBankAccountNumber,sourceBankAccount.getBalance()));
        transactions.saveByAccountNumber(new TransactionDTO(sourceBankAccountNumber
                ,money,sourceBankAccount.getBalance(), TRANSFERS.name()));
        bankAccounts.saveBalance(new BankAccountDTO(destinationBankAccountNumber, destinationBankAccount.getBalance()));
        transactions.saveByAccountNumber(new TransactionDTO(destinationBankAccountNumber
                                        ,money,destinationBankAccount.getBalance(), TRANSFERS.name()));
    }
}
