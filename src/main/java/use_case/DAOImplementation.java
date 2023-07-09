package use_case;

import exceptions.DuplicateRecordFoundException;
import exceptions.MainSQLException;
import exceptions.RecordNotFoundException;
import model.Customer;
import model.Transactions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class DAOImplementation implements DAORepository {

    private Customer customer;
    BigDecimal amount;
    private String host;
    private String user;
    private String pass;
    public static final String SELECT_SQL = "SELECT t.*, c.first_name, c.last_name " +
            "FROM transactions_history t " +
            "JOIN customers c ON t.customer_national_Id = c.national_Id " +
            "WHERE t.customer_national_Id = ?";

    public static final String SAVE_Customer_SQL = "INSERT INTO " +
            "customers (first_name,last_name,national_Id,phone_number,balance) VALUES (?,?,?,?,?)";

    public static final String MAKE_DEPOSIT_SQL = "UPDATE customers SET balance = ? WHERE national_Id = ?";

    public static final String SAVE_TRANSACTION = "INSERT INTO " +
            "transactions_history (customer_national_id, amount, transaction_type, customer_balance) VALUES (?, ?, ?,?)";

    public static final String WITHDRAW_SQL = "UPDATE customers SET balance = ? WHERE national_Id = ?";

    public static final String GET_CUSTOMER_BY_NATIONAL_ID = "SELECT *  FROM customers WHERE national_Id = ?";

    public static final String DUPLICATE_SEARCH_SQL = "SELECT COUNT(*) FROM customers WHERE national_Id = ?";

    @Override
    public void saveCustomer(Customer customer) {
        loadConfigFile();
        customer.check();
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
    public void deposit(String nationalId, BigDecimal amount) {
        this.amount = amount;
        findCustomerByNationalId(nationalId);
        customer.deposit(amount);

        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement makeDeposit = connection.prepareStatement(MAKE_DEPOSIT_SQL);
            makeDeposit.setBigDecimal(1, customer.getBalance());
            makeDeposit.setString(2, nationalId);
            makeDeposit.executeUpdate();

            saveTransaction();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    @Override
    public void withdraw(String nationalId, BigDecimal amount) {
        this.amount = amount;
        findCustomerByNationalId(nationalId);
        customer.withdraw(amount);

        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement withdraw = connection.prepareStatement(WITHDRAW_SQL);
            withdraw.setBigDecimal(1, customer.getBalance());
            withdraw.setString(2, nationalId);
            withdraw.executeUpdate();

            saveTransaction();
        } catch (SQLException e) {
            throw new MainSQLException(e);
        }
    }

    public void saveTransaction() {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
            saveTransaction.setString(1, customer.getNationalId());
            saveTransaction.setBigDecimal(2, amount);
            saveTransaction.setString(3, customer.getTransactionType().name());
            saveTransaction.setBigDecimal(4, customer.getBalance());
            saveTransaction.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer findCustomerByNationalId(String nationalId) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement selectCustomer = connection.prepareStatement(GET_CUSTOMER_BY_NATIONAL_ID);

            selectCustomer.setString(1, nationalId);
            ResultSet resultSet = selectCustomer.executeQuery();
            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phoneNumber = resultSet.getString("phone_number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                customer = new Customer(firstName, lastName, nationalId, phoneNumber, balance);
            } else {
                throw new RecordNotFoundException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public List<Transactions> getTransactions(String nationalId) {
        loadConfigFile();
        List<Transactions> transactionHistory = new ArrayList<>();

    try (final Connection connection = getConnection(host, user, pass);
         final PreparedStatement select = connection.prepareStatement(SELECT_SQL)) {
            select.setString(1, nationalId);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                Transactions transaction = new Transactions
                (resultSet.getString("first_name"), resultSet.getString("last_name"),
                resultSet.getString("customer_national_id"), resultSet.getString("transaction_type"),
                resultSet.getBigDecimal("amount"), resultSet.getBigDecimal("customer_balance"),
                resultSet.getString("transaction_time"));

                transactionHistory.add(transaction);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return transactionHistory;
    }

    @Override
    public BigDecimal getAccountBalance(String nationalId) {
        findCustomerByNationalId(nationalId);
        return customer.getBalance();
    }

    boolean duplicateCheck(String nationalId) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             PreparedStatement check = connection.prepareStatement(DUPLICATE_SEARCH_SQL)) {
            check.setString(1, nationalId);
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