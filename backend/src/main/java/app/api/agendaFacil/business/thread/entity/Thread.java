package app.api.agendaFacil.business.thread.entity;

import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@Entity
@Table(name = "thread")
public class Thread extends EntityBase {

    public static final Long STATUS_PENDENTE = 1L;
    public static final Long STATUS_EM_EXECUCAO = 2L;
    public static final Long STATUS_FINALIZADO = 3L;
    public static final Long STATUS_FALHA = 4L;
    public static final Long STATUS_CANCELADO = 5L;
    @Column()
    String statusDescricao;
    @Column()
    @JsonIgnore
    LocalDateTime dataHoraInicio;
    @Column()
    @JsonIgnore
    LocalDateTime dataHoraFim;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private Long status;
    @Column()
    @JsonIgnore
    private Boolean ativo;
    @Column()
    @JsonIgnore
    private LocalDateTime dataAcao;

    public Thread() {
        super();
        this.setStatus(Thread.STATUS_PENDENTE);
        this.setAtivo(Boolean.TRUE);
        this.setStatusDescricao(this.statusDestricao());
        this.setDataAcao(Contexto.dataHoraContexto());
    }

    protected Thread(SecurityContext context) {
        super(context);
        this.setStatus(Thread.STATUS_PENDENTE);
        this.setAtivo(Boolean.TRUE);
        this.setStatusDescricao(this.statusDestricao());
        this.setDataAcao(Contexto.dataHoraContexto());
    }

    public String statusDestricao() {
        if (BasicFunctions.isValid(this.status) && this.status.equals(STATUS_PENDENTE)) {
            return "Pendente";
        }
        if (BasicFunctions.isValid(this.status) && this.status.equals(STATUS_EM_EXECUCAO)) {
            return "Em execução";
        }
        if (BasicFunctions.isValid(this.status) && this.status.equals(STATUS_FINALIZADO)) {
            return "Finalizado";
        }
        if (BasicFunctions.isValid(this.status) && this.status.equals(STATUS_FALHA)) {
            return "Falha";
        }
        if (BasicFunctions.isValid(this.status) && this.status.equals(STATUS_CANCELADO)) {
            return "Cancelado";
        }
        return "Não identificado";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public void setStatusDescricao(String statusDescricao) {
        this.statusDescricao = statusDescricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDateTime dataAcao) {
        this.dataAcao = dataAcao;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }
}
