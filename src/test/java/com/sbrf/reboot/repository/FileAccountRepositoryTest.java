package com.sbrf.reboot.repository;

import com.sbrf.reboot.repository.impl.FileAccountRepository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileAccountRepositoryTest {

    AccountRepository accountRepository;

    @Test
    void onlyPersonalAccounts() {
        String filePath = "src/main/resources/Accounts.txt";
        accountRepository = new FileAccountRepository(filePath);

        long clientId = 1L;
        Set<Long> actualAccounts = accountRepository.getAllAccountsByClientId(clientId);

        Set<Long> expected = new HashSet<Long>() {{
            add(111L);
            add(222L);
            add(333L);
        }};

        actualAccounts.forEach(e -> assertTrue(expected.contains(e)));
    }

    @Test
    void failGetAllAccountsByClientId() {
        long clientId = 1L;

        String filePath = "somePath";

        assertThrows(RuntimeException.class, () -> accountRepository = new FileAccountRepository(filePath));
    }

    @Test
    void failGetNonExistentClient() {
        String filePath = "src/main/resources/Accounts.txt";
        accountRepository = new FileAccountRepository(filePath);

        long clientId = 44L;

        assertThrows(RuntimeException.class, () -> accountRepository.getAllAccountsByClientId(clientId));
    }

    @Test
    void accountsCanBeUpdated() {
        String filePath = "src/main/resources/Accounts.txt";
        accountRepository = new FileAccountRepository(filePath);

        long clientId = 1L;

        accountRepository.updateAccountById(clientId, 222L, 524L);
        Set<Long> actualAccounts = accountRepository.getAllAccountsByClientId(clientId);

        Set<Long> expectedFirst = new HashSet<Long>() {{
            add(111L);
            add(524L);
            add(333L);
        }};

        actualAccounts.forEach(e -> assertTrue(expectedFirst.contains(e)));

        accountRepository.updateAccountById(clientId, 524L, 222L);
        actualAccounts = accountRepository.getAllAccountsByClientId(clientId);

        Set<Long> expectedSecond = new HashSet<Long>() {{
            add(111L);
            add(222L);
            add(333L);
        }};

        actualAccounts.forEach(e -> assertTrue(expectedSecond.contains(e)));
    }

    @Test
    void failUpdateNotExistentAccount() {
        String filePath = "src/main/resources/Accounts.txt";
        accountRepository = new FileAccountRepository(filePath);

        long clientId = 1L;

        assertThrows(RuntimeException.class, () -> accountRepository.updateAccountById(clientId, 225L, 444L));
    }

}