package com.formulauno.service;

public interface IO {

    String inter(String code, Object... params);

    void interPrintln(String code, Object... params);

    void interPrint(String code, Object... params);

    void println(Object o);

    void print(Object o);

    String readLine();
}
