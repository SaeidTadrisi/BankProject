package use_case;

import model.BankAccount;
import model.BankAccounts;
import model.Money;
import model.Transactions;

import static model.TransactionType.DEPOSIT;

public class Deposit {
    private final BankAccounts bankAccounts;
    private final Transactions transactions;


    public Deposit(BankAccounts bankAccounts, Transactions transactions) {
    this.bankAccounts = bankAccounts;
    this.transactions = transactions;
    }

    public void execute (String accountNumber, Money money){
        BankAccount bankAccount = bankAccounts.findByAccountNumber(accountNumber);

        bankAccount.deposit(money);

        transactions.saveToDatabase(new SaveTransactionDTO(bankAccount, money), accountNumber, DEPOSIT);
    }
}
