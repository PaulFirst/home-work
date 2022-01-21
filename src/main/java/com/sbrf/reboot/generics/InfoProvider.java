package com.sbrf.reboot.generics;

public interface InfoProvider<T> {

    //Возвращение данных типа Т
    T getInfo();
}
