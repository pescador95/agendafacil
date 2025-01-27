package app.api.agendaFacil.business.atendimento.entity;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.agendamento.repository.AgendamentoRepository;
import app.api.agendaFacil.business.agendamento.service.AgendamentoService;
import app.api.agendaFacil.business.atendimento.DTO.AtendimentoDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimento", indexes = {
        @Index(name = "iatendimentoak1", columnList = "dataAtendimento, pessoaId, ativo")
})
public class Atendimento extends Audit {

    @Column()
    @SequenceGenerator(name = "atendimentoIdSequence", sequenceName = "atendimento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private LocalDateTime dataAtendimento;

    @Column()
    private String atividade;

    @Column(columnDefinition = "TEXT")
    private String evolucaoSintomas;

    @Column(columnDefinition = "TEXT")
    private String avaliacao;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoa;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "agendamentoId")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "profissionalId")
    private Usuario profissionalAtendimento;

    @Column()
    private Boolean ativo;

    public Atendimento() {
        super();
    }

    public Atendimento(SecurityContext context) {
        super(context);
    }

    public Atendimento(Atendimento pAtendimento, Long agendamentoId, Pessoa pessoa, SecurityContext context) {
        super(context);
        if (BasicFunctions.isValid(agendamentoId)) {

            Agendamento agendamentoAtendimento = AgendamentoRepository.findById(agendamentoId);

            if (BasicFunctions.isNotEmpty(agendamentoAtendimento) && BasicFunctions.isValid(agendamentoAtendimento)) {
                AgendamentoService.setStatusAgendamentoAtendidoByAgendamento(agendamentoAtendimento);
                this.agendamento = agendamentoAtendimento;
            }
        }
        this.dataAtendimento = pAtendimento.getDataAtendimento();
        this.profissionalAtendimento = usuarioAuth;
        this.atividade = pAtendimento.getAtividade();
        this.evolucaoSintomas = pAtendimento.getEvolucaoSintomas();
        this.avaliacao = pAtendimento.getAvaliacao();
        this.pessoa = pessoa;

        this.ativo = Boolean.TRUE;
        this.createAudit(context);

    }

    public Atendimento(AtendimentoDTO atendimentoDTO, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(atendimentoDTO)) {
            this.id = atendimentoDTO.getId();
            this.dataAtendimento = atendimentoDTO.getDataAtendimento();
            this.atividade = atendimentoDTO.getAtividade();
            this.evolucaoSintomas = atendimentoDTO.getEvolucaoSintomas();
            this.avaliacao = atendimentoDTO.getAvaliacao();
            this.ativo = atendimentoDTO.getAtivo();
            if (BasicFunctions.isNotEmpty(atendimentoDTO.getPessoa())) {
                this.pessoa = new Pessoa(atendimentoDTO.getPessoa(), context);
            }
            if (BasicFunctions.isNotEmpty(atendimentoDTO.getProfissionalAtendimento())) {
                this.profissionalAtendimento = new Usuario(atendimentoDTO.getProfissionalAtendimento(), context);
            }
        }
    }

    public Atendimento atendimento(Atendimento entityOld, Atendimento pAtendimento, Long agendamentoId, Pessoa pessoa, SecurityContext context) {

        if (BasicFunctions.isValid(agendamentoId)) {

            Agendamento agendamentoAtendimento = AgendamentoRepository.findById(agendamentoId);

            if (BasicFunctions.isNotEmpty(agendamentoAtendimento) && BasicFunctions.isValid(agendamentoAtendimento)) {
                AgendamentoService.setStatusAgendamentoAtendidoByAgendamento(agendamentoAtendimento);
                entityOld.agendamento = agendamentoAtendimento;
            }
        }
        entityOld.dataAtendimento = pAtendimento.getDataAtendimento();
        entityOld.profissionalAtendimento = usuarioAuth;
        entityOld.atividade = pAtendimento.getAtividade();
        entityOld.evolucaoSintomas = pAtendimento.getEvolucaoSintomas();
        entityOld.avaliacao = pAtendimento.getAvaliacao();
        entityOld.pessoa = pessoa;
        entityOld.agendamento = pAtendimento.getAgendamento();
        entityOld.ativo = Boolean.TRUE;
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Atendimento deletarAtendimento(Atendimento entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public Atendimento reativarAgendimento(Atendimento entity, SecurityContext context) {

        entity.setAtivo(Boolean.TRUE);
        entity.restoreAudit(context);
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public String getAtividade() {
        return atividade;
    }

    public String getEvolucaoSintomas() {
        return evolucaoSintomas;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Usuario getProfissionalAtendimento() {
        return profissionalAtendimento;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
}
