import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static model.CurrencyTypes.EURO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private BankAccounts bankAccounts;
    private Transactions transactions;
    @BeforeEach
    void setUp() {
        bankAccounts = new FakeBankAccounts();
        transactions = new FakeTransactions();
    }

    @Test
    void should_check_customer() {

        Profile profile1 = new Profile("Omid", "",
                "69236741", "09174568219");

        Profile profile2 = new Profile("Omid", "",
                "69236741", "09174568219");

        Profile profile3 = new Profile("Omid", "Moradi",
                "", "09174568219");

        Profile profile4 = new Profile("Omid", "Moradi",
                "69236741", "");

        Profile profile5 = new Profile("Omid", "Moradi",
                "69236741", "09174568219");

        Profile profile6 = new Profile("Omid", "Moradi",
                "6926741", "09174568219");

        Profile profile7 = new Profile("Omid", "Moradi",
                "69236741", "091745668219");

        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile1, new Money(new BigDecimal(5_000_000), EURO)));
        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile2, new Money(new BigDecimal(5_000_000), EURO)));
        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile3, new Money(new BigDecimal(5_000_000), EURO)));
        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile4, new Money(new BigDecimal(5_000_000), EURO)));
        assertThrows(InvalidAmountEntryException.class,
                ()-> new BankAccount(profile5, new Money(null, EURO)));
        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile6, new Money(new BigDecimal(5_000_000), EURO)));
        assertThrows(CustomerDetailsException.class,
                ()-> new BankAccount(profile7, new Money(new BigDecimal(5_000_000), EURO)));
    }

    @Test
    void should_make_deposit() {

        Deposit deposit = new Deposit(bankAccounts, transactions);

        deposit.execute("1298574125", new Money(new BigDecimal(5_000), EURO));

        assertThat(bankAccounts.findByAccountNumber("1298574125").getBalance())
                .isEqualTo(new BigDecimal(55_000));
    }

    @Test
    void should_make_withdraw() {

        Withdraw withdraw = new Withdraw(bankAccounts, transactions);

        withdraw.execute("1298574125", new Money(new BigDecimal(20_000), EURO));

        assertThat(bankAccounts.findByAccountNumber("1298574125").getBalance())
                .isEqualTo(new BigDecimal(30_000));
    }

    @Test
    void should_make_transfer() {

        Transfer transfer = new Transfer(bankAccounts, transactions);

        transfer.execute("1298574125", "3247851238",
                new Money(new BigDecimal(10_000), EURO));

        assertThat(bankAccounts.findByAccountNumber("1298574125").getBalance()).isEqualTo(new BigDecimal(40_000));
        assertThat(bankAccounts.findByAccountNumber("3247851238").getBalance()).isEqualTo(new BigDecimal(20_000));
    }

    @Test
    void should_get_account_balance() {

        RetrieveBalance retrieveBalance = new RetrieveBalance(bankAccounts);

        BigDecimal accountBalance = retrieveBalance.getAccountBalance("1298574125");

        assertThat(accountBalance).isEqualTo(new BigDecimal(50_000));

    }

    @Test
    void should_throw_balance_exception() {

        Withdraw withdraw = new Withdraw(bankAccounts, transactions);

        assertThrows(InsufficientAccountBalanceException.class,
                ()-> withdraw.execute("1298574125", new Money(new BigDecimal(60_000), EURO)));
    }

    @Test
    void should_throw_various_currency_exception() {
        Transfer transfer = new Transfer(bankAccounts, transactions);

        assertThrows(VariousCurrencyException.class,
                ()-> transfer.execute("1298574125", "3512579654",
                        new Money(new BigDecimal(10_000), EURO)));

        assertThrows(VariousCurrencyException.class,
                ()-> transfer.execute("3512579654", "1298574125",
                        new Money(new BigDecimal(10_000), EURO)));
    }

    @Test
    void should_get_transaction_history() {
        List<TransactionDTO> byAccountNumber = transactions.getTransactions("1298574125");

        List<TransactionDTO> expectedList = new ArrayList<>();
        expectedList.add(new TransactionDTO("1298574125", new BigDecimal(10_000), EURO.name()
                , new BigDecimal(50_000),"DEPOSIT" ));

        expectedList.add(new TransactionDTO("1298574125", new BigDecimal(10_000), EURO.name()
                , new BigDecimal(50_000),"Withdraw" ));

        expectedList.add(new TransactionDTO("1298574125",new BigDecimal(10_000), EURO.name()
                , new BigDecimal(50_000),"Transfer" ));

        assertThat(byAccountNumber.toString()).isEqualTo(expectedList.toString());
    }
}