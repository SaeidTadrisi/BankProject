package model;

import exceptions.CustomerDetailsException;

public class Profile {

    private final String fistName;
    private final String lastName;
    private final String nationalId;
    private final String phoneNumber;

    public Profile(String fistName, String lastName, String nationalId, String phoneNumber) {

        this.fistName = fistName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }


    void check(){
        if (getFirstName() == null || getFirstName().isEmpty()) {
            throw new CustomerDetailsException("You must enter the name");
        }
        if (getLastName() == null || getLastName().isEmpty()) {
            throw new CustomerDetailsException("You must enter the last name");
        }
        if (getNationalId() == null || getNationalId().isEmpty()) {
            throw new CustomerDetailsException("You must enter the email address");
        }
        if (getPhoneNumber() == null || getPhoneNumber().isEmpty()) {
            throw new CustomerDetailsException("You must enter the phone number");
        }
        if (getNationalId().length() != 8 ){
            throw new CustomerDetailsException("Your National Id must be 8 digits");
        }
        if (getPhoneNumber().length() != 11){
            throw new CustomerDetailsException("Your Phone number must be 11 digits");
        }

    }
    public String getFirstName() {
        return fistName;
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
}
