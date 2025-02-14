package app.core.helpers.trace.monitor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.jboss.logging.Logger;

@Interceptor
@Monitor
public class MonitorInterceptor {

    private static final Logger LOGGER = Logger.getLogger(MonitorInterceptor.class);

    @AroundInvoke
    public Object logExecutionTime(InvocationContext context) throws Exception {
        long start = System.currentTimeMillis();

        Monitor annotation = context.getMethod().getAnnotation(Monitor.class);
        boolean logParams = annotation == null || annotation.logParams();
        boolean logResult = annotation == null || annotation.logResult();

        String methodName = context.getMethod().getDeclaringClass().getName() + "." + context.getMethod().getName();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("🔹 Executando: " + methodName);
            if (logParams) {
                LOGGER.debug("🔹 Parâmetros: " + java.util.Arrays.toString(context.getParameters()));
            }
        }

        try {
            Object result = context.proceed();
            long elapsedTime = System.currentTimeMillis() - start;

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("✅ " + methodName + " executado em " + elapsedTime + "ms");
                if (logResult) {
                    LOGGER.info("🔹 Retorno: " + result);
                }
            }

            return result;
        } catch (Exception e) {
            LOGGER.error("❌ Erro ao executar " + methodName, e);
            throw e;
        }
    }
}
