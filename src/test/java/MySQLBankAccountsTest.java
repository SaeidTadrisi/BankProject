import infrastracture.MySQLBankAccounts;
import infrastracture.MySQLTransactions;
import model.*;
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

    private static String host;
    private static String user;
    private static String pass;

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
                (new Profile("Saeid", "Tadrisi","30217587","09123654789"),
                        new Money(new BigDecimal(50_000), CurrencyTypes.EURO)));
    }

    @Test
    void should_find_a_bank_account() {
        BankAccount byAccountNumber = bankAccounts.findByAccountNumber("7389866034");
        System.out.println(byAccountNumber);
    }

    @Test
    void should_make_deposit() {
        Deposit deposit = new Deposit(bankAccounts, transactions);
        deposit.execute("7389866034", new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_make_withdraw() {
        Withdraw withdraw = new Withdraw(bankAccounts, transactions);
        withdraw.execute("7389866034",new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_transfer() {
        Transfer transfer = new Transfer(bankAccounts, transactions);
        transfer.execute("7389866034","5331799194", new Money(new BigDecimal(10_000), CurrencyTypes.EURO));
    }

    @Test
    void should_return_account_balance() {
        RetrieveBalance retrieveBalance = new RetrieveBalance(bankAccounts);
        System.out.println(retrieveBalance.getAccountBalance("7389866034"));
    }

    @Test
    void should_return_transaction() {
        List<GetTransactionDTO> transactions1 = transactions.getTransactions("7389866034");
        System.out.println(transactions1);

    }

    //    @AfterAll
//    static void afterAll() {
//        try (InputStream configFile = new FileInputStream("db-config.properties")) {
//            final Properties properties = new Properties();
//            properties.load(configFile);
//            host = properties.get("host").toString();
//            user = properties.get("user").toString();
//            pass = properties.get("pass").toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try (final Connection connection = getConnection(host, user, pass)) {
//            PreparedStatement foreignKeyDisable = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
//            PreparedStatement truncateAccounts = connection.prepareStatement("TRUNCATE TABLE accounts");
//            PreparedStatement truncateProfiles = connection.prepareStatement("TRUNCATE TABLE profiles");
//            PreparedStatement truncateTransactions = connection.prepareStatement("TRUNCATE TABLE transactions");
//            PreparedStatement foreignKeyEnable = connection.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
//            foreignKeyDisable.executeUpdate();
//            truncateProfiles.executeUpdate();
//            truncateAccounts.executeUpdate();
//            truncateTransactions.executeUpdate();
//            foreignKeyEnable.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
