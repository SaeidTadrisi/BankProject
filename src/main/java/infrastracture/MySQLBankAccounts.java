package infrastracture;

import model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class MySQLBankAccounts implements BankAccounts {
    private String host;
    private String user;
    private String pass;

    public static final String SAVE_PROFILE_SQL =
            "INSERT INTO profiles (account_number,first_name, last_name, national_id, phone_number) " +
            "VALUES (?, ?, ?, ?,?);";

    public static final String SAVE_ACCOUNT_SQL =
            "INSERT INTO accounts (account_number, currency_type, balance) " +
            "VALUES (?, ?, ?);";

    public static final String SAVE_TRANSACTION = "INSERT INTO " +
            "transactions (account_number,transaction_type,currency_type," +
            " amount) VALUES (?, ?, ?, ?)";
    public static final String SELECT_BANK_ACCOUNT_BY_ACCOUNT_NUMBER =
            "SELECT currency_type, balance FROM accounts WHERE account_number = ?";

    public static final String DUPLICATE_SEARCH_SQL = "SELECT COUNT(*) FROM profiles WHERE national_Id = ?";

    @Override
    public void saveNewCustomer(BankAccount bankAccount) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            if (duplicateCheck(bankAccount.getNationalId())) {
                throw new DuplicateRecordFoundException();
            } else {
                PreparedStatement saveAccountStatement = connection.prepareStatement(SAVE_ACCOUNT_SQL);
                saveAccountStatement.setString(1, bankAccount.getAccountNumber());
                saveAccountStatement.setString(2, bankAccount.getCurrencyType().name());
                saveAccountStatement.setBigDecimal(3, bankAccount.getAmount());
                saveAccountStatement.executeUpdate();

                PreparedStatement saveProfileStatement = connection.prepareStatement(SAVE_PROFILE_SQL);
                saveProfileStatement.setString(1, bankAccount.getAccountNumber());
                saveProfileStatement.setString(2, bankAccount.getFirstName());
                saveProfileStatement.setString(3, bankAccount.getLastName());
                saveProfileStatement.setString(4, bankAccount.getNationalId());
                saveProfileStatement.setString(5, bankAccount.getPhoneNumber());
                saveProfileStatement.executeUpdate();

                PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
                saveTransaction.setString(1, bankAccount.getAccountNumber());
                saveTransaction.setString(2, TransactionType.DEPOSIT.name());
                saveTransaction.setString(3, bankAccount.getFirstDeposit().getCurrencyType().name());
                saveTransaction.setBigDecimal(4, bankAccount.getFirstDeposit().getAmount());
                saveTransaction.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement selectProfile = connection.prepareStatement(SELECT_BANK_ACCOUNT_BY_ACCOUNT_NUMBER);
            selectProfile.setString(1, accountNumber);
            ResultSet resultSet = selectProfile.executeQuery();
            if (resultSet.next()) {
                String currencyType = resultSet.getString("currency_type");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                return new BankAccount(accountNumber, CurrencyTypes.valueOf(currencyType), balance);
            } else {
                throw new RecordNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean duplicateCheck(String national_id) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement check = connection.prepareStatement(DUPLICATE_SEARCH_SQL)) {
            check.setString(1, national_id);
            ResultSet resultSet = check.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
        return false;
    }

    private void loadConfigFile() {
        try (InputStream configFile = new FileInputStream("db-config.properties")) {
            final Properties properties = new Properties();
            properties.load(configFile);
            host = properties.get("host").toString();
            user = properties.get("user").toString();
            pass = properties.get("pass").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
