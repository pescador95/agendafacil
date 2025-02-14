package app.api.agendaFacil.business.historicoPessoa.entity;

import app.api.agendaFacil.business.historicoPessoa.DTO.HistoricoPessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Entity
@Table(name = "historicoPessoa", indexes = {
        @Index(name = "ihistoricopessoaak1", columnList = "pessoaId, ativo")
})
public class HistoricoPessoa extends Audit {

    @Column()
    @SequenceGenerator(name = "historicopessoaIdSequence", sequenceName = "historicopessoa_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String queixaPrincipal;

    @Column()
    private String medicamentos;

    @Column(columnDefinition = "TEXT")
    private String diagnosticoClinico;

    @Column()
    private String comorbidades;

    @Column()
    private String ocupacao;

    @Column()
    private String responsavelContato;

    @Column()
    private String nomePessoa;

    @ManyToOne
    @JoinColumn(name = "pessoaId")
    private Pessoa pessoa;

    @Column()
    private Boolean ativo;

    public HistoricoPessoa() {
        super();
    }

    public HistoricoPessoa(SecurityContext context) {
        super(context);
    }

    public HistoricoPessoa(HistoricoPessoaDTO historicoPessoaDTO) {

        if (BasicFunctions.isNotEmpty(historicoPessoaDTO)) {
            this.id = historicoPessoaDTO.getId();
            this.queixaPrincipal = historicoPessoaDTO.getQueixaPrincipal();
            this.medicamentos = historicoPessoaDTO.getMedicamentos();
            this.diagnosticoClinico = historicoPessoaDTO.getDiagnosticoClinico();
            this.comorbidades = historicoPessoaDTO.getComorbidades();
            this.ocupacao = historicoPessoaDTO.getOcupacao();
            this.responsavelContato = historicoPessoaDTO.getResponsavelContato();
        }
    }

    public HistoricoPessoa(HistoricoPessoa pHistoricoPessoa, Pessoa pessoa, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(pessoa)) {
            this.setPessoa(pessoa);
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getQueixaPrincipal())) {
            this.setQueixaPrincipal(pHistoricoPessoa.getQueixaPrincipal());
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getMedicamentos())) {
            this.setMedicamentos(pHistoricoPessoa.getMedicamentos());
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getDiagnosticoClinico())) {
            this.setDiagnosticoClinico(pHistoricoPessoa.getDiagnosticoClinico());
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getComorbidades())) {
            this.setComorbidades(pHistoricoPessoa.getComorbidades());
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getOcupacao())) {
            this.setOcupacao(pHistoricoPessoa.getOcupacao());
        }
        if (BasicFunctions.isNotEmpty(pHistoricoPessoa.getResponsavelContato())) {
            this.setResponsavelContato(pHistoricoPessoa.getResponsavelContato());
        }
        this.setPessoa(pessoa);
        this.setNomePessoa(pessoa.getNome());
        this.setAtivo(Boolean.TRUE);
        this.createAudit(context);
    }

    public HistoricoPessoa(HistoricoPessoaDTO entity, Pessoa pessoa, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(pessoa)) {
            this.setPessoa(pessoa);
        }
        if (BasicFunctions.isNotEmpty(entity.getQueixaPrincipal())) {
            this.setQueixaPrincipal(entity.getQueixaPrincipal());
        }
        if (BasicFunctions.isNotEmpty(entity.getMedicamentos())) {
            this.setMedicamentos(entity.getMedicamentos());
        }
        if (BasicFunctions.isNotEmpty(entity.getDiagnosticoClinico())) {
            this.setDiagnosticoClinico(entity.getDiagnosticoClinico());
        }
        if (BasicFunctions.isNotEmpty(entity.getComorbidades())) {
            this.setComorbidades(entity.getComorbidades());
        }
        if (BasicFunctions.isNotEmpty(entity.getOcupacao())) {
            this.setOcupacao(entity.getOcupacao());
        }
        if (BasicFunctions.isNotEmpty(entity.getResponsavelContato())) {
            this.setResponsavelContato(entity.getResponsavelContato());
        }
        this.setPessoa(pessoa);
        this.setNomePessoa(pessoa.getNome());
        this.setAtivo(Boolean.TRUE);
        this.createAudit(context);
    }

    public HistoricoPessoa historicoPessoa(HistoricoPessoa entityOld, HistoricoPessoaDTO entity, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(entity.getQueixaPrincipal())) {
            entityOld.setQueixaPrincipal(entity.getQueixaPrincipal());
        }
        if (BasicFunctions.isNotEmpty(entity.getMedicamentos())) {
            entityOld.setMedicamentos(entity.getMedicamentos());
        }
        if (BasicFunctions.isNotEmpty(entity.getDiagnosticoClinico())) {
            entityOld.setDiagnosticoClinico(entity.getDiagnosticoClinico());
        }
        if (BasicFunctions.isNotEmpty(entity.getComorbidades())) {
            entityOld.setComorbidades(entity.getComorbidades());
        }
        if (BasicFunctions.isNotEmpty(entity.getOcupacao())) {
            entityOld.setOcupacao(entity.getOcupacao());
        }
        if (BasicFunctions.isNotEmpty(entity.getResponsavelContato())) {
            entityOld.setResponsavelContato(entity.getResponsavelContato());
        }
        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public HistoricoPessoa deletarHistoricoPessoa(HistoricoPessoa entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public HistoricoPessoa reativarHistoricoPessoa(HistoricoPessoa entity, SecurityContext context) {

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

    public String getQueixaPrincipal() {
        return queixaPrincipal;
    }

    public void setQueixaPrincipal(String queixaPrincipal) {
        this.queixaPrincipal = queixaPrincipal;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getDiagnosticoClinico() {
        return diagnosticoClinico;
    }

    public void setDiagnosticoClinico(String diagnosticoClinico) {
        this.diagnosticoClinico = diagnosticoClinico;
    }

    public String getComorbidades() {
        return comorbidades;
    }

    public void setComorbidades(String comorbidades) {
        this.comorbidades = comorbidades;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getResponsavelContato() {
        return responsavelContato;
    }

    public void setResponsavelContato(String responsavelContato) {
        this.responsavelContato = responsavelContato;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

}
