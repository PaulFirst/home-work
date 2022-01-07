package com.sbrf.reboot.repository;

import java.util.Set;

public interface AccountRepository {

    Set<Long> getAllAccountsByClientId(long clientId);

    String getUserAgreementDateByClientId(long clientId);
}