package com.sbrf.reboot.generics;

public class Repository<T> implements InfoProvider<T> {
    T data;

    public Repository(T data) {
        this.data = data;
    }

    @Override
    public T getInfo() {
        return data;
    }
}
