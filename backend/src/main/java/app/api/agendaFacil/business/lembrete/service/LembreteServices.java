package app.api.agendaFacil.business.lembrete.service;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.configuradorNotificacao.entity.ConfiguradorNotificacao;
import app.api.agendaFacil.business.lembrete.entity.Lembrete;
import app.api.agendaFacil.business.lembrete.manager.LembreteManager;
import app.api.agendaFacil.business.lembrete.thread.LembreteThread;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class LembreteServices extends LembreteManager {

    public LembreteServices() {
        super();
    }

    @Inject
    public LembreteServices(LembreteThread lembreteThread) {
        super(lembreteThread);
    }

    public static List<Lembrete> gerarLembretes(List<Agendamento> agendamentos) {

        List<Lembrete> lembretes = new ArrayList<>();

        List<ConfiguradorNotificacao> configuradorNotificacoes = ConfiguradorNotificacao.listAll();

        if (BasicFunctions.isNotEmpty(agendamentos, configuradorNotificacoes)) {
            agendamentos.forEach(agendamento -> configuradorNotificacoes.forEach(configurador -> {

                Lembrete lembrete1, lembrete2;

                if (agendamento.profissionalValido()) {
                    lembrete1 = makeLembrete(agendamento, agendamento.getProfissionalAgendamento().getPessoa(),
                            configurador,
                            Lembrete.mensagemProfissional());

                    if (BasicFunctions.isNotEmpty(lembrete1)) {
                        lembretes.add(lembrete1);
                    }
                }

                if (agendamento.clienteValido()) {
                    lembrete2 = makeLembrete(agendamento, agendamento.getPessoaAgendamento(), configurador,
                            Lembrete.mensagemCliente());

                    if (BasicFunctions.isNotEmpty(lembrete2)) {
                        lembretes.add(lembrete2);
                    }
                }

            }));
        }
        return lembretes;
    }

    public static Lembrete makeLembrete(Agendamento agendamento, Entidade pessoa, ConfiguradorNotificacao configurador,
                                        String mensagemTemplate) {

        LocalDate dataNotificacao = agendamento.getDataAgendamento().minusDays(configurador.getDataIntervalo());
        LocalTime horarioNotificacao = agendamento.getHorarioAgendamento()
                .minusHours(configurador.getHoraMinutoIntervalo().getHour())
                .minusMinutes(configurador.getHoraMinutoIntervalo().getMinute());

        return Lembrete.makeLembreteNotificacao(agendamento, dataNotificacao, horarioNotificacao, mensagemTemplate,
                pessoa);
    }

    public Response enviarLembrete(String mensagem, Long whatsppId, Long telegramId) {

        try {

            responses = new Responses();

            if (BasicFunctions.isValid(telegramId)) {
                lembreteThread.enviarLembrete(mensagem, Contexto.getTelegramBaseUrl(), telegramId, null, tenant);
                responses.setMessages("Lembrete enviado com sucesso para o Telegram");
            }
            if (BasicFunctions.isValid(whatsppId)) {
                lembreteThread.enviarLembrete(mensagem, Contexto.getWhatsappBaseUrl(), whatsppId, null, tenant);
                responses.setMessages("Lembrete enviado com sucesso para o WhatsApp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(lembreteThread).status(responses.getStatus()).build();
    }
}
