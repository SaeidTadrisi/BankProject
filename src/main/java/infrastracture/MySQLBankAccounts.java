package infrastracture;

import model.BankAccount;
import model.CurrencyTypes;
import model.Money;
import model.Profile;
import use_case.BankAccountDTO;
import use_case.BankAccounts;

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

    public static final String SELECT_CUSTOMER_BY_ACCOUNT_NUMBER =
            "SELECT a.currency_type, a.balance, p.first_name, p.last_name, p.national_id, p.phone_number " +
                    "FROM accounts a JOIN profiles p ON a.account_number = p.account_number " +
                    "WHERE a.account_number = ?";

    public static final String SAVE_BALANCE =
            "UPDATE accounts SET balance = ? WHERE account_number = ?";

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
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement selectProfile = connection.prepareStatement(SELECT_CUSTOMER_BY_ACCOUNT_NUMBER);
            selectProfile.setString(1, accountNumber);
            ResultSet resultSet = selectProfile.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String nationalId = resultSet.getString("national_id");
                String phoneNumber = resultSet.getString("phone_number");
                String currencyType = resultSet.getString("currency_type");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                return new BankAccount(new Profile(firstName,lastName,nationalId,phoneNumber)
                        , new Money(balance, CurrencyTypes.valueOf(currencyType)));
            } else {
                throw new RecordNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveBalance(BankAccountDTO bankAccountDTO) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
                PreparedStatement saveBalance = connection.prepareStatement(SAVE_BALANCE);
                saveBalance.setBigDecimal(1, bankAccountDTO.getBalance());
                saveBalance.setString(2, bankAccountDTO.getAccountNumber());
                saveBalance.executeUpdate();
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
