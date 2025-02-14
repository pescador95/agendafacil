package app.core.application.tenant;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.logging.MDC;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Provider
public class TenantMetricsFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOGGER = Logger.getLogger(TenantMetricsFilter.class);
    private static final String TIMER_SAMPLE = "timerSample";
    private static final Set<String> IGNORE_PATHS = Set.of("favicon.ico", "health", "metrics");
    private final Map<String, Timer> timerCache = new ConcurrentHashMap<>();
    @Inject
    MeterRegistry meterRegistry;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Timer.Sample sample = Timer.start(meterRegistry);
        requestContext.setProperty(TIMER_SAMPLE, sample);

        String tenant = requestContext.getHeaderString("X-Tenant");
        MDC.put("tenant", tenant != null ? tenant : "none");
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        if (shouldIgnoreRequest(requestContext)) {
            return;
        }
        try {
            Timer.Sample sample = (Timer.Sample) requestContext.getProperty(TIMER_SAMPLE);
            if (sample != null) {
                String tenant = requestContext.getHeaderString("X-Tenant");
                String method = requestContext.getMethod();
                String path = sanitizePath(requestContext.getUriInfo().getPath());
                String status = String.valueOf(responseContext.getStatus());

                Timer timer = timerCache.computeIfAbsent(
                        path,
                        p -> Timer.builder("http_server_requests_seconds")
                                .description("Tempo de resposta do servidor HTTP")
                                .tags("tenant", tenant != null ? tenant : "none",
                                        "method", method,
                                        "uri", p,
                                        "status", status)
                                .register(meterRegistry)
                );

                sample.stop(timer);

                meterRegistry.counter("tenant_requests_total", "tenant", tenant != null ? tenant : "none").increment();
            }
        } catch (Exception e) {
            LOGGER.error("Error recording tenant metrics", e);
        }
    }

    public Boolean shouldIgnoreRequest(ContainerRequestContext requestContext) {
        String path = requestContext.getUriInfo().getPath();
        return IGNORE_PATHS.contains(path);
    }

    private String sanitizePath(String path) {
        return path.replaceAll("/\\d+", "/{id}");
    }
}

