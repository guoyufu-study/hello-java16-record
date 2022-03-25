package cn.jasper.java14.record.features.imps;

record Customer(String name, byte age) implements Billable {
}

interface Billable {}
