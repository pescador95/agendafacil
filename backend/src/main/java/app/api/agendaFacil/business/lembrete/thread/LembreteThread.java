package app.api.agendaFacil.business.lembrete.thread;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.lembrete.entity.Lembrete;
import app.api.agendaFacil.business.lembrete.repository.LembreteRepository;
import app.api.agendaFacil.business.lembrete.service.LembreteServices;
import app.api.agendaFacil.business.thread.entity.Thread;
import app.api.agendaFacil.business.thread.repository.ThreadRepository;
import app.core.helpers.trace.MethodTracer;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

import static app.core.helpers.utils.BasicFunctions.log;
import static app.core.helpers.utils.Contexto.dataHoraContextoToString;
import static app.core.helpers.utils.Contexto.dataHoraToString;

@RequestScoped
@Transactional
@Path("/")
public class LembreteThread {

    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    @Inject
    ThreadRepository threadRepository;
    @Inject
    AgendamentoRepository agendamentoRepository;
    @Inject
    LembreteRepository lembreteRepository;

    private Set<Lembrete> filaLembretes = new HashSet<>();

    @RunOnVirtualThread
    @GET
    @MethodTracer.RastrearExecucaoMetodo
    public void filaLembreteAgendamentos() {

        String telegramApiUrl = Contexto.getTelegramBaseUrl();

        String whatsappApiUrl = Contexto.getWhatsappBaseUrl();

        if (telegramApiUrl == null) {
            throw new IllegalStateException("A variável de ambiente TELEGRAM_BASEURL não está definida.");
        }

        if (whatsappApiUrl == null) {
            throw new IllegalStateException("A variável de ambiente WHATSAPP_BASEURL não está definida.");
        }

        gerarLembretesAdicionarFila();

        enviarLembretesEAtualizarFila();

        atualizarStatusFilaAutomaticamente();

        pararAgendador();
    }

    public void gerarLembretesAdicionarFila() {

        List<Agendamento> agendamentos = recuperarAgendamentosDoBancoDeDados();

        if (BasicFunctions.isNotEmpty(agendamentos)) {

            List<Lembrete> lembretes = LembreteServices.gerarLembretes(agendamentos);

            if (BasicFunctions.isNotEmpty(lembretes)) {

                Thread fila = buscarFila();

                adicionarLembretesAFila(lembretes, fila);
            }

        }

        removeLembreteExpirados();

        removeFilasExpiradas();

        loadLembretesPersistidos();
    }

    private List<Agendamento> recuperarAgendamentosDoBancoDeDados() {
        return agendamentoRepository.listAgendamentosSemLembretesGerados();
    }

    public void loadLembretesPersistidos() {

        List<Lembrete> lembretesPersisted = LembreteRepository.listLembretesNaoEnviados();

        Set<Lembrete> lembretesFiltrados = lembretesPersisted.stream()
                .filter(lembrete -> lembrete.getDataLembrete()
                        .isEqual(Contexto
                                .dataContexto(lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento().getZoneId()))).collect(Collectors.toSet());

        filaLembretes.addAll(lembretesFiltrados);
    }

    public Thread buscarFila() {

        Thread filaExecucao = ThreadRepository.findByStatus(Thread.STATUS_EM_EXECUCAO);

        if (BasicFunctions.isNotEmpty(filaExecucao)) {
            return filaExecucao;
        }

        Thread filaFalha = ThreadRepository.findByStatus(Thread.STATUS_FALHA);

        if (BasicFunctions.isNotEmpty(filaFalha)) {
            return filaFalha;
        }

        Thread filaPendente = ThreadRepository.findByStatus(Thread.STATUS_PENDENTE);

        if (BasicFunctions.isNotEmpty(filaPendente)) {
            return filaPendente;
        }

        return criarFila();
    }

    public Thread criarFila() {

        Thread fila = new Thread();

        fila.persist();
        log("Nova Fila criada. ID: " + fila.getId() + " - Data e hora de criação: " + fila.getDataAcao(),
                Contexto.traceMethods());
        return fila;
    }

    public void adicionarLembretesAFila(List<Lembrete> lembretes, Thread fila) {

        if (BasicFunctions.isNotEmpty(lembretes)) {

            for (Lembrete lembrete : lembretes) {
                filaLembretes.add(lembrete);
                lembrete.setThread(fila);
                lembrete.persist();
            }
        }
    }

    public void enviarLembretesEAtualizarFila() {

        String telegramApiUrl = Contexto.getTelegramBaseUrl();

        String whatsappApiUrl = Contexto.getWhatsappBaseUrl();

        List<Lembrete> lembretesMantidos = new ArrayList<>();

        if (BasicFunctions.isNotEmpty(filaLembretes)) {
            for (Lembrete lembrete : filaLembretes) {

                atualizarStatusFila(lembrete.getThread(), Thread.STATUS_EM_EXECUCAO);

                if (lembrete.enviarLembrete()) {

                    if (BasicFunctions.isValid(lembrete.getTelegramId())) {
                        enviarLembrete(lembrete.getMensagem(), telegramApiUrl,
                                lembrete.getTelegramId(), lembrete, lembrete.getOrganizacaoTenant());
                    }
                    if (BasicFunctions.isValid(lembrete.getWhatsappId())) {
                        enviarLembrete(lembrete.getMensagem(), whatsappApiUrl,
                                lembrete.getWhatsappId(), lembrete, lembrete.getOrganizacaoTenant());
                    }
                    lembretesMantidos.add(lembrete);
                }
            }
            filaLembretes = new HashSet<>(lembretesMantidos);
        }
    }

    public void atualizarStatusFila(Thread fila, Long status) {

        fila.setStatus(status);
        fila.setStatusDescricao(fila.statusDestricao());

        if (status.equals(Thread.STATUS_EM_EXECUCAO)) {
            fila.setDataHoraInicio(Contexto.dataHoraContexto());
        }
        if (status.equals(Thread.STATUS_FINALIZADO)) {
            fila.setDataHoraFim(Contexto.dataHoraContexto());
        }
        if (status.equals(Thread.STATUS_PENDENTE)) {
            fila.setDataHoraInicio((null));
            fila.setDataHoraFim((null));
        }
        if (status.equals(Thread.STATUS_FALHA)) {
            fila.setDataHoraFim(Contexto.dataHoraContexto());
        }
        if (status.equals(Thread.STATUS_CANCELADO)) {
            fila.setDataHoraFim(Contexto.dataHoraContexto());
        }
        fila.setDataAcao(Contexto.dataHoraContexto());
        threadRepository.update(fila);
    }

    public void atualizarStatusFilaAutomaticamente() {
        List<Thread> filas = Thread.listAll();
        String message = "";

        for (Thread fila : filas) {
            if (todosLembretesNaoEnviados(fila)) {
                message = "\nAinda há lembretes da Fila ID:" + fila.getId()
                        + " com status de não enviado. Atualizando status da Fila para PENDENTE.";
                atualizarStatusFila(fila, Thread.STATUS_PENDENTE);
            }
            if (todosLembretesEnviados(fila)) {
                message = "\nTodos os lembretes da Fila ID:" + fila.getId()
                        + " estão com status de enviado. Atualizando status da Fila para FINALIZADA.";
                atualizarStatusFila(fila, Thread.STATUS_FINALIZADO);
            }
            if (todosLembretesComFalha(fila)) {
                message = "\nAinda há lembretes da Fila ID:" + fila.getId()
                        + " com status de falha no envio. Atualizando status da Fila para FALHA.";
                atualizarStatusFila(fila, Thread.STATUS_FALHA);
            }
            if (!todosLembretesNaoEnviados(fila) && !todosLembretesEnviados(fila) && !todosLembretesComFalha(fila)) {
                message = "\nAinda há lembretes da fila id:" + fila.getId()
                        + " com status de não enviado. Atualizando status da Fila para EM EXECUÇÃO.";
                atualizarStatusFila(fila, Thread.STATUS_EM_EXECUCAO);
            }
        }
        log(message, Contexto.traceMethods());
        statusFilas();
    }

    private boolean todosLembretesNaoEnviados(Thread fila) {
        List<Lembrete> lembretes = LembreteRepository.listByThread(fila);
        return lembretes.stream()
                .filter(lembrete -> lembrete.getThread().getId().equals(fila.getId()))
                .allMatch(lembrete -> lembrete.getStatusNotificacao().equals(Lembrete.STATUS_NOTIFICACAO_NAO_ENVIADO));
    }

    private boolean todosLembretesEnviados(Thread fila) {
        List<Lembrete> lembretes = LembreteRepository.listByThread(fila);
        return lembretes.stream()
                .filter(lembrete -> lembrete.getThread().getId().equals(fila.getId()))
                .allMatch(lembrete -> lembrete.getStatusNotificacao().equals(Lembrete.STATUS_NOTIFICACAO_ENVIADO));
    }

    private boolean todosLembretesComFalha(Thread fila) {
        List<Lembrete> lembretes = LembreteRepository.listByThread(fila);
        return lembretes.stream()
                .filter(lembrete -> lembrete.getThread().getId().equals(fila.getId()))
                .allMatch(lembrete -> lembrete.getStatusNotificacao().equals(Lembrete.STATUS_NOTIFICACAO_FALHA_ENVIO));
    }

    public void enviarLembrete(String mensagem, String apiUrl, Long id, Lembrete lembrete, String tenant) {

        if (apiUrl == null) {
            throw new IllegalStateException("A variável de ambiente TELEGRAM_BASEURL não está definida.");
        }

        String url = apiUrl + "/enviarLembrete/" + id;

        JsonObject body = Json.createObjectBuilder()
                .add("mensagem", mensagem)
                .add("tenant", tenant)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            if (BasicFunctions.isNotEmpty(lembrete)) {
                checkStatusAndUpdate(statusCode, lembrete);
            }

        } catch (InterruptedException | IOException e) {

            if (BasicFunctions.isNotEmpty(lembrete)) {

                log("Erro ao enviar mensagem de lembrete: \n - Motivo:" + e
                                + "\n - Data e hora de tentativa de envio: "
                                + dataHoraContextoToString(
                                lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento()),
                        Contexto.traceMethods());
                atualizarStatusLembrete(lembrete.getId(), Lembrete.STATUS_NOTIFICACAO_FALHA_ENVIO);
            }
        }
    }

    private void checkStatusAndUpdate(Integer statusCode, Lembrete lembrete) {
        if (statusCode == 200) {
            envioSucesso(lembrete);
            atualizarStatusLembrete(lembrete.getId(), Lembrete.STATUS_NOTIFICACAO_ENVIADO);
        }
        if (statusCode != 200) {
            envioFalha(lembrete, statusCode);
            atualizarStatusLembrete(lembrete.getId(), Lembrete.STATUS_NOTIFICACAO_FALHA_ENVIO);
        }
    }

    private void atualizarStatusLembrete(Long id, Long status) {

        Lembrete lembrete = LembreteRepository.findById(id);
        if (lembrete != null) {
            lembrete.setStatusNotificacao(status);
            lembrete.setDataHoraEnvio(
                    Contexto.dataHoraContexto(lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento().getZoneId()));
            lembrete.setStatusLembrete(lembrete.statusLembrete());
            lembreteRepository.create(lembrete);
            log("\nStatus do lembrete atualizado.\n Lembrete ID: " + lembrete.getId() + "\n - Status: "
                            + lembrete.getStatusLembrete() + "\n - Data e hora de atualização: "
                            + dataHoraToString(lembrete.getDataHoraEnvio(),
                            lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento()),
                    Contexto.traceMethods());
        }
    }

    public void pararAgendador() {

        scheduler.shutdown();

    }


    public void envioSucesso(Lembrete lembrete) {
        log(mensagemEnvioSucesso(lembrete), Contexto.traceMethods());
    }

    public void envioFalha(Lembrete lembrete, int statusCode) {
        log(mensagemEnvioFalha(lembrete, statusCode), Contexto.traceMethods());
    }

    public String mensagemEnvioSucesso(Lembrete lembrete) {
        return "Mensagem de lembrete enviada com sucesso.\n Lembrete ID: " + lembrete.getId()
                + "\n - Data e hora de envio: "
                + dataHoraContextoToString(lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento()) + "\n";
    }

    public String mensagemEnvioFalha(Lembrete lembrete, int statusCode) {
        return "Falha ao enviar mensagem de lembrete.\n Código de status: " + statusCode
                + "\n - Data e hora de tentativa de envio: "
                + dataHoraContextoToString(lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento()) + "\n";
    }

    public void statusFilas() {
        log("NÚMERO DE FILAS PENDENTES: " + ThreadRepository.countByStatus(Thread.STATUS_PENDENTE), Contexto.traceMethods());
        log("NÚMERO DE FILAS EM EXECUÇÃO: " + ThreadRepository.countByStatus(Thread.STATUS_EM_EXECUCAO), Contexto.traceMethods());
        log("NÚMERO DE FILAS FINALIZADAS: " + ThreadRepository.countByStatus(Thread.STATUS_FINALIZADO), Contexto.traceMethods());
        log("NÚMERO DE FILAS COM FALHA: " + ThreadRepository.countByStatus(Thread.STATUS_FALHA), Contexto.traceMethods());
        log("NÚMERO DE FILAS CANCELADAS: " + ThreadRepository.countByStatus(Thread.STATUS_CANCELADO), Contexto.traceMethods());
    }

    public void removeLembreteExpirados() {
        List<Lembrete> lembretesExpirados = LembreteRepository.listAll();
        if (BasicFunctions.isNotEmpty(lembretesExpirados)) {
            lembretesExpirados.forEach(lembrete -> {
                if (!lembrete.getStatusNotificacao().equals(Lembrete.STATUS_NOTIFICACAO_ENVIADO)
                        && lembrete.getDataLembrete()
                        .isBefore(Contexto.dataContexto(
                                lembrete.getAgendamentoLembrete().getOrganizacaoAgendamento().getZoneId()))) {
                    lembreteRepository.remove(lembrete);
                    log("Lembrete ID: " + lembrete.getId() + " removido.", Contexto.traceMethods());
                }
            });
        }
    }

    public void removeFilasExpiradas() {
        List<Thread> filas = ThreadRepository.listByStatusAndData(Thread.STATUS_PENDENTE, Contexto.dataHoraContexto());

        if (BasicFunctions.isNotEmpty(filas)) {
            List<Long> filaIds = filas.stream().map(Thread::getId).collect(Collectors.toList());

            List<Lembrete> lembretes = LembreteRepository.listByListaFilaId(filaIds);

            filas.forEach(fila -> {
                boolean hasAssociatedLembrete = lembretes.stream()
                        .anyMatch(lembrete -> lembrete.getThread().getId().equals(fila.getId()));

                if (!hasAssociatedLembrete) {
                    threadRepository.remove(fila);
                    fila.delete();
                    log("Fila ID: " + fila.getId() + " removida.", Contexto.traceMethods());
                }
            });
        }
    }

}
