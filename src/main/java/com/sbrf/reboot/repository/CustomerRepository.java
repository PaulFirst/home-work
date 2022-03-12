package com.sbrf.reboot.repository;

import com.sbrf.reboot.dto.Customer;
import lombok.NonNull;

import java.util.List;

public interface CustomerRepository {

    boolean createCustomer(@NonNull String userName, String eMail);

    List<Customer> getAll();

    boolean deleteCustomer(@NonNull String userName, String eMail);

    boolean updateCustomer(@NonNull String newUserName, String newEMail, @NonNull String oldUserName, String oldEMail);

}
