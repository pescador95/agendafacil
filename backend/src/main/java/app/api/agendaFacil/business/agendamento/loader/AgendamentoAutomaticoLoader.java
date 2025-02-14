package app.api.agendaFacil.business.agendamento.loader;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AgendamentoAutomaticoLoader {

    final AgendamentoAutomaticoLoader agendamentoAutomaticoLoader;

    public AgendamentoAutomaticoLoader(AgendamentoAutomaticoLoader agendamentoAutomaticoLoader) {
        this.agendamentoAutomaticoLoader = agendamentoAutomaticoLoader;
    }

}
