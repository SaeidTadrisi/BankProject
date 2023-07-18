package use_case;

import model.BankAccount;
import model.Money;

import static model.TransactionType.WITHDRAW;

public class MakeWithdraw {

    private final BankAccounts bankAccounts;
    private final Transactions transactions;

    public MakeWithdraw(BankAccounts bankAccounts,Transactions transactions) {
        this.bankAccounts = bankAccounts;
        this.transactions = transactions;
    }

    public void execute(String accountNumber, Money money){

        BankAccount bankAccount = bankAccounts.findByAccountNumber(accountNumber);

        bankAccount.withdraw(money);

        bankAccounts.saveBalance(new BankAccountDTO(accountNumber,bankAccount.getBalance()));
        transactions.saveByAccountNumber(new TransactionDTO(accountNumber,money,bankAccount.getBalance(),
                                         WITHDRAW.name()));
    }
}
