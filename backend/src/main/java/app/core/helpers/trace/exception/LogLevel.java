package app.core.helpers.trace.exception;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@InterceptorBinding
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogLevel {
    boolean logError() default false;

    boolean logInfo() default false;

    boolean logOff() default false;

    boolean logTrace() default false;

    boolean logDebug() default false;

    boolean logFatal() default false;

    boolean logAll() default false;
}
