package com.sbrf.reboot.repository;

import java.util.Set;

public interface AccountRepository {

    Set<Long> getAllAccountsByClientId(long clientId);

    void updateAccountById(long clientId, long oldAccount, long newAccount);

    String getUserAgreementDateByClientId(long clientId);
}
