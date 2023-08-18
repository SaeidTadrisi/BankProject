package model;

public class Profile {

    private final String firstName;
    private final String lastName;
    private final String nationalId;
    private final String phoneNumber;

    public Profile(String firstName, String lastName, String nationalId, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
    }

    void check(){
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
        if (nationalId.length() != 8 ){
            throw new CustomerDetailsException("Your National Id must be 8 digits");
        }
        if (phoneNumber.length() != 11){
            throw new CustomerDetailsException("Your Phone number must be 11 digits");
        }
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

    @Override
    public String toString() {
        return "Profile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
