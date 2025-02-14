package app.api.agendaFacil.business.configuradorAgendamento.entity;

import app.api.agendaFacil.business.configuradorAgendamento.DTO.ConfiguradorAgendamentoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "configuradorAgendamento")
public class ConfiguradorAgendamento extends EntityBase {

    @Column()
    @SequenceGenerator(name = "configuradorAgendamentoIdSequence", sequenceName = "configuradorAgendamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String nome;
    @ManyToOne
    @JoinColumn(name = "organizacaoId")
    private Organizacao organizacaoConfigurador;
    @Column()
    private LocalTime horarioInicioManha;
    @Column()
    private LocalTime horarioFimManha;
    @Column()
    private LocalTime horarioInicioTarde;
    @Column()
    private LocalTime horarioFimTarde;
    @Column()
    private LocalTime horarioInicioNoite;
    @Column()
    private LocalTime horarioFimNoite;
    @Column()
    private LocalTime horaMinutoIntervalo;

    @Column()
    private LocalTime horaMinutoTolerancia;

    @Column()
    private Boolean agendaManha = false;

    @Column()
    private Boolean agendaTarde = false;

    @Column()
    private Boolean agendaNoite = false;

    @Column()
    private Boolean atendeSabado = false;

    @Column()
    private Boolean atendeDomingo = false;

    @Column()
    private Boolean agendaSabadoManha = false;

    @Column()
    private Boolean agendaSabadoTarde = false;

    @Column()
    private Boolean agendaSabadoNoite = false;

    @Column()
    private Boolean agendaDomingoManha = false;

    @Column()
    private Boolean agendaDomingoTarde = false;

    @Column()
    private Boolean agendaDomingoNoite = false;

    @ManyToOne
    @JoinColumn(name = "profissionalId")
    private Usuario profissionalConfigurador;

    @Column()
    private Boolean configuradorOrganizacao = false;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "usuarioAcaoId")
    private Usuario usuarioAcao;

    @Column()
    private LocalDateTime dataAcao;

    public ConfiguradorAgendamento() {
        super();
    }

    @Inject
    protected ConfiguradorAgendamento(SecurityContext context) {
        super(context);
    }

    public ConfiguradorAgendamento(ConfiguradorAgendamentoDTO entityDTO) {
        super();
        if (BasicFunctions.isNotEmpty(entityDTO)) {
            this.id = entityDTO.getId();
            this.nome = entityDTO.getNome();
            this.horarioInicioManha = entityDTO.getHorarioInicioManha();
            this.horarioFimManha = entityDTO.getHorarioFimManha();
            this.horarioInicioTarde = entityDTO.getHorarioInicioTarde();
            this.horarioFimTarde = entityDTO.getHorarioFimTarde();
            this.horarioInicioNoite = entityDTO.getHorarioInicioNoite();
            this.horarioFimNoite = entityDTO.getHorarioFimNoite();
            this.horaMinutoIntervalo = entityDTO.getHoraMinutoIntervalo();
            this.horaMinutoTolerancia = entityDTO.getHoraMinutoTolerancia();
            this.agendaManha = entityDTO.getAgendaManha();
            this.agendaTarde = entityDTO.getAgendaTarde();
            this.agendaNoite = entityDTO.getAgendaNoite();
            this.atendeSabado = entityDTO.getAtendeSabado();
            this.atendeDomingo = entityDTO.getAtendeDomingo();
            this.agendaSabadoManha = entityDTO.getAgendaSabadoManha();
            this.agendaSabadoTarde = entityDTO.getAgendaSabadoTarde();
            this.agendaSabadoNoite = entityDTO.getAgendaSabadoNoite();
            this.agendaDomingoManha = entityDTO.getAgendaDomingoManha();
            this.agendaDomingoTarde = entityDTO.getAgendaDomingoTarde();
            this.agendaDomingoNoite = entityDTO.getAgendaDomingoNoite();
            this.configuradorOrganizacao = entityDTO.getConfiguradorOrganizacao();
            if (BasicFunctions.isNotEmpty(entityDTO.getOrganizacaoConfigurador())) {
                this.organizacaoConfigurador = new Organizacao(entityDTO.getOrganizacaoConfigurador());
            }
            if (BasicFunctions.isNotEmpty(entityDTO.getProfissionalConfigurador())) {
                this.profissionalConfigurador = new Usuario(entityDTO.getProfissionalConfigurador());
            }
        }
    }

    public ConfiguradorAgendamento(ConfiguradorAgendamentoDTO entity, Organizacao pOrganizacao, Usuario pProfissional, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(pOrganizacao) && BasicFunctions.isValid(pOrganizacao)) {
            this.setOrganizacaoConfigurador(pOrganizacao);
        }
        if (BasicFunctions.isNotEmpty(pProfissional) && BasicFunctions.isValid(pProfissional)) {
            this.setProfissionalConfigurador(pProfissional);
        }
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            this.setNome(entity.getNome());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioManha())) {
            this.setHorarioInicioManha(entity.getHorarioInicioManha());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimManha())) {
            this.setHorarioFimManha(entity.getHorarioFimManha());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioTarde())) {
            this.setHorarioInicioTarde(entity.getHorarioInicioTarde());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimTarde())) {
            this.setHorarioFimTarde(entity.getHorarioFimTarde());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioNoite())) {
            this.setHorarioInicioNoite(entity.getHorarioInicioNoite());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimNoite())) {
            this.setHorarioFimNoite(entity.getHorarioFimNoite());
        }
        if (BasicFunctions.isValid(entity.getHoraMinutoIntervalo())) {
            this.setHoraMinutoIntervalo(entity.getHoraMinutoIntervalo());
        }
        if (BasicFunctions.isValid(entity.getHoraMinutoTolerancia())) {
            this.setHoraMinutoTolerancia(entity.getHoraMinutoTolerancia());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaManha())) {
            this.setAgendaManha(entity.getAgendaManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaTarde())) {
            this.setAgendaTarde(entity.getAgendaTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaNoite())) {
            this.setAgendaNoite(entity.getAgendaNoite());
        }
        if (BasicFunctions.isNotEmpty(entity.getConfiguradorOrganizacao())) {
            this.setConfiguradorOrganizacao(entity.getConfiguradorOrganizacao());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoManha())) {
            this.setAgendaSabadoManha(entity.getAgendaSabadoManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoTarde())) {
            this.setAgendaSabadoTarde(entity.getAgendaSabadoTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoNoite())) {
            this.setAgendaSabadoNoite(entity.getAgendaSabadoNoite());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoManha())) {
            this.setAgendaDomingoManha(entity.getAgendaDomingoManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoTarde())) {
            this.setAgendaDomingoTarde(entity.getAgendaDomingoTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoNoite())) {
            this.setAgendaDomingoNoite(entity.getAgendaDomingoNoite());
        }

        if (BasicFunctions.isNotEmpty(entity.getAtendeSabado())) {
            this.setAtendeSabado(entity.getAtendeSabado());
        }
        if (BasicFunctions.isNotEmpty(entity.getAtendeDomingo())) {
            this.setAtendeDomingo(entity.getAtendeDomingo());
        }
        this.setUsuario(usuarioAuth);
        this.setUsuarioAcao(usuarioAuth);
        this.setDataAcao(Contexto.dataHoraContexto(usuarioAuth.getOrganizacaoDefaultId()));
    }

    public ConfiguradorAgendamento configuradorAgendamento(ConfiguradorAgendamento entityOld, ConfiguradorAgendamentoDTO entity, Organizacao pOrganizacao, Usuario pProfissional, Usuario usuarioAuth) {
        if (BasicFunctions.isNotEmpty(pOrganizacao) && BasicFunctions.isValid(pOrganizacao)) {
            entityOld.setOrganizacaoConfigurador(pOrganizacao);
        }
        if (BasicFunctions.isNotEmpty(pProfissional) && BasicFunctions.isValid(pOrganizacao)) {
            entityOld.setProfissionalConfigurador(pProfissional);
        }
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            entityOld.setNome(entity.getNome());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioManha())) {
            entityOld.setHorarioInicioManha(entity.getHorarioInicioManha());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimManha())) {
            entityOld.setHorarioFimManha(entity.getHorarioFimManha());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioTarde())) {
            entityOld.setHorarioInicioTarde(entity.getHorarioInicioTarde());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimTarde())) {
            entityOld.setHorarioFimTarde(entity.getHorarioFimTarde());
        }
        if (BasicFunctions.isValid(entity.getHorarioInicioNoite())) {
            entityOld.setHorarioInicioNoite(entity.getHorarioInicioNoite());
        }
        if (BasicFunctions.isValid(entity.getHorarioFimNoite())) {
            entityOld.setHorarioFimNoite(entity.getHorarioFimNoite());
        }
        if (BasicFunctions.isValid(entity.getHoraMinutoIntervalo())) {
            entityOld.setHoraMinutoIntervalo(entity.getHoraMinutoIntervalo());
        }
        if (BasicFunctions.isValid(entity.getHoraMinutoTolerancia())) {
            entityOld.setHoraMinutoTolerancia(entity.getHoraMinutoTolerancia());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaManha())) {
            entityOld.setAgendaManha(entity.getAgendaManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaTarde())) {
            entityOld.setAgendaTarde(entity.getAgendaTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaNoite())) {
            entityOld.setAgendaNoite(entity.getAgendaNoite());
        }
        if (BasicFunctions.isNotEmpty(entity.getConfiguradorOrganizacao())) {
            entityOld.setConfiguradorOrganizacao(entity.getConfiguradorOrganizacao());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoManha())) {
            entityOld.setAgendaSabadoManha(entity.getAgendaSabadoManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoTarde())) {
            entityOld.setAgendaSabadoTarde(entity.getAgendaSabadoTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaSabadoNoite())) {
            entityOld.setAgendaSabadoNoite(entity.getAgendaSabadoNoite());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoManha())) {
            entityOld.setAgendaDomingoManha(entity.getAgendaDomingoManha());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoTarde())) {
            entityOld.setAgendaDomingoTarde(entity.getAgendaDomingoTarde());
        }
        if (BasicFunctions.isNotEmpty(entity.getAgendaDomingoNoite())) {
            entityOld.setAgendaDomingoNoite(entity.getAgendaDomingoNoite());
        }
        if (BasicFunctions.isNotEmpty(entity.getAtendeSabado())) {
            entityOld.setAtendeSabado(entity.getAtendeSabado());
        }
        if (BasicFunctions.isNotEmpty(entity.getAtendeDomingo())) {
            entityOld.setAtendeDomingo(entity.getAtendeDomingo());
        }

        entityOld.setUsuario(usuarioAuth);
        entityOld.setUsuarioAcao(usuarioAuth);
        entityOld.setDataAcao(Contexto.dataHoraContexto(usuarioAuth.getOrganizacaoDefaultId()));
        return entityOld;
    }

    public Boolean atendeSabado() {
        return BasicFunctions.isNotNull(this.atendeSabado) && this.atendeSabado;
    }

    public Boolean atendeDomingo() {
        return BasicFunctions.isNotNull(this.atendeDomingo) && this.atendeDomingo;
    }

    public Boolean naoAtendeFimDeSemana(LocalDate dataAgendamento) {
        return this.naoAtendeSabado(dataAgendamento) || this.naoAtendeDomingo(dataAgendamento);
    }

    public Boolean naoAtendeSabado(LocalDate dataAgendamento) {
        return !this.atendeSabado() && dataAgendamento.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    public Boolean naoAtendeDomingo(LocalDate dataAgendamento) {
        return !this.atendeDomingo() && dataAgendamento.getDayOfWeek().equals(DayOfWeek.SUNDAY);
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

    public Organizacao getOrganizacaoConfigurador() {
        return organizacaoConfigurador;
    }

    public void setOrganizacaoConfigurador(Organizacao organizacaoConfigurador) {
        this.organizacaoConfigurador = organizacaoConfigurador;
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

    public Usuario getProfissionalConfigurador() {
        return profissionalConfigurador;
    }

    public void setProfissionalConfigurador(Usuario profissionalConfigurador) {
        this.profissionalConfigurador = profissionalConfigurador;
    }

    public Boolean getConfiguradorOrganizacao() {
        return configuradorOrganizacao;
    }

    public void setConfiguradorOrganizacao(Boolean configuradorOrganizacao) {
        this.configuradorOrganizacao = configuradorOrganizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioAcao() {
        return usuarioAcao;
    }

    public void setUsuarioAcao(Usuario usuarioAcao) {
        this.usuarioAcao = usuarioAcao;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }
}
