import exceptions.EmptyOrNullException;

import java.math.BigDecimal;

public class Customer {

    private String firstName;
    private String lastName;
    private String nationalId;
    private String phoneNumber;

    private BigDecimal balance;

    private int id;

    public Customer(String firstName, String lastName, String nationalId, String phoneNumber, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
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

    public void nullCheck() {
        if (firstName == null || firstName.isEmpty()) {
            throw new EmptyOrNullException("You must enter the name");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new EmptyOrNullException("You must enter the last name");
        }
        if (nationalId == null || nationalId.isEmpty()) {
            throw new EmptyOrNullException("You must enter the email address");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new EmptyOrNullException("You must enter the phone number");
        }
        if (balance == null) {
            throw new EmptyOrNullException("You must enter the balance");
        }
    }
}