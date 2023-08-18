import infrastracture.MySQLBankAccounts;
import infrastracture.MySQLTransactions;
import model.BankAccount;
import model.CurrencyTypes;
import model.Money;
import model.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;
import static org.assertj.core.api.Fail.fail;

public class MySQLBankAccountsTest {

    BankAccounts bankAccounts;
    Transactions transactions;
    @BeforeEach
    void setUp() {
        bankAccounts = new MySQLBankAccounts();
        transactions = new MySQLTransactions();
    }

    @Test
    void should_connect_to_database() {
        try (final Connection connection = getConnection("jdbc:mysql://localhost:3306/bank", "bank", "Bank@1848")) {
            if (connection == null) {
                fail("Error");
            }
        } catch (SQLException e) {
            fail("Error");
        }
    }

    @Test
    void should_save_new_account() {
        bankAccounts.saveNewCustomer(new BankAccount
                (new Profile("Saeid", "Tadrisi","30214587","09123654789"),
                        new Money(new BigDecimal(50_000), CurrencyTypes.EURO)));
    }

    @Test
    void should_find_a_bank_account() {
        BankAccount byAccountNumber = bankAccounts.findByAccountNumber("9050442999");
        System.out.println(byAccountNumber);
    }

    @Test
    void should_make_deposit() {
        Deposit deposit = new Deposit(bankAccounts, transactions);
        deposit.execute("9050442999", new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_make_withdraw() {
        Withdraw withdraw = new Withdraw(bankAccounts, transactions);
        withdraw.execute("9050442999",new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_transfer() {
        Transfer transfer = new Transfer(bankAccounts, transactions);
        transfer.execute("8521478962","9050442999", new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_return_account_balance() {
        RetrieveBalance retrieveBalance = new RetrieveBalance(bankAccounts);
        System.out.println(retrieveBalance.getAccountBalance("8521478962"));
    }

    @Test
    void should_return_transaction() {
        List<TransactionDTO> transactions1 = transactions.getTransactions("9050442999");
        System.out.println(transactions1);

    }
}
