package cn.jasper.java14.record.features.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

record Rectangle(@GreaterThanZero double length, @GreaterThanZero double width) {
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface GreaterThanZero {

}