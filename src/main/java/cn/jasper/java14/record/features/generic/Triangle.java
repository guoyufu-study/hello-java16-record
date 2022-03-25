package cn.jasper.java14.record.features.generic;

record Triangle<C extends Coordinate>(C top, C left, C right) {
}

record Coordinate(double x, double y) {
}

