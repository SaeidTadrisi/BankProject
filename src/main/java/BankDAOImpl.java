import exceptions.BalanceException;
import exceptions.DuplicateRecordFoundException;
import exceptions.MainSQLException;
import exceptions.RecordNotFoundException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;


public class BankDAOImpl implements BankDAO {

    public static final String SELECT_SQL = "SELECT t.*, c.first_name, c.last_name " +
                                            "FROM transactions t " +
                                            "JOIN customers c ON t.customer_national_Id = c.national_Id " +
                                            "WHERE t.customer_national_Id = ?";
    private String host;
    private String user;
    private String pass;
    public static final String SAVE_Customer_SQL = "INSERT INTO customers (first_name,last_name,national_Id,phone_number,balance) VALUES (?,?,?,?,?)";
    public static final String MAKE_DEPOSIT_SQL = "UPDATE customers SET balance = ? WHERE national_Id = ?";
    public static final String SAVE_TRANSACTION = "INSERT INTO transactions (customer_national_id, amount, transaction_type, customer_balance) VALUES (?, ?, ?,?)";
    public static final String WITHDRAW_SQL = "UPDATE customers SET balance = ? WHERE national_Id = ?";
    public static final String GET_AMOUNT_SQL = "SELECT balance FROM customers WHERE national_Id = ?";
    public static final String DUPLICATE_SEARCH_SQL = "SELECT COUNT(*) FROM customers WHERE national_Id = ?";


    @Override
    public void saveCustomer(Customer customer) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            if (duplicateCheck(customer.getNationalId())) {
                throw new DuplicateRecordFoundException("The Customer has already been saved and is a duplicate");
            } else {
                PreparedStatement saveCustomer = connection.prepareStatement(SAVE_Customer_SQL);
                saveCustomer.setString(1, customer.getFirstName());
                saveCustomer.setString(2, customer.getLastName());
                saveCustomer.setString(3, customer.getNationalId());
                saveCustomer.setString(4, customer.getPhoneNumber());
                saveCustomer.setBigDecimal(5, customer.getBalance());
                saveCustomer.executeUpdate();
            }
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void saveTransaction(String nationalId, BigDecimal amount, TransactionType transactionType) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            switch (transactionType) {
                case DEPOSIT -> {
                    PreparedStatement makeDeposit = connection.prepareStatement(MAKE_DEPOSIT_SQL);
                    BigDecimal accountBalance = getAccountBalance(nationalId);
                    BigDecimal result = accountBalance.add(amount);
                    makeDeposit.setBigDecimal(1, result);
                    makeDeposit.setString(2, nationalId);
                    makeDeposit.executeUpdate();

                    PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
                    saveTransaction.setString(1, nationalId);
                    saveTransaction.setBigDecimal(2, amount);
                    saveTransaction.setString(3, TransactionType.DEPOSIT.name());
                    saveTransaction.setString(4, result.toString());
                    saveTransaction.executeUpdate();
                }
                case WITHDRAWAL -> {
                    PreparedStatement withdraw = connection.prepareStatement(WITHDRAW_SQL);
                    BigDecimal accountBalance = getAccountBalance(nationalId);
                    int comparisonResult = accountBalance.compareTo(amount);

                    if (comparisonResult < 0) {
                        throw new BalanceException("Your account balance is insufficient");
                    } else {
                    BigDecimal result = accountBalance.subtract(amount);
                    withdraw.setBigDecimal(1, result);
                    withdraw.setString(2, nationalId);
                    withdraw.executeUpdate();

                    PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
                    saveTransaction.setString(1, nationalId);
                    saveTransaction.setBigDecimal(2, amount);
                    saveTransaction.setString(3, TransactionType.WITHDRAWAL.name());
                    saveTransaction.setString(4, result.toString());
                    saveTransaction.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal getAccountBalance(String nationalId) {
        loadConfigFile();
        BigDecimal balance;
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement getAmount = connection.prepareStatement(GET_AMOUNT_SQL)) {
            getAmount.setString(1,nationalId);
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

    @Override
    public List<Transaction> getAllTransactions(String nationalId) {
        loadConfigFile();
        List<Transaction> transactionHistory = new ArrayList<>();

        try (final Connection connection = getConnection(host,user,pass);
             final PreparedStatement  select = connection.prepareStatement(SELECT_SQL)){
                 select.setString(1,nationalId);
                 ResultSet resultSet = select.executeQuery();
                 while (resultSet.next()){
                     Transaction transaction = new Transaction();
                     transaction.setFirstName(resultSet.getString("first_name"));
                     transaction.setLastName(resultSet.getString("last_name"));
                     transaction.setNationalId(resultSet.getString("customer_national_id"));
                     transaction.setTransactionType(resultSet.getString("transaction_type"));
                     transaction.setAmount(resultSet.getBigDecimal("amount"));
                     transaction.setCustomerBalance(resultSet.getBigDecimal("customer_balance"));
                     transaction.setTransactionTime(String.valueOf(resultSet.getDate("transaction_time")));

                     transactionHistory.add(transaction);
                 }
       } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } return transactionHistory;
    }

    boolean duplicateCheck (String nationalId){
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement check = connection.prepareStatement(DUPLICATE_SEARCH_SQL)){
            check.setString(1,nationalId);
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