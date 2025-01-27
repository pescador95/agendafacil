package app.core.helpers.trace;

import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static app.core.helpers.utils.BasicFunctions.log;

public class MethodTracer {

    private static final Object lock = new Object();

    public MethodTracer() {

    }

    public static void runThreadsAndLogTracers(List<Invoker> invokers) {

        executarMetodosComRastreamento(invokers);

    }

    public static void executarMetodosComRastreamento(List<Invoker> invokers) {
        invokers.sort(Comparator.comparing(Invoker::getOrder));

        invokers.forEach(invoker -> {
            Object obj = invoker.getObjectToInvoke();
            Object classWithAnnotation = invoker.getClassWithAnnotation();
            Class<?> clazz = classWithAnnotation.getClass();

            List<Method> annotatedMethods = Arrays.stream(clazz.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RastrearExecucaoMetodo.class)
                            && isMethodInClass(method, clazz))
                    .toList();

            annotatedMethods.forEach(method -> rastrearExecucaoMetodo(obj, clazz, method, invoker.getTenant()));
        });
    }

    public static void logMethodExecution(String methodInfo, String tenant) {
        String startTimeKey = methodInfo + "-START-TIME";
        System.setProperty(startTimeKey, Contexto.dataHoraContextoToString());
        log("\n=================================================================\n" +
                "Tenant: " + tenant + "\n" +
                methodInfo + "\n\n"
        );
    }

    public static void logMethodCompletion(String methodInfo) {
        String startTimeKey = methodInfo + "-START-TIME";

        String startTimeStr = System.getProperty(startTimeKey);

        if (BasicFunctions.isNotEmpty(startTimeStr)) {
            LocalDateTime startTime = LocalDateTime.parse(startTimeStr,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS"));
            LocalDateTime endTime = Contexto.dataHoraContexto();

            Duration duration = Duration.between(startTime, endTime);
            long seconds = duration.getSeconds();
            long millis = duration.toMillis();

            log("\n\n - Executing at " + startTime +
                    "\n - Completed at " + Contexto.dataHoraContextoToString() +
                    "\n\n - Execution time: " + seconds + " seconds and " + millis + " milliseconds" +
                    "\n=================================================================\n");
        }
    }

    public static void rastrearExecucaoMetodo(Object obj, Class<?> clazz, Method method, String tenant) {

        String methodInfo = null;

        String className = clazz.getSimpleName();
        String methodName = method.getName();

        methodInfo = getMethodInfo(className, methodName);

        synchronized (lock) {

            try {

                logMethodExecution(methodInfo, tenant);

                method.invoke(obj);
            } catch (Exception e) {
                log(e.getMessage(), Contexto.traceMethods());
            } finally {
                logMethodCompletion(methodInfo);
            }
        }
    }

    public static String getMethodInfo(String className, String methodName) {
        return "\n - Class: " + className + "\n - " + "Method: " + methodName + "()";
    }

    public static boolean isMethodInClass(Method method, Class<?> clazz) {
        String methodName = method.getName();
        return Arrays.stream(clazz.getDeclaredMethods()).anyMatch(m -> m.getName().equals(methodName));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface RastrearExecucaoMetodo {
        String classe() default "";

        String metodo() default "";
    }
}
