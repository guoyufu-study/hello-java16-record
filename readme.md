# record 类

> https://docs.oracle.com/en/java/javase/18/language/records.html
> 

## JEP395

record 类 [Rectangle](src/main/java/cn/jasper/java14/record/simple/Rectangle.java) 等同于：

```java
public final class Rectangle {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    double length() { return this.length; }
    double width()  { return this.width; }

    // Implementation of equals() and hashCode(), which specify
    // that two record objects are equal if they
    // are of the same type and contain equal field values.
//    public boolean equals...
//    public int hashCode...

    // An implementation of toString() that returns a string
    // representation of all the record class's fields,
    // including their names.
//    public String toString() {...}
}
```

## 规范构造函数

可以显式声明[规范构造函数](src/main/java/cn/jasper/java14/record/constructor/canonical/Rectangle.java)：

```java
record Rectangle(double length, double width) {
    public Rectangle(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                    String.format("Invalid dimensions: %f, %f", length, width));
        }
        this.length = length;
        this.width = width;
    }
}
```

可以显式声明[紧凑构造函数](src/main/java/cn/jasper/java14/record/constructor/compact/Rectangle.java)：

```java
record Rectangle(double length, double width) {
    public Rectangle {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                String.format("Invalid dimensions: %f, %f", length, width));
        }
    }
}
```

## 成员

可以显式声明[访问器方法](src/main/java/cn/jasper/java14/record/members/accessor/Rectangle.java)：

```java
record Rectangle(double length, double width) {
 
    // Public accessor method
    public double length() {
        System.out.println("Length is " + length);
        return length;
    }
}
```

同理，也可以显式声明 `equals`、`hashCode` 和 `toString` 方法。

可以声明[静态字段、静态初始化器和静态方法](src/main/java/cn/jasper/java14/record/members/statics/Rectangle.java)：

```java
record Rectangle(double length, double width) {
    
    // Static field
    static double goldenRatio;

    // Static initializer
    static {
        goldenRatio = (1 + Math.sqrt(5)) / 2;
    }

    // Static method
    public static Rectangle createGoldenRectangle(double width) {
        return new Rectangle(width, width * goldenRatio);
    }
}
```

但是，不允许声明实例变量（非静态字段），或者实例初化器。

```java
record Rectangle(double length, double width) {

    // Field declarations must be static:
    BiFunction<Double, Double, Double> diagonal;

    // Instance initializers are not allowed in records:
    {
        diagonal = (x, y) -> Math.sqrt(x*x + y*y);
    }
}
```
还可以声明[实例方法和内部类、内部接口、内部记录类](src/main/java/cn/jasper/java14/record/members/nested/Rectangle.java)：

```java
record Rectangle(double length, double width) {

    // Nested record class
    record RotationAngle(double angle) {
        public RotationAngle {
            angle = Math.toRadians(angle);
        }
    }
    
    // Public instance method
    public Rectangle getRotatedRectangleBoundingBox(double angle) {
        RotationAngle ra = new RotationAngle(angle);
        double x = Math.abs(length * Math.cos(ra.angle())) +
                   Math.abs(width * Math.sin(ra.angle()));
        double y = Math.abs(length * Math.sin(ra.angle())) +
                   Math.abs(width * Math.cos(ra.angle()));
        return new Rectangle(x, y);
    }
}
```
> 内部记录类隐式 `static` 
> 

但是不能声明 `native` 方法。

## 特性

隐式扩展自 `java.lang.Record`。 所以，不允许使用 `extends` 子句

隐式 `final`。 所以，不能被扩展，不能使用 `abstract` 关键字。

除此之外，record 类与普通类的行为没有区别：

可以创建[泛型记录](src/main/java/cn/jasper/java14/record/features/generic/Triangle.java)：

```java
record Triangle<C extends Coordinate> (C top, C left, C right) { }
```

可以[实现一个或多个接口](src/main/java/cn/jasper/java14/record/features/imps/Customer.java)：

```java
record Customer() implements Billable { }
```

可以[注解 record 类及其组件](src/main/java/cn/jasper/java14/record/features/annotate/Rectangle.java)：

```java
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GreaterThanZero { }
```

```java
record Rectangle(
    @GreaterThanZero double length,
    @GreaterThanZero double width) { }
```

## 本地记录类

[本地记录类](src/main/java/cn/jasper/java14/record/local/MerchantExample.java)类似于本地类，也是在方法体中定义。

与嵌套记录类一样，本地记录类是**隐式静态**的，这意味着它们自己的方法不能访问封闭方法的任何变量，这与本地类不同，本地类永远不是静态的。


## 内部类的静态成员

在 Java SE 16 之前，不能在内部类中显式或隐式声明静态成员，除非该成员是常量变量。这意味着内部类不能声明记录类成员，因为嵌套的记录类是隐式静态的。

在 Java SE 16 及更高版本中，内部类可以声明显式或隐式静态成员，其中包括记录类成员。

## 序列化

可以序列化和反序列化记录类的实例，但不能通过提供 `writeObject`, `readObject`, `readObjectNoData`, `writeExternal`, 或 `readExternal` 等方法，自定义流程。

记录类的组件控制序列化，而记录类的规范构造函数控制反序列化。

[Java 对象序列化规范中的记录序列化部分](https://docs.oracle.com/en/java/javase/18/docs/specs/serialization/serial-arch.html#serialization-of-records)




## 引用不明确

[引用不明确](src/main/java/cn/jasper/java14/record/imports)时，编译器会报错：
对 'Record' 的引用不明确，'cn.jasper.java14.record.imports.Record' 和 'java.lang.Record' 均匹配。


