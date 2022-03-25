package cn.jasper.java14.record.members.accessor;


import com.google.common.base.Objects;

public record Rectangle(double length, double width) {

    // Public accessor method
    @Override
    public double length() {
        System.out.println("Length is " + length);
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return Double.compare(rectangle.length, length) == 0 && Double.compare(rectangle.width, width) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(length, width);
    }
}
