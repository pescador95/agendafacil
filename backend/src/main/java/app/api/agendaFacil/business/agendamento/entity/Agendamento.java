package app.api.agendaFacil.business.agendamento.entity;

import app.api.agendaFacil.business.agendamento.DTO.AgendamentoDTO;
import app.api.agendaFacil.business.agendamento.listener.AgendamentoListener;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento", indexes = {
        @Index(name = "iagendamentoak1", columnList = "dataAgendamento, horarioAgendamento, pessoaId, profissionalId, organizacaoId, StatusAgendamentoId, ativo")
})
@EntityListeners(AgendamentoListener.class)
public class Agendamento extends Audit {

    @Column()
    @SequenceGenerator(name = "agendamentoIdSequence", sequenceName = "agendamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipoAgendamentoId")
    private TipoAgendamento tipoAgendamento;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoaAgendamento;

    @ManyToOne
    @JoinColumn(name = "profissionalId")
    private Usuario profissionalAgendamento;

    @ManyToOne
    @JoinColumn(name = "statusAgendamentoId")
    private StatusAgendamento statusAgendamento;

    @ManyToOne
    @JoinColumn(name = "organizacaoId")
    private Organizacao organizacaoAgendamento;

    @Column()
    private LocalDate dataAgendamento;

    @Column()
    private LocalTime horarioAgendamento;

    @Column()
    private Boolean comPreferencia = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agendamentoOldId")
    private Agendamento agendamentoOld;

    @Column()
    private Boolean ativo;

    public Agendamento() {
    }

    public Agendamento(SecurityContext context) {
        super(context);
    }

    public Agendamento(AgendamentoDTO entityDTO) {
        super();
        if (BasicFunctions.isNotEmpty(entityDTO)) {
            if (BasicFunctions.isNotEmpty(entityDTO.getId())) {
                this.id = entityDTO.getId();
            }
            this.dataAgendamento = entityDTO.getDataAgendamento();
            this.horarioAgendamento = entityDTO.getHorarioAgendamento();
            this.ativo = entityDTO.getAtivo();
            this.statusAgendamento = new StatusAgendamento(StatusAgendamento.AGENDADO);
            this.comPreferencia = entityDTO.getComPreferencia();

            if (BasicFunctions.isNotEmpty(entityDTO.getTipoAgendamento())) {
                this.tipoAgendamento = new TipoAgendamento(entityDTO.getTipoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getStatusAgendamento())) {
                this.statusAgendamento = new StatusAgendamento(entityDTO.getStatusAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getOrganizacaoAgendamento())) {
                this.organizacaoAgendamento = new Organizacao(entityDTO.getOrganizacaoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getPessoaAgendamento())) {
                this.pessoaAgendamento = new Pessoa(entityDTO.getPessoaAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getProfissionalAgendamento())) {
                this.profissionalAgendamento = new Usuario(entityDTO.getProfissionalAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getAgendamentoOld())) {
                this.agendamentoOld = AgendamentoRepository.findById(entityDTO.getAgendamentoOld());
            }
        }
    }

    public Agendamento(AgendamentoDTO entityDTO, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(entityDTO)) {
            if (BasicFunctions.isNotEmpty(entityDTO.getId())) {
                this.id = entityDTO.getId();
            }
            this.dataAgendamento = entityDTO.getDataAgendamento();
            this.horarioAgendamento = entityDTO.getHorarioAgendamento();
            this.ativo = entityDTO.getAtivo();
            this.statusAgendamento = new StatusAgendamento(StatusAgendamento.AGENDADO);
            this.comPreferencia = entityDTO.getComPreferencia();

            if (BasicFunctions.isNotEmpty(entityDTO.getTipoAgendamento())) {
                this.tipoAgendamento = new TipoAgendamento(entityDTO.getTipoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getStatusAgendamento())) {
                this.statusAgendamento = new StatusAgendamento(entityDTO.getStatusAgendamento(), context);
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getOrganizacaoAgendamento())) {
                this.organizacaoAgendamento = new Organizacao(entityDTO.getOrganizacaoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getPessoaAgendamento())) {
                this.pessoaAgendamento = new Pessoa(entityDTO.getPessoaAgendamento(), context);
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getProfissionalAgendamento())) {
                this.profissionalAgendamento = new Usuario(entityDTO.getProfissionalAgendamento(), context);
            }

            if (BasicFunctions.isNotEmpty(entityDTO.getAgendamentoOld())) {
                this.agendamentoOld = AgendamentoRepository.findById(entityDTO.getAgendamentoOld());
            }
        }
    }

    public Agendamento(Agendamento entity, Agendamento entityOld, TipoAgendamento tipoAgendamento,
                       Pessoa pessoaAgendamento, Usuario profissionalAgendamento, StatusAgendamento statusAgendamento,
                       Organizacao organizacaoAgendamento, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(entity.getId())) {
            this.id = entity.getId();
        }
        this.tipoAgendamento = tipoAgendamento;

        this.organizacaoAgendamento = organizacaoAgendamento;
        this.dataAgendamento = entity.getDataAgendamento();
        this.horarioAgendamento = entity.getHorarioAgendamento();
        this.statusAgendamento = new StatusAgendamento(StatusAgendamento.AGENDADO);
        this.comPreferencia = entity.getComPreferencia();

        this.profissionalAgendamento = BasicFunctions.isNotEmpty(profissionalAgendamento) && BasicFunctions.isValid(profissionalAgendamento)
                ? profissionalAgendamento
                : usuarioAuth;

        if (BasicFunctions.isValid(entity.getComPreferencia())) {
            this.comPreferencia = entity.getComPreferencia();
        }
        if (BasicFunctions.isNotEmpty(statusAgendamento) && BasicFunctions.isValid(statusAgendamento)) {
            this.statusAgendamento = statusAgendamento;
        }
        if (BasicFunctions.isNotEmpty(pessoaAgendamento)) {
            this.pessoaAgendamento = pessoaAgendamento;
        }
        if (BasicFunctions.isNotEmpty(entityOld) && BasicFunctions.isValid(entityOld)) {
            this.agendamentoOld = entityOld;
        }
        this.ativo = Boolean.TRUE;
        this.createAudit(context);
    }

    public Agendamento agendamento(Agendamento entityOld, Agendamento entity, TipoAgendamento tipoAgendamento,
                                   Pessoa pessoaAgendamento, Usuario profissionalAgendamento, StatusAgendamento statusAgendamento,
                                   Organizacao organizacaoAgendamento, SecurityContext context) {

        entityOld.tipoAgendamento = tipoAgendamento;
        entityOld.comPreferencia = entity.getComPreferencia();
        entityOld.organizacaoAgendamento = organizacaoAgendamento;
        entityOld.dataAgendamento = entity.getDataAgendamento();
        entityOld.horarioAgendamento = entity.getHorarioAgendamento();
        entityOld.statusAgendamento = new StatusAgendamento(StatusAgendamento.AGENDADO);

        entityOld.profissionalAgendamento = BasicFunctions.isNotEmpty(profissionalAgendamento) && BasicFunctions.isValid(profissionalAgendamento)
                ? profissionalAgendamento
                : usuarioAuth;

        if (BasicFunctions.isValid(entity.getComPreferencia())) {
            entityOld.comPreferencia = entity.getComPreferencia();
        }
        if (BasicFunctions.isNotEmpty(statusAgendamento) && BasicFunctions.isValid(statusAgendamento)) {
            entityOld.statusAgendamento = statusAgendamento;
        }
        if (BasicFunctions.isNotEmpty(pessoaAgendamento)) {
            entityOld.pessoaAgendamento = pessoaAgendamento;
        }
        if (BasicFunctions.isNotEmpty(entityOld) && BasicFunctions.isValid(entityOld)) {
            entityOld.agendamentoOld = entityOld;
        }
        entityOld.ativo = Boolean.TRUE;
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Boolean hasAgendamentoOld() {
        return BasicFunctions.isNotEmpty(this.agendamentoOld) && BasicFunctions.isValid(this.agendamentoOld.id);
    }

    public Agendamento cancelarAgendamento(Agendamento entity, SecurityContext context) {

        entity.setStatusAgendamento(StatusAgendamento.statusCancelado());
        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public Agendamento marcarComoLivre(Agendamento entity, SecurityContext context) {

        entity.setStatusAgendamento(StatusAgendamento.statusLivre());
        entity.setAtivo(Boolean.TRUE);
        entity.updateAudit(context);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoAgendamento getTipoAgendamento() {
        return tipoAgendamento;
    }

    public void setTipoAgendamento(TipoAgendamento tipoAgendamento) {
        this.tipoAgendamento = tipoAgendamento;
    }

    public Pessoa getPessoaAgendamento() {
        return pessoaAgendamento;
    }

    public void setPessoaAgendamento(Pessoa pessoaAgendamento) {
        this.pessoaAgendamento = pessoaAgendamento;
    }

    public Usuario getProfissionalAgendamento() {
        return profissionalAgendamento;
    }

    public void setProfissionalAgendamento(Usuario profissionalAgendamento) {
        this.profissionalAgendamento = profissionalAgendamento;
    }

    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    public Organizacao getOrganizacaoAgendamento() {
        return organizacaoAgendamento;
    }

    public void setOrganizacaoAgendamento(Organizacao organizacaoAgendamento) {
        this.organizacaoAgendamento = organizacaoAgendamento;
    }

    public LocalDate getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(LocalDate dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public LocalTime getHorarioAgendamento() {
        return horarioAgendamento;
    }

    public void setHorarioAgendamento(LocalTime horarioAgendamento) {
        this.horarioAgendamento = horarioAgendamento;
    }

    public Boolean getComPreferencia() {
        return comPreferencia;
    }

    public void setComPreferencia(Boolean comPreferencia) {
        this.comPreferencia = comPreferencia;
    }

    public Agendamento getAgendamentoOld() {
        return agendamentoOld;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean ativo() {
        return BasicFunctions.isValid(this.ativo) && this.ativo;
    }

    public Boolean isValidPessoaTelegramId() {
        return BasicFunctions.isNotEmpty(this, this.getPessoaAgendamento(), this.getPessoaAgendamento().getTelegramId());
    }

    public Boolean isValidPessoaWhatsappId() {
        return BasicFunctions.isNotEmpty(this, this.getPessoaAgendamento(), this.getPessoaAgendamento().getWhatsappId());
    }

    public Boolean isValidProfissionalTelegramId() {
        return BasicFunctions.isNotEmpty(this, this.getProfissionalAgendamento(), this.getProfissionalAgendamento().getTelegramId());
    }

    public Boolean isValidProfissionalWhatsappId() {
        return BasicFunctions.isNotEmpty(this, this.getProfissionalAgendamento(), this.getProfissionalAgendamento().getWhatsappId());
    }

    public Boolean isValidPessoaContato() {
        return BasicFunctions.isNotEmpty(this) && this.isValidPessoaTelegramId() || this.isValidPessoaWhatsappId();
    }

    public Boolean isValidProfissionalContato() {
        return BasicFunctions.isNotEmpty(this) && this.isValidProfissionalTelegramId() || this.isValidProfissionalWhatsappId();
    }

    public Boolean agendamentoPendente() {
        return BasicFunctions.isNotEmpty(this) && this.ativo() && this.getStatusAgendamento().agendado() &&
                this.getDataAgendamento().isEqual(Contexto.dataContexto(this.getOrganizacaoAgendamento().getZoneId())) &&
                (this.isValidPessoaContato() || this.isValidProfissionalContato());
    }

    public Boolean removerAgendamentoDaFila() {
        if (BasicFunctions.isNotEmpty(this) && this.ativo() && !this.getStatusAgendamento().agendado() || !this.ativo()
                || this.getStatusAgendamento().agendado()
                && this.getDataAgendamento().isEqual(Contexto.dataContexto(this.organizacaoAgendamento.getZoneId()))
                && this.getHorarioAgendamento().isBefore(Contexto.horarioContexto(this.organizacaoAgendamento.getZoneId()))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean adicionarAgendamentoNaFila() {
        if (BasicFunctions.isNotEmpty(this) && this.ativo() && this.getStatusAgendamento().agendado()
                && this.getDataAgendamento().isAfter(Contexto.dataContexto(this.organizacaoAgendamento.getZoneId()))
                || this.getDataAgendamento().isEqual(Contexto.dataContexto(this.organizacaoAgendamento.getZoneId())) && !this.getHorarioAgendamento().isBefore(Contexto.horarioContexto(this.getOrganizacaoAgendamento().getZoneId()))) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean pessoasDistintas() {
        return BasicFunctions.isNotEmpty(this, this.getPessoaAgendamento(), this.getProfissionalAgendamento().getPessoa()) && !this.getPessoaAgendamento().equals(this.getProfissionalAgendamento().getPessoa());
    }

    public Boolean profissionalValido() {
        return BasicFunctions.isNotEmpty(this) && this.pessoasDistintas() && this.isValidProfissionalTelegramId() || this.isValidProfissionalWhatsappId();
    }

    public Boolean clienteValido() {
        return BasicFunctions.isNotEmpty(this) && this.pessoasDistintas() && this.isValidPessoaTelegramId() || this.isValidPessoaWhatsappId();
    }
}
