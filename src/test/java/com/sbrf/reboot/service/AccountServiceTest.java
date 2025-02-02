package com.sbrf.reboot.service;

import com.sbrf.reboot.AccountService;
import com.sbrf.reboot.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountService(accountRepository);
    }

    @Test
    void contractExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(111L);

        long clientId = 1L;
        long contractNumber = 111L;


        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertTrue(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void contractNotExist() {
        Set<Long> accounts = new HashSet();
        accounts.add(222L);

        long clientId = 1L;
        long contractNumber = 111L;

        when(accountRepository.getAllAccountsByClientId(clientId)).thenReturn(accounts);

        assertFalse(accountService.isClientHasContract(clientId, contractNumber));
    }

    @Test
    void repositoryHasTreeMethods() {
        assertEquals(3, AccountRepository.class.getMethods().length);
    }

    @Test
    void serviceHasTreeMethods() {
        assertEquals(2, AccountService.class.getMethods().length - Object.class.getMethods().length);
    }

    @Test
    void agreementRelevant() {
        long clientId = 4L;
        when(accountRepository.getUserAgreementDateByClientId(clientId)).thenReturn("02.04.2018");

        assertTrue(accountService.isClientAgreementRelevant(clientId, 5));
    }

    @Test
    void agreementIrrelevant() {
        long clientId = 5L;
        when(accountRepository.getUserAgreementDateByClientId(clientId)).thenReturn("15.09.2019");

        assertFalse(accountService.isClientAgreementRelevant(clientId, 1));
    }
}