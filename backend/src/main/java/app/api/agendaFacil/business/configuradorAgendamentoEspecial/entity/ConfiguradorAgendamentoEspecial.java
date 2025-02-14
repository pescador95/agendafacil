package app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO.ConfiguradorAgendamentoEspecialDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "configuradorAgendamentoEspecial", indexes = {
        @Index(name = "iconfiguradoragendamentoespecialak1", columnList = "dataInicio, dataFim, profissionalId, organizacaoId")
})
public class ConfiguradorAgendamentoEspecial extends EntityBase {

    @Column()
    @SequenceGenerator(name = "configuradorAgendamentoEspecialIdSequence", sequenceName = "configuradorAgendamentoEspecial_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String nome;

    @ManyToOne
    @JoinColumn(name = "profissionalId")
    private Usuario profissionalConfigurador;

    @Column()
    private LocalDate dataInicio;
    @Column()
    private LocalDate dataFim;

    @ManyToOne
    @JoinColumn(name = "organizacaoId")
    private Organizacao organizacaoConfigurador;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "configuradorespecialtipoagendamento", joinColumns = {
            @JoinColumn(name = "configAgendamentoEspecialId")}, inverseJoinColumns = {
            @JoinColumn(name = "tipoAgendamentoId")})
    private List<TipoAgendamento> tiposAgendamentos;

    @ManyToOne
    @JoinColumn(name = "usuarioAcaoId")
    private Usuario usuarioAcao;

    @Column()
    private LocalDateTime dataAcao;

    public ConfiguradorAgendamentoEspecial() {
        super();
    }

    @Inject
    protected ConfiguradorAgendamentoEspecial(SecurityContext context) {
        super(context);
    }

    public ConfiguradorAgendamentoEspecial(Long id) {
        super();
        this.id = id;
    }

    public ConfiguradorAgendamentoEspecial(ConfiguradorAgendamentoEspecialDTO configuradorAgendamentoEspecialDTO) {
        super();
        if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecialDTO)) {
            this.id = configuradorAgendamentoEspecialDTO.getId();
            this.nome = configuradorAgendamentoEspecialDTO.getNome();
            this.dataInicio = configuradorAgendamentoEspecialDTO.getDataInicio();
            this.dataFim = configuradorAgendamentoEspecialDTO.getDataFim();

            this.organizacaoConfigurador = new Organizacao(configuradorAgendamentoEspecialDTO.getOrganizacaoConfigurador());
            this.profissionalConfigurador = new Usuario(configuradorAgendamentoEspecialDTO.getProfissionalConfigurador());

            if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecialDTO.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                configuradorAgendamentoEspecialDTO.getTiposAgendamentos().forEach(configurador -> this.tiposAgendamentos.add(new TipoAgendamento(configurador))
                );
            }
        }

    }

    public ConfiguradorAgendamentoEspecial(ConfiguradorAgendamentoEspecialDTO pConfiguradorAgendamentoEspecial, List<TipoAgendamento> tiposAgendamentos, Organizacao organizacaoConfigurador, Usuario profissionalConfigurador, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(tiposAgendamentos)) {
            this.tiposAgendamentos = new ArrayList<>(tiposAgendamentos);
        }
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial.getNome())) {
            this.setNome(pConfiguradorAgendamentoEspecial.getNome());
        }
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial.getDataInicio())) {
            this.setDataInicio(pConfiguradorAgendamentoEspecial.getDataInicio());
        }
        if (BasicFunctions.isNotEmpty(pConfiguradorAgendamentoEspecial.getDataFim())) {
            this.setDataFim(pConfiguradorAgendamentoEspecial.getDataFim());
        }
        this.organizacaoConfigurador = organizacaoConfigurador;
        this.profissionalConfigurador = profissionalConfigurador;
        this.setUsuarioAcao(usuarioAuth);
        this.setDataAcao(Contexto.dataHoraContexto());
    }

    public ConfiguradorAgendamentoEspecial configuradorAgendamentoEspecial(ConfiguradorAgendamentoEspecial entityOld, ConfiguradorAgendamentoEspecialDTO entity, List<TipoAgendamento> tiposAgendamentos, Organizacao organizacaoConfigurador, Usuario profissionalConfigurador, Usuario usuarioAuth) {

        if (BasicFunctions.isNotEmpty(tiposAgendamentos)) {
            entityOld.setTiposAgendamentos(new ArrayList<>());
            entityOld.getTiposAgendamentos().addAll(tiposAgendamentos);
        }
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            entityOld.setNome(entity.getNome());
        }
        if (BasicFunctions.isNotEmpty(entity.getDataInicio())) {
            entityOld.setDataInicio(entity.getDataInicio());
        }
        if (BasicFunctions.isNotEmpty(entity.getDataFim())) {
            entityOld.setDataFim(entity.getDataFim());
        }
        entityOld.organizacaoConfigurador = organizacaoConfigurador;
        entityOld.profissionalConfigurador = profissionalConfigurador;
        entityOld.setUsuarioAcao(usuarioAuth);
        entityOld.setDataAcao(Contexto.dataHoraContexto());

        return entityOld;
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

    public Usuario getProfissionalConfigurador() {
        return profissionalConfigurador;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Organizacao getOrganizacaoConfigurador() {
        return organizacaoConfigurador;
    }


    public List<TipoAgendamento> getTiposAgendamentos() {
        return tiposAgendamentos;
    }

    public void setTiposAgendamentos(List<TipoAgendamento> tiposAgendamentos) {
        this.tiposAgendamentos = tiposAgendamentos;
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
