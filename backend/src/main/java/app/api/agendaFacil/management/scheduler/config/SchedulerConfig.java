package app.api.agendaFacil.management.scheduler.config;


import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static app.core.helpers.utils.BasicFunctions.log;

@ConfigProperties(prefix = "quarkus.scheduler")
public class SchedulerConfig {


    @ConfigProperty(name = "enabled")
    public boolean enabled;

    public void schedulerConfig() {

        String scheduler = ConfigProvider.getConfig().getValue("quarkus.scheduler.enabled", String.class);
        log("Filas e Schedulers de Lembrete de Agendamentos: " + scheduler);
    }
}
