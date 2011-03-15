package com.saucelabs.demo;

@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface SeleniumVersion {

    int version();

    boolean local() default false;
}
