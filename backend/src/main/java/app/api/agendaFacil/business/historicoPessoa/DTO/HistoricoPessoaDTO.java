package app.api.agendaFacil.business.historicoPessoa.DTO;

import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.core.helpers.utils.BasicFunctions;

public class HistoricoPessoaDTO {

    private Long id;

    private String queixaPrincipal;

    private String medicamentos;

    private String diagnosticoClinico;

    private String comorbidades;

    private String ocupacao;

    private String responsavelContato;

    private String nomePessoa;

    private PessoaDTO pessoa;

    private Boolean ativo;

    public HistoricoPessoaDTO() {
    }

    public HistoricoPessoaDTO(HistoricoPessoa historicoPessoa) {

        if (BasicFunctions.isNotEmpty(historicoPessoa)) {

            this.id = historicoPessoa.getId();

            this.queixaPrincipal = historicoPessoa.getQueixaPrincipal();

            this.medicamentos = historicoPessoa.getMedicamentos();

            this.diagnosticoClinico = historicoPessoa.getDiagnosticoClinico();

            this.comorbidades = historicoPessoa.getComorbidades();

            this.ocupacao = historicoPessoa.getOcupacao();

            this.responsavelContato = historicoPessoa.getResponsavelContato();

            this.nomePessoa = historicoPessoa.getNomePessoa();

            this.ativo = historicoPessoa.getAtivo();

            if (BasicFunctions.isNotEmpty(historicoPessoa.getPessoa())) {
                this.pessoa = new PessoaDTO(historicoPessoa.getPessoa());
            }
        }
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

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
