package com.sbrf.reboot.dto;

public class Request {
    String atmNumber;

    public Request(String atmNumber) {
        this.atmNumber = atmNumber;
    }

    public Request() {}

    public String getAtmNumber() {
        return atmNumber;
    }
}
