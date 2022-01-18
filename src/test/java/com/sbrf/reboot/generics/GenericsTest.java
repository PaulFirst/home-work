package com.sbrf.reboot.generics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericsTest {

    @Test
    public void getGenericInfo() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        String fullName = firstName + " " + lastName;

        InfoInterface<String> client = new Client(firstName, lastName);
        Assertions.assertEquals(fullName, client.getInfo());

        List<Integer> accountIds = new ArrayList<>();
        accountIds.add(12);
        accountIds.add(36);
        accountIds.add(15);

        InfoInterface<List<Integer>> repository1 = new Repository<>(accountIds);
        Assertions.assertEquals(accountIds, repository1.getInfo());

        int amountOfClients = 1047;
        InfoInterface<Integer> repository2 = new Repository<>(amountOfClients);
        Assertions.assertEquals(amountOfClients, repository2.getInfo());

    }
}
