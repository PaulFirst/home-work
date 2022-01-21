package com.sbrf.reboot.generics;

public class Client implements InfoProvider<String> {
    String firstName;
    String lastName;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String getInfo() {
        return firstName + " " + lastName;
    }
}
