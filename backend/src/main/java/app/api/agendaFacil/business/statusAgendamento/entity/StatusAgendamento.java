package app.api.agendaFacil.business.statusAgendamento.entity;

import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.repository.StatusAgendamentoRepository;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "statusAgendamento")
public class StatusAgendamento extends EntityBase {

    public static final long AGENDADO = 1;
    public static final long REMARCADO = 2;
    public static final long CANCELADO = 3;
    public static final long LIVRE = 4;
    public static final long ATENDIDO = 5;

    @Column()
    @SequenceGenerator(name = "statusAgendamentoIdSequence", sequenceName = "statusAgendamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String status;

    public StatusAgendamento() {
        super();
    }

    public StatusAgendamento(Long id) {
        super();
        this.id = id;
    }

    @Inject
    protected StatusAgendamento(SecurityContext context) {
        super(context);
    }

    public StatusAgendamento(StatusAgendamentoDTO statusAgendamentoDTO, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(statusAgendamentoDTO)) {
            this.id = statusAgendamentoDTO.getId();
            this.status = statusAgendamentoDTO.getStatus();
        }
    }

    public StatusAgendamento(StatusAgendamentoDTO statusAgendamentoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(statusAgendamentoDTO)) {
            this.id = statusAgendamentoDTO.getId();
            this.status = statusAgendamentoDTO.getStatus();
        }
    }

    public static StatusAgendamento statusLivre() {
        return StatusAgendamentoRepository.findById(StatusAgendamento.LIVRE);
    }

    public static StatusAgendamento statusAgendado() {
        return StatusAgendamentoRepository.findById(StatusAgendamento.AGENDADO);
    }

    public static StatusAgendamento statusRemarcado() {
        return StatusAgendamentoRepository.findById(StatusAgendamento.REMARCADO);
    }

    public static StatusAgendamento statusCancelado() {
        return StatusAgendamentoRepository.findById(StatusAgendamento.CANCELADO);
    }

    public StatusAgendamento statusAgendamento(StatusAgendamento entityOld, StatusAgendamentoDTO entity) {

        if (BasicFunctions.isNotEmpty(entity)) {
            entityOld.status = entity.getStatus();
        }
        return entityOld;
    }

    public Boolean agendado() {
        return this.id == AGENDADO;
    }

    public Boolean remarcado() {
        return this.id == REMARCADO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
