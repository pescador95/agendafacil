package app.api.agendaFacil.business.tipoAgendamento.entity;

import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.core.application.entity.EntityBase;
import app.core.helpers.utils.BasicFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipoAgendamento")
public class TipoAgendamento extends EntityBase {

    @Column()
    @SequenceGenerator(name = "tipoAgendamentoIdSequence", sequenceName = "tipoAgendamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String tipoAgendamento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tipoagendamentoorganizacoes", joinColumns = {
            @JoinColumn(name = "tipoagendamentoId")}, inverseJoinColumns = {
            @JoinColumn(name = "organizacaoId")})
    private List<Organizacao> organizacoes = new ArrayList<>();

    public TipoAgendamento() {
        super();
    }

    @Inject
    public TipoAgendamento(SecurityContext context) {
        super(context);
    }

    public TipoAgendamento(Long id) {
        super();
        this.id = id;
    }

    public TipoAgendamento(TipoAgendamentoDTO tipoAgendamentoDTO) {
        super();
        if (BasicFunctions.isNotEmpty(tipoAgendamentoDTO)) {
            if (BasicFunctions.isNotEmpty(tipoAgendamentoDTO.getId())) {
                this.id = tipoAgendamentoDTO.getId();
            }
            this.tipoAgendamento = tipoAgendamentoDTO.getTipoAgendamento();

            if (BasicFunctions.isNotEmpty(tipoAgendamentoDTO.getOrganizacoes())) {
                tipoAgendamentoDTO.getOrganizacoes().forEach(organizacaoDTO -> {
                    this.organizacoes.add(new Organizacao(organizacaoDTO));
                });
            }
        }
    }

    public TipoAgendamento tipoAgendamento(TipoAgendamento entityOld, TipoAgendamentoDTO entity, List<Organizacao> organizacoes) {

        entityOld.tipoAgendamento = entity.getTipoAgendamento();
        entityOld.organizacoes = organizacoes;
        return entityOld;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoAgendamento() {
        return tipoAgendamento;
    }

    public void setTipoAgendamento(String tipoAgendamento) {
        this.tipoAgendamento = tipoAgendamento;
    }

    public List<Organizacao> getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(List<Organizacao> organizacoes) {
        this.organizacoes = organizacoes;
    }
}
