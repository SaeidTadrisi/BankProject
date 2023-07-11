package use_case;

import model.Customer;

public interface CustomerRepository {

    Customer findByNationalId (String nationalId);
}
