import exceptions.BalanceException;
import exceptions.DuplicateRecordFoundException;
import exceptions.MainSQLException;
import exceptions.RecordNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;


public class BankDAOImpl implements BankDAO{

    private String host;
    private String user;
    private String pass;
    public static final String SAVE_Customer_SQL = "INSERT INTO customer (first_name,last_name,email_address,phone_number,balance) VALUES (?,?,?,?,?)";
    public static final String MAKE_DEPOSIT_SQL = "UPDATE customer SET balance = ? WHERE phone_number = ?";
    public static final String GET_AMOUNT_SQL = "SELECT balance FROM customer WHERE phone_number = ?";
    public static final String DUPLICATE_SEARCH_BY_PHONE_NUMBER_SQL = "SELECT COUNT(*) FROM customer WHERE phone_number = ?";
    public static final String WITHDRAW_SQL = "UPDATE customer SET balance = ? WHERE phone_number = ?";


    @Override
    public void saveCustomer(Customer customer) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            if (duplicateCheck(customer.getPhoneNumber())) {
                throw new DuplicateRecordFoundException("The Customer has already been saved and is a duplicate");
            } else {
                PreparedStatement saveCustomer = connection.prepareStatement(SAVE_Customer_SQL);
                saveCustomer.setString(1, customer.getFirstName());
                saveCustomer.setString(2, customer.getLastName());
                saveCustomer.setString(3, customer.getEmailAddress());
                saveCustomer.setString(4, customer.getPhoneNumber());
                saveCustomer.setBigDecimal(5, customer.getBalance());
                saveCustomer.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void deposit (BigDecimal amount, String phoneNumber) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement makeDeposit = connection.prepareStatement(MAKE_DEPOSIT_SQL)) {
            BigDecimal accountBalance = getAccountBalance(phoneNumber);
            BigDecimal result = accountBalance.add(amount);
            makeDeposit.setBigDecimal(1,result);
            makeDeposit.setString(2, phoneNumber);
            makeDeposit.executeUpdate();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void withdraw(BigDecimal amount, String phoneNumber) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement withdraw = connection.prepareStatement(WITHDRAW_SQL)){
            BigDecimal accountBalance = getAccountBalance(phoneNumber);
            int comparisonResult = accountBalance.compareTo(amount);
            if (comparisonResult < 0){
            throw new BalanceException("Your account balance is insufficient");

            }else {
                BigDecimal result = accountBalance.subtract(amount);
                withdraw.setBigDecimal(1, result);
                withdraw.setString(2, phoneNumber);
                withdraw.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getAccountBalance(String phoneNumber) {
        loadConfigFile();
        BigDecimal balance;
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement getAmount = connection.prepareStatement(GET_AMOUNT_SQL)) {
            getAmount.setString(1,phoneNumber);
            ResultSet resultSet = getAmount.executeQuery();
            if (resultSet.next()){
                balance = resultSet.getBigDecimal("balance");
            }else {
                throw new RecordNotFoundException();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
        return balance;
    }

    boolean duplicateCheck (String phoneNumber){
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement check = connection.prepareStatement(DUPLICATE_SEARCH_BY_PHONE_NUMBER_SQL)){
            check.setString(1,phoneNumber);
            ResultSet resultSet = check.executeQuery();

            if (resultSet.next()){
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            throw new MainSQLException(e);

        }return false;
    }

    private void loadConfigFile() {
        try (InputStream configFile = new FileInputStream("db-config.properties")){
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