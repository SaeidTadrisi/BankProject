import model.BankAccount;
import model.Money;
import model.Profile;
import model.BankAccounts;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static model.CurrencyTypes.EURO;
import static model.CurrencyTypes.USD;

public class FakeBankAccounts implements BankAccounts {

    Map<String, BankAccount> customers;

    public FakeBankAccounts() {

        customers = new HashMap<>();

        BankAccount aliRezaei = new BankAccount(new Profile("Ali", "Rezaei",
                "25874136", "09123698514"), new Money(new BigDecimal(50_000), EURO));
        BankAccount rezaJafari = new BankAccount(new Profile("Reza", "Jafari",
                "76925413", "09136149285"),new Money(new BigDecimal(80_000), USD));
        BankAccount omidMoradi = new BankAccount(new Profile("Omid", "Moradi",
                "69236741", "09174568219"),new Money(new BigDecimal(10_000), EURO));

        customers.put("1298574125",aliRezaei);
        customers.put("3512579654",rezaJafari);
        customers.put("3247851238",omidMoradi);
    }

    @Override
    public void saveNewCustomer(BankAccount bankAccount) {

    }

    public BankAccount findByAccountNumber(String accountNumber) {
        return customers.get(accountNumber);
    }

}
