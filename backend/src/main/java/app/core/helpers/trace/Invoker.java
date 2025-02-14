package app.core.helpers.trace;

import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import java.util.ArrayList;
import java.util.List;

import static app.core.helpers.trace.MethodTracer.runThreadsAndLogTracers;

@RequestScoped
public class Invoker {

    @Inject
    LembreteThread lembreteThread;
    private String methodToInvoke;
    private Object objectToInvoke;
    private Long order;
    private Object classWithAnnotation;
    private SecurityContext context;
    private String tenant;

    public Invoker(String methodToInvoke, Object objectToInvoke, Long order, Object classWithAnnotation, String tenant) {
        this.methodToInvoke = methodToInvoke;
        this.objectToInvoke = objectToInvoke;
        this.order = order;
        this.classWithAnnotation = classWithAnnotation;
        this.tenant = tenant;
    }

    public Invoker() {

    }

    public void invokerFilaLembreteAgendamentos(String tenant) {

        List<Invoker> invokers = new ArrayList<>();

        invokers.add(new Invoker("filaLembreteAgendamentos", lembreteThread, 1L, new LembreteThread(), tenant));

        runThreadsAndLogTracers(invokers);

    }

    public Object getObjectToInvoke() {
        return objectToInvoke;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Object getClassWithAnnotation() {
        return classWithAnnotation;
    }

    public String getTenant() {
        return tenant;
    }
}
