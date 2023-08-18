package infrastracture;

import java.sql.SQLException;

public class MainSQLException extends RuntimeException {
    public MainSQLException(SQLException message) {
    }
}
