package app.api.agendaFacil.business.atendimento.DTO;


import app.api.agendaFacil.business.atendimento.entity.Atendimento;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalDateTime;

public class AtendimentoDTO {

    private Long id;
    private LocalDateTime dataAtendimento;

    private String atividade;
    private String evolucaoSintomas;

    private String avaliacao;
    private PessoaDTO pessoa;
    private UsuarioDTO profissionalAtendimento;
    private Boolean ativo;

    public AtendimentoDTO() {

    }

    public AtendimentoDTO(Long id, LocalDateTime dataAtendimento, String atividade, String evolucaoSintomas, String avaliacao, Entidade pessoa, Usuario profissionalAtendimento, Boolean ativo) {

        this.id = id;
        this.dataAtendimento = dataAtendimento;
        this.atividade = atividade;
        this.evolucaoSintomas = evolucaoSintomas;
        this.avaliacao = avaliacao;
        this.ativo = ativo;

        this.pessoa = new PessoaDTO(pessoa);
        this.profissionalAtendimento = new UsuarioDTO(profissionalAtendimento);

    }

    public AtendimentoDTO(Atendimento atendimento) {

        if (BasicFunctions.isNotEmpty(atendimento)) {

            this.id = atendimento.getId();
            this.dataAtendimento = atendimento.getDataAtendimento();
            this.atividade = atendimento.getAtividade();
            this.evolucaoSintomas = atendimento.getEvolucaoSintomas();
            this.avaliacao = atendimento.getAvaliacao();
            this.ativo = atendimento.getAtivo();

            if (BasicFunctions.isNotEmpty(atendimento.getProfissionalAtendimento())) {
                this.profissionalAtendimento = new UsuarioDTO(atendimento.getProfissionalAtendimento());
            }

            if (BasicFunctions.isNotEmpty(atendimento.getPessoa())) {
                this.pessoa = new PessoaDTO(atendimento.getPessoa());
            }
        }
    }

    public AtendimentoDTO(AtendimentoDTO atendimento) {

        if (BasicFunctions.isNotEmpty(atendimento)) {

            this.id = atendimento.getId();
            this.dataAtendimento = atendimento.getDataAtendimento();
            this.atividade = atendimento.getAtividade();
            this.evolucaoSintomas = atendimento.getEvolucaoSintomas();
            this.avaliacao = atendimento.getAvaliacao();
            this.ativo = atendimento.getAtivo();

            if (BasicFunctions.isNotEmpty(atendimento.getProfissionalAtendimento())) {
                this.profissionalAtendimento = atendimento.getProfissionalAtendimento();
            }

            if (BasicFunctions.isNotEmpty(atendimento.getPessoa())) {
                this.pessoa = atendimento.getPessoa();
            }
        }
    }

    public AtendimentoDTO(Long id, LocalDateTime dataAtendimento, String atividade, String evolucaoSintomas, String avaliacao, PessoaDTO pessoa, UsuarioDTO profissionalAtendimento, Boolean ativo) {
        this.id = id;
        this.dataAtendimento = dataAtendimento;
        this.atividade = atividade;
        this.evolucaoSintomas = evolucaoSintomas;
        this.avaliacao = avaliacao;
        this.pessoa = pessoa;
        this.profissionalAtendimento = profissionalAtendimento;
        this.ativo = ativo;
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

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getEvolucaoSintomas() {
        return evolucaoSintomas;
    }

    public void setEvolucaoSintomas(String evolucaoSintomas) {
        this.evolucaoSintomas = evolucaoSintomas;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoaAtendimento) {
        this.pessoa = pessoaAtendimento;
    }

    public UsuarioDTO getProfissionalAtendimento() {
        return profissionalAtendimento;
    }

    public void setProfissionalAtendimento(UsuarioDTO profissionalAtendimento) {
        this.profissionalAtendimento = profissionalAtendimento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}