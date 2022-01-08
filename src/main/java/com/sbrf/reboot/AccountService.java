package com.sbrf.reboot;

import com.sbrf.reboot.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository repository;

    public boolean isClientHasContract(long clientId, long contractNumber) {
        return repository.getAllAccountsByClientId(clientId).contains(contractNumber);
    }

    public boolean isClientAgreementRelevant(long clientId, int relevantPeriod) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int relevantPeriodInDays = relevantPeriod * 365;

        String agreementDate = repository.getUserAgreementDateByClientId(clientId);

        LocalDate parsedAgreementDate = LocalDate.parse(agreementDate, dtf);
        LocalDate currentDate = LocalDate.now();

        long daysBetween = ChronoUnit.DAYS.between(parsedAgreementDate, currentDate);
        return daysBetween < relevantPeriodInDays;
    }
}
