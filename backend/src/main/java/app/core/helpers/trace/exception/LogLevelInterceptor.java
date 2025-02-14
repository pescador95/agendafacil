package app.core.helpers.trace.exception;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@LogLevel
public class LogLevelInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LogLevelInterceptor.class);

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {
        LogLevel annotation = context.getMethod().getAnnotation(LogLevel.class);

        if (annotation == null) {
            annotation = context.getMethod().getDeclaringClass().getAnnotation(LogLevel.class);
        }

        if (annotation != null && annotation.logOff()) {
            return context.proceed();
        }

        String methodName = context.getMethod().getDeclaringClass().getSimpleName() + "." + context.getMethod().getName();

        try {

            if (annotation != null && annotation.logAll()) {
                logMessage("🔹 Executando " + methodName, "INFO");
            } else {
                logBasedOnAnnotation(annotation, "🔹 Executando " + methodName);
            }

            Object result = context.proceed();

            if (annotation != null && annotation.logAll()) {
                logMessage("✅ " + methodName + " executado com sucesso!", "INFO");
            } else {
                logBasedOnAnnotation(annotation, "✅ " + methodName + " executado com sucesso!");
            }

            return result;
        } catch (Exception e) {
            String errorMessage = "❌ Erro em " + methodName + ": " + e.getMessage();

            if (annotation != null && annotation.logAll()) {
                logMessage(errorMessage, "ERROR");
            } else {
                logBasedOnAnnotation(annotation, errorMessage);
            }

            throw e;
        }
    }

    private void logBasedOnAnnotation(LogLevel annotation, String message) {
        if (annotation == null) return;

        if (annotation.logError()) logMessage(message, "ERROR");
        if (annotation.logInfo()) logMessage(message, "INFO");
        if (annotation.logTrace()) logMessage(message, "TRACE");
        if (annotation.logDebug()) logMessage(message, "DEBUG");
        if (annotation.logFatal()) logMessage(message, "FATAL");
    }

    private void logMessage(String message, String level) {
        switch (level) {
            case "ERROR":
                if (LOGGER.isEnabled(Logger.Level.ERROR)) LOGGER.error(message);
                break;
            case "INFO":
                if (LOGGER.isEnabled(Logger.Level.INFO)) LOGGER.info(message);
                break;
            case "TRACE":
                if (LOGGER.isEnabled(Logger.Level.TRACE)) LOGGER.trace(message);
                break;
            case "DEBUG":
                if (LOGGER.isEnabled(Logger.Level.DEBUG)) LOGGER.debug(message);
                break;
            case "FATAL":
                if (LOGGER.isEnabled(Logger.Level.FATAL)) LOGGER.fatal(message);
                break;
        }
    }
}
