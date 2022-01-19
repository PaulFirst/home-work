package com.sbrf.reboot.atm;

import java.util.ArrayList;

public class Cassette<T extends Banknote> {

    ArrayList<T> banknotes = new ArrayList<>();

    public Cassette(ArrayList<T> banknotes) {
        this.banknotes.addAll(banknotes);
    }

    public int getCountBanknotes() {
        return banknotes.size();
    }
}
