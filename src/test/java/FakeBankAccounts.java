import model.BankAccount;
import model.BankAccounts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static model.CurrencyTypes.EURO;
import static model.CurrencyTypes.USD;

public class FakeBankAccounts implements BankAccounts {

    Map<String, BankAccount> bankAccounts;

    public FakeBankAccounts() {

        bankAccounts = new HashMap<>();

        BankAccount aliRezaei = new BankAccount("1298574125", EURO, new BigDecimal(50_000));
        BankAccount rezaJafari = new BankAccount("3512579654", USD, new BigDecimal(80_000));
        BankAccount omidMoradi = new BankAccount("3247851238", EURO, new BigDecimal(10_000));

        bankAccounts.put("1298574125",aliRezaei);
        bankAccounts.put("3512579654",rezaJafari);
        bankAccounts.put("3247851238",omidMoradi);
    }

    @Override
    public void saveNewCustomer(BankAccount bankAccount) {

    }

    public BankAccount findByAccountNumber(String accountNumber) {
        return bankAccounts.get(accountNumber);
    }

}
