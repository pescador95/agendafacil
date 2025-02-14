package app.api.agendaFacil.business.lembrete.entity;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.thread.entity.Thread;
import app.core.application.entity.EntityBase;
import app.core.application.tenant.CustomTenantResolver;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.StringFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "lembrete")
public class Lembrete extends EntityBase {
    public static final Long STATUS_NOTIFICACAO_ENVIADO = 1L;
    public static final Long STATUS_NOTIFICACAO_NAO_ENVIADO = 2L;
    public static final Long STATUS_NOTIFICACAO_FALHA_ENVIO = 3L;
    @Column()
    @SequenceGenerator(name = "lembreteIdSequence", sequenceName = "lembrete_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "agendamentoId")
    private Agendamento agendamentoLembrete;
    @Column()
    private LocalDate dataLembrete;
    @Column()
    private LocalTime horarioLembrete;
    @Column(columnDefinition = "TEXT")
    private String mensagem;
    @Column()
    private Long statusNotificacao;

    @Column()
    private String statusLembrete;

    @Column()
    private LocalDateTime dataHoraEnvio;

    @Column()
    private LocalDateTime dataAcao;

    @ManyToOne
    @JoinColumn(name = "queueId")
    private Thread thread;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoaLembrete;

    public Lembrete() {
        super();
    }

    @Inject
    protected Lembrete(SecurityContext context) {
        super(context);
    }

    public static Lembrete makeLembreteNotificacao(Agendamento agendamento, LocalDate dataNotificacao,
                                                   LocalTime horarioNotificacao, String mensagemTemplate, Entidade pessoa) {
        Lembrete lembrete = new Lembrete();
        lembrete.setAgendamentoLembrete(agendamento);
        lembrete.setDataLembrete(dataNotificacao);
        lembrete.setDataAcao(Contexto.dataHoraContexto());
        lembrete.setHorarioLembrete(horarioNotificacao);
        lembrete.setMensagem(StringFunctions.makeMensagemByNotificacaoTemplate(mensagemTemplate, StringFunctions.notificacaoBuilder(agendamento)));
        lembrete.setPessoaLembrete((Pessoa) pessoa);
        lembrete.setStatusNotificacao(Lembrete.STATUS_NOTIFICACAO_NAO_ENVIADO);
        lembrete.setStatusLembrete(lembrete.statusLembrete());
        return lembrete;
    }

    public static String mensagemCliente() {

        return "Olá, CLIENTE!\n\nVocê tem um atendimento de TIPOAGENDAMENTO agendado para DIA SEMANA às HORARIO na EMPRESA.\n \n Endereço: ENDERECO\n Contato: CONTATO \n Profissional: PROFISSIONAL \n Data do Agendamento: DATA \n Horário do Agendamento: HORARIO\n \n Atensiosamente, \n EMPRESA.";

    }

    public static String mensagemProfissional() {

        return "Olá, PROFISSIONAL!\n\nVocê tem um atendimento de TIPOAGENDAMENTO agendado para DIA SEMANA às HORARIO na EMPRESA.\n \n Endereço: ENDERECO\n Contato: CONTATO \n Cliente: CLIENTE \n Data do Agendamento: DATA \n Horário do Agendamento: HORARIO\n \n Atensiosamente, \n EMPRESA.";

    }

    public Boolean lembreteEnviado() {
        return BasicFunctions.isValid(this.statusNotificacao)
                && this.statusNotificacao.equals(STATUS_NOTIFICACAO_ENVIADO);
    }

    public Boolean lembreteNaoEnviado() {
        return BasicFunctions.isValid(this.statusNotificacao)
                && this.statusNotificacao.equals(STATUS_NOTIFICACAO_NAO_ENVIADO);
    }

    public Boolean lembreteFalhaEnvio() {
        return BasicFunctions.isValid(this.statusNotificacao)
                && this.statusNotificacao.equals(STATUS_NOTIFICACAO_FALHA_ENVIO);
    }

    public String statusLembrete() {
        if (lembreteEnviado()) {
            return "Enviado";
        }
        if (lembreteNaoEnviado()) {
            return "Não enviado";
        }
        if (lembreteFalhaEnvio()) {
            return "Falha no envio";
        }
        return "Não enviado";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Agendamento getAgendamentoLembrete() {
        return agendamentoLembrete;
    }

    public void setAgendamentoLembrete(Agendamento agendamentoLembrete) {
        this.agendamentoLembrete = agendamentoLembrete;
    }

    public LocalDate getDataLembrete() {
        return dataLembrete;
    }

    public void setDataLembrete(LocalDate dataLembrete) {
        this.dataLembrete = dataLembrete;
    }

    public LocalTime getHorarioLembrete() {
        return horarioLembrete;
    }

    public void setHorarioLembrete(LocalTime horarioLembrete) {
        this.horarioLembrete = horarioLembrete;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getStatusNotificacao() {
        return statusNotificacao;
    }

    public void setStatusNotificacao(Long statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public String getStatusLembrete() {
        return statusLembrete;
    }

    public void setStatusLembrete(String statusLembrete) {
        this.statusLembrete = statusLembrete;
    }

    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Long getTelegramId() {
        return BasicFunctions.isNotEmpty(this.pessoaLembrete) ? this.pessoaLembrete.getTelegramId() : null;
    }

    public Long getWhatsappId() {
        return BasicFunctions.isNotEmpty(this.pessoaLembrete) ? this.pessoaLembrete.getWhatsappId() : null;
    }

    public void setPessoaLembrete(Pessoa pessoaLembrete) {
        this.pessoaLembrete = pessoaLembrete;
    }

    public Boolean enviarLembrete() {

        return this.getAgendamentoLembrete().agendamentoPendente() && this.lembretePendente();
    }

    public Boolean lembretePendente() {
        return !this.lembreteEnviado() && this.getDataLembrete().isEqual(Contexto.dataContexto(this.getAgendamentoLembrete().getOrganizacaoAgendamento().getZoneId()))
                && !Contexto.horarioContexto(this.getAgendamentoLembrete().getOrganizacaoAgendamento().getZoneId()).isBefore(this.getHorarioLembrete())
                && (BasicFunctions.isValid(this.getTelegramId()) || BasicFunctions.isValid(this.getWhatsappId()));
    }

    public String getOrganizacaoTenant() {

        if (BasicFunctions.isNotEmpty(this.getAgendamentoLembrete(), this.getAgendamentoLembrete().getOrganizacaoAgendamento(), this.getAgendamentoLembrete().getOrganizacaoAgendamento().getTenant())) {
            return this.getAgendamentoLembrete().getOrganizacaoAgendamento().getTenant();
        }
        CustomTenantResolver tenant = new CustomTenantResolver();
        return tenant.getDefaultTenantId();
    }
}
