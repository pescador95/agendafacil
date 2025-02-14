package app.api.agendaFacil.management.scheduler.service;

import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import app.api.agendaFacil.management.scheduler.manager.SchedulerManager;
import app.core.application.tenant.Tenant;
import app.core.application.tenant.TenantService;
import app.core.helpers.trace.Invoker;
import app.core.helpers.utils.HttpService;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static app.core.helpers.utils.Info.quarkusBaseURL;

@RequestScoped
public class SchedulerServices extends SchedulerManager {

    public SchedulerServices() {
        super();
    }

    @Inject
    public SchedulerServices(LembreteThread lembreteThread, Invoker invoker, TenantService tenantService, AgendamentoRepository agendamentoRepository) {
        super(lembreteThread, invoker, tenantService, agendamentoRepository);
    }

    @Scheduled(cron = "{counter.cron.expression45s}")
    @Transactional
    public void run() {
        if (getSchedulerEnabled()) {
            execute(tenantService.createAndGetTenants());
        }
    }

    @ActivateRequestContext
    public void execute(List<Tenant> tenants) {

        HttpService httpService = new HttpService();

        List<CompletableFuture<Void>> futures = tenants.stream().map(tenant -> {
            String url = quarkusBaseURL() + "/schedulerServices/execute";
            return CompletableFuture.runAsync(() -> httpService.post(url, String.valueOf(tenant.getTenant())));
        }).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    @Transactional
    public void verifyAndRunScheduler(String tenant) {

        try {

            this.setTenant(tenant);

            if (getSchedulerEnabled()) {

                lembreteThread.gerarLembretesAdicionarFila();

                if (canRunScheduler()) {
                    runScheduler(tenant);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void runScheduler(String tenant) {

        if (getTraceMethods()) {
            invoker.invokerFilaLembreteAgendamentos(tenant);
        }
        if (!getTraceMethods()) {
            lembreteThread.filaLembreteAgendamentos();
        }
    }

    public Boolean canRunScheduler() {
        return agendamentoRepository.canIRunScheduler();
    }
}