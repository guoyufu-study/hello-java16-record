package cn.jasper.java14.record.constructor.compact;

public record Rectangle(double length, double width) {

    public Rectangle {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                    String.format("Invalid dimensions: %f, %f", length, width));
        }
    }
}
