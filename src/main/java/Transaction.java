import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private String firstName;
    private String lastName;
    private String nationalId;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal customerBalance;
    private String transactionTime;

    public Transaction setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Transaction setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Transaction setNationalId(String nationalId) {
        this.nationalId = nationalId;
        return this;
    }

    public Transaction setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Transaction setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
        return this;
    }

    public Transaction setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
        return this;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", customerBalance=" + customerBalance +
                ", transactionTime=" + transactionTime.toString() +
                '}'
                ;
    }
}
