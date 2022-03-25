package cn.jasper.java14.record.imports.example;

import cn.jasper.java14.record.imports.Record;

public class Example {

    public static void main(String[] args) {
        Record record = new Record("Hello World!");
        System.out.println(record.greeting);
    }
}
