package model;

import exceptions.BalanceException;
import exceptions.CustomerDetailsException;

import java.math.BigDecimal;
import java.util.Objects;

import static model.TransactionType.DEPOSIT;
import static model.TransactionType.WITHDRAW;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final String phoneNumber;
    private BigDecimal balance;
    private TransactionType transactionType;

    public Customer(String firstName, String lastName, String nationalId, String phoneNumber, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public void deposit(BigDecimal amount){
        transactionType = DEPOSIT;
        balance = balance.add(amount);
    }

    public void withdraw(BigDecimal amount){
        transactionType = WITHDRAW;
        if (balance.compareTo(amount) < 0 ){
            throw new BalanceException("Your account balance is insufficient");
        }
        balance = balance.subtract(amount);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void check () {
        if (firstName == null || firstName.isEmpty()) {
            throw new CustomerDetailsException("You must enter the name");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new CustomerDetailsException("You must enter the last name");
        }
        if (nationalId == null || nationalId.isEmpty()) {
            throw new CustomerDetailsException("You must enter the email address");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new CustomerDetailsException("You must enter the phone number");
        }
        if (balance == null) {
            throw new CustomerDetailsException("You must enter the balance");
        }
        if (nationalId.length() != 8 ){
            throw new CustomerDetailsException("Your National Id must be 8 digits");
        }
        if (phoneNumber.length() != 11){
            throw new CustomerDetailsException("Your Phone number must be 11 digits");
        }
    }
    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && nationalId.equals(customer.nationalId) && phoneNumber.equals(customer.phoneNumber) && balance.equals(customer.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nationalId, phoneNumber, balance);
    }
}
