package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerH2RepositoryTest {

    private static CustomerRepository customerRepository;

    @BeforeAll
    public static void before() {
        customerRepository = new CustomerH2Repository();
    }

    @Test
    void getAll() {
        boolean tomCreated = customerRepository.createCustomer("Tom", "tom@ya.ru");

        List<Customer> all = customerRepository.getAll();

        assertTrue(all.size() != 0);
    }

    @Test
    void createCustomer() {

        boolean mariaCreated = customerRepository.createCustomer("Maria", "maria98@ya.ru");

        assertTrue(mariaCreated);
    }

    @Test
    void updateCustomer() {

        boolean customerCreated = customerRepository.createCustomer("Bob", "bob12@mail.ru");
        assertTrue(customerCreated);

        List<Customer> customersFirst = customerRepository.getAll();

        boolean customerUpdated = customerRepository.updateCustomer("John",
                "john7@mail.ru", "Bob", "bob12@mail.ru");
        assertTrue(customerUpdated);

        List<Customer> customersSecond = customerRepository.getAll();

        assertTrue(customersFirst.size() == customersSecond.size() &&
                !customersFirst.containsAll(customersSecond) && !customersSecond.containsAll(customersFirst));

        boolean customerUpdatedBack = customerRepository.updateCustomer("Bob",
                "bob12@mail.ru", "John", "john7@mail.ru");
        assertTrue(customerUpdatedBack);

        List<Customer> customersThird = customerRepository.getAll();

        assertTrue(customersFirst.size() == customersThird.size() &&
                customersFirst.containsAll(customersThird) && customersThird.containsAll(customersFirst));

    }

    @Test
    void deleteCustomer() {
        boolean customerCreated = customerRepository.createCustomer("temporal", "temp@mail.ru");
        assertTrue(customerCreated);

        List<Customer> customersFirst = customerRepository.getAll();

        boolean customerDeleted = customerRepository.deleteCustomer("temporal", "temp@mail.ru");
        assertTrue(customerDeleted);

        List<Customer> customersLast = customerRepository.getAll();

        assertEquals(customersFirst.size() - 1, customersLast.size());
    }
}