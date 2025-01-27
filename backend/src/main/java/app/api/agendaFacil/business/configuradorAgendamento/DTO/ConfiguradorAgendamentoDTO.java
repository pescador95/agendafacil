package app.api.agendaFacil.business.configuradorAgendamento.DTO;

import app.api.agendaFacil.business.configuradorAgendamento.entity.ConfiguradorAgendamento;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalTime;

public class ConfiguradorAgendamentoDTO {

    private Long id;

    private String nome;

    private LocalTime horarioInicioManha;

    private LocalTime horarioFimManha;

    private LocalTime horarioInicioTarde;

    private LocalTime horarioFimTarde;

    private LocalTime horarioInicioNoite;

    private LocalTime horarioFimNoite;

    private LocalTime horaMinutoIntervalo;

    private LocalTime horaMinutoTolerancia;

    private Boolean agendaManha = false;

    private Boolean agendaTarde = false;

    private Boolean agendaNoite = false;

    private Boolean atendeSabado = false;

    private Boolean atendeDomingo = false;

    private Boolean agendaSabadoManha = false;

    private Boolean agendaSabadoTarde = false;

    private Boolean agendaSabadoNoite = false;

    private Boolean agendaDomingoManha = false;

    private Boolean agendaDomingoTarde = false;

    private Boolean agendaDomingoNoite = false;

    private Boolean configuradorOrganizacao = false;

    private OrganizacaoDTO organizacaoConfigurador;

    private UsuarioDTO profissionalConfigurador;

    public ConfiguradorAgendamentoDTO() {
    }

    public ConfiguradorAgendamentoDTO(ConfiguradorAgendamento configuradorAgendamento) {

        if (BasicFunctions.isNotEmpty(configuradorAgendamento)) {
            this.id = configuradorAgendamento.getId();

            this.nome = configuradorAgendamento.getNome();

            this.horarioInicioManha = configuradorAgendamento.getHorarioInicioManha();

            this.horarioFimManha = configuradorAgendamento.getHorarioFimManha();

            this.horarioInicioTarde = configuradorAgendamento.getHorarioInicioTarde();

            this.horarioFimTarde = configuradorAgendamento.getHorarioFimTarde();

            this.horarioInicioNoite = configuradorAgendamento.getHorarioInicioNoite();

            this.horarioFimNoite = configuradorAgendamento.getHorarioFimNoite();

            this.horaMinutoIntervalo = configuradorAgendamento.getHoraMinutoIntervalo();

            this.horaMinutoTolerancia = configuradorAgendamento.getHoraMinutoTolerancia();

            this.agendaManha = configuradorAgendamento.getAgendaManha();

            this.agendaTarde = configuradorAgendamento.getAgendaTarde();

            this.agendaNoite = configuradorAgendamento.getAgendaNoite();

            this.atendeSabado = configuradorAgendamento.getAtendeSabado();

            this.atendeDomingo = configuradorAgendamento.getAtendeDomingo();

            this.agendaSabadoManha = configuradorAgendamento.getAgendaSabadoManha();

            this.agendaSabadoTarde = configuradorAgendamento.getAgendaSabadoTarde();

            this.agendaSabadoNoite = configuradorAgendamento.getAgendaSabadoNoite();

            this.agendaDomingoManha = configuradorAgendamento.getAgendaDomingoManha();

            this.agendaDomingoTarde = configuradorAgendamento.getAgendaDomingoTarde();

            this.agendaDomingoNoite = configuradorAgendamento.getAgendaDomingoNoite();

            this.configuradorOrganizacao = configuradorAgendamento.getConfiguradorOrganizacao();

            this.organizacaoConfigurador = new OrganizacaoDTO(configuradorAgendamento.getOrganizacaoConfigurador());

            this.profissionalConfigurador = new UsuarioDTO(configuradorAgendamento.getProfissionalConfigurador());

        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getHorarioInicioManha() {
        return horarioInicioManha;
    }

    public void setHorarioInicioManha(LocalTime horarioInicioManha) {
        this.horarioInicioManha = horarioInicioManha;
    }

    public LocalTime getHorarioFimManha() {
        return horarioFimManha;
    }

    public void setHorarioFimManha(LocalTime horarioFimManha) {
        this.horarioFimManha = horarioFimManha;
    }

    public LocalTime getHorarioInicioTarde() {
        return horarioInicioTarde;
    }

    public void setHorarioInicioTarde(LocalTime horarioInicioTarde) {
        this.horarioInicioTarde = horarioInicioTarde;
    }

    public LocalTime getHorarioFimTarde() {
        return horarioFimTarde;
    }

    public void setHorarioFimTarde(LocalTime horarioFimTarde) {
        this.horarioFimTarde = horarioFimTarde;
    }

    public LocalTime getHorarioInicioNoite() {
        return horarioInicioNoite;
    }

    public void setHorarioInicioNoite(LocalTime horarioInicioNoite) {
        this.horarioInicioNoite = horarioInicioNoite;
    }

    public LocalTime getHorarioFimNoite() {
        return horarioFimNoite;
    }

    public void setHorarioFimNoite(LocalTime horarioFimNoite) {
        this.horarioFimNoite = horarioFimNoite;
    }

    public LocalTime getHoraMinutoIntervalo() {
        return horaMinutoIntervalo;
    }

    public void setHoraMinutoIntervalo(LocalTime horaMinutoIntervalo) {
        this.horaMinutoIntervalo = horaMinutoIntervalo;
    }

    public LocalTime getHoraMinutoTolerancia() {
        return horaMinutoTolerancia;
    }

    public void setHoraMinutoTolerancia(LocalTime horaMinutoTolerancia) {
        this.horaMinutoTolerancia = horaMinutoTolerancia;
    }

    public Boolean getAgendaManha() {
        return agendaManha;
    }

    public void setAgendaManha(Boolean agendaManha) {
        this.agendaManha = agendaManha;
    }

    public Boolean getAgendaTarde() {
        return agendaTarde;
    }

    public void setAgendaTarde(Boolean agendaTarde) {
        this.agendaTarde = agendaTarde;
    }

    public Boolean getAgendaNoite() {
        return agendaNoite;
    }

    public void setAgendaNoite(Boolean agendaNoite) {
        this.agendaNoite = agendaNoite;
    }

    public Boolean getAtendeSabado() {
        return atendeSabado;
    }

    public void setAtendeSabado(Boolean atendeSabado) {
        this.atendeSabado = atendeSabado;
    }

    public Boolean getAtendeDomingo() {
        return atendeDomingo;
    }

    public void setAtendeDomingo(Boolean atendeDomingo) {
        this.atendeDomingo = atendeDomingo;
    }

    public Boolean getAgendaSabadoManha() {
        return agendaSabadoManha;
    }

    public void setAgendaSabadoManha(Boolean agendaSabadoManha) {
        this.agendaSabadoManha = agendaSabadoManha;
    }

    public Boolean getAgendaSabadoTarde() {
        return agendaSabadoTarde;
    }

    public void setAgendaSabadoTarde(Boolean agendaSabadoTarde) {
        this.agendaSabadoTarde = agendaSabadoTarde;
    }

    public Boolean getAgendaSabadoNoite() {
        return agendaSabadoNoite;
    }

    public void setAgendaSabadoNoite(Boolean agendaSabadoNoite) {
        this.agendaSabadoNoite = agendaSabadoNoite;
    }

    public Boolean getAgendaDomingoManha() {
        return agendaDomingoManha;
    }

    public void setAgendaDomingoManha(Boolean agendaDomingoManha) {
        this.agendaDomingoManha = agendaDomingoManha;
    }

    public Boolean getAgendaDomingoTarde() {
        return agendaDomingoTarde;
    }

    public void setAgendaDomingoTarde(Boolean agendaDomingoTarde) {
        this.agendaDomingoTarde = agendaDomingoTarde;
    }

    public Boolean getAgendaDomingoNoite() {
        return agendaDomingoNoite;
    }

    public void setAgendaDomingoNoite(Boolean agendaDomingoNoite) {
        this.agendaDomingoNoite = agendaDomingoNoite;
    }

    public Boolean getConfiguradorOrganizacao() {
        return configuradorOrganizacao;
    }

    public void setConfiguradorOrganizacao(Boolean configuradorOrganizacao) {
        this.configuradorOrganizacao = configuradorOrganizacao;
    }

    public OrganizacaoDTO getOrganizacaoConfigurador() {
        return organizacaoConfigurador;
    }

    public void setOrganizacaoConfigurador(OrganizacaoDTO organizacaoConfigurador) {
        this.organizacaoConfigurador = organizacaoConfigurador;
    }

    public UsuarioDTO getProfissionalConfigurador() {
        return profissionalConfigurador;
    }

    public void setProfissionalConfigurador(UsuarioDTO profissionalConfigurador) {
        this.profissionalConfigurador = profissionalConfigurador;
    }
}
