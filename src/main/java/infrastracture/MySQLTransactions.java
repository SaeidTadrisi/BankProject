package infrastracture;

import use_case.TransactionDTO;
import use_case.Transactions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

public class MySQLTransactions implements Transactions {
    private String host;
    private String user;
    private String pass;

    public static final String SELECT_SQL = "SELECT t.*,a.balance "+
            "FROM transactions t " +
            "JOIN accounts a ON t.account_number = a.account_number " +
            "WHERE t.account_number = ?";

    public static final String SAVE_TRANSACTION = "INSERT INTO " +
            "transactions (account_number,transaction_type,currency_type," +
            " amount) VALUES (?, ?, ?, ?)";

    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
            saveTransaction.setString(1, transactionDTO.getAccountNumber());
            saveTransaction.setString(2, transactionDTO.getTransactionType());
            saveTransaction.setString(3, transactionDTO.getCurrencyType());
            saveTransaction.setBigDecimal(4, transactionDTO.getAmount());
            saveTransaction.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TransactionDTO> getTransactions(String accountNumber) {
        List<TransactionDTO> transactionHistory = new ArrayList<>();
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             final PreparedStatement select = connection.prepareStatement(SELECT_SQL)) {
            select.setString(1, accountNumber);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                TransactionDTO transactionDTO = new TransactionDTO(
                        accountNumber, resultSet.getBigDecimal("amount"),
                        resultSet.getString("currency_type"),
                        resultSet.getBigDecimal("balance"),
                        resultSet.getString("transaction_type"));
                transactionHistory.add(transactionDTO);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return transactionHistory;
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
