package infrastracture;

import model.TransactionType;
import use_case.GetTransactionDTO;
import use_case.SaveTransactionDTO;
import model.Transactions;

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

    public static final String SAVE_BALANCE =
            "UPDATE accounts SET balance = ? WHERE account_number = ?";

    public List<GetTransactionDTO> getTransactions(String accountNumber) {
        List<GetTransactionDTO> transactionHistory = new ArrayList<>();
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass);
             final PreparedStatement select = connection.prepareStatement(SELECT_SQL)) {
            select.setString(1, accountNumber);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                GetTransactionDTO getTransactionDTO = new GetTransactionDTO(
                        accountNumber, resultSet.getBigDecimal("amount"),
                        resultSet.getString("currency_type"),
                        resultSet.getBigDecimal("balance"),
                        resultSet.getString("transaction_type"));
                transactionHistory.add(getTransactionDTO);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return transactionHistory;
    }

    @Override
    public void saveToDatabase(SaveTransactionDTO saveTransactionDTO, TransactionType transactionType) {
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement saveBalance = connection.prepareStatement(SAVE_BALANCE);
            saveBalance.setBigDecimal(1, saveTransactionDTO.getBalance());
            saveBalance.setString(2, saveTransactionDTO.getAccountNumber());
            saveBalance.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        loadConfigFile();
        try (final Connection connection = getConnection(host, user, pass)) {
            PreparedStatement saveTransaction = connection.prepareStatement(SAVE_TRANSACTION);
            saveTransaction.setString(1, saveTransactionDTO.getAccountNumber());
            saveTransaction.setString(2, transactionType.name());
            saveTransaction.setString(3, saveTransactionDTO.getCurrencyType());
            saveTransaction.setBigDecimal(4, saveTransactionDTO.getAmount());
            saveTransaction.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
