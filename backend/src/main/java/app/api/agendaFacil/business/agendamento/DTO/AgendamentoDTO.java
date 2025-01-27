package app.api.agendaFacil.business.agendamento.DTO;

import app.api.agendaFacil.business.agendamento.entity.Agendamento;
import app.api.agendaFacil.business.endereco.DTO.EnderecoDTO;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.statusAgendamento.DTO.StatusAgendamentoDTO;
import app.api.agendaFacil.business.statusAgendamento.entity.StatusAgendamento;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.time.LocalDate;
import java.time.LocalTime;

public class AgendamentoDTO {

    private Long id;

    private LocalDate dataAgendamento;

    private LocalTime horarioAgendamento;

    @JsonIncludeProperties({"id", "cep", "enderecoCompleto"})
    private EnderecoDTO endereco;

    @JsonIncludeProperties({"id", "nome", "celular"})
    private PessoaDTO pessoaAgendamento;

    @JsonIncludeProperties({"id", "nome", "celular"})
    private OrganizacaoDTO organizacaoAgendamento;

    @JsonIncludeProperties({"id", "nomeProfissional"})
    private UsuarioDTO profissionalAgendamento;

    private TipoAgendamentoDTO tipoAgendamento;

    private StatusAgendamentoDTO statusAgendamento;

    private Boolean comPreferencia = false;

    @JsonIncludeProperties({"id", "dataAgendamento", "horarioAgendamento", "pessoaAgendamento",
            "organizacaoAgendamento", "profissionalAgendamento", "tipoAgendamento", "statusAgendamento"})
    private Long agendamentoOld;

    private Boolean ativo;

    public AgendamentoDTO() {

    }

    public AgendamentoDTO(Long id,
                          LocalDate dataAgendamento,
                          LocalTime horarioAgendamento,
                          Pessoa pessoaAgendamento,
                          Organizacao organizacaoAgendamento,
                          Usuario profissionalAgendamento,
                          TipoAgendamento tipoAgendamentos,
                          StatusAgendamento statusAgendamento,
                          Boolean ativo) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
        this.horarioAgendamento = horarioAgendamento;
        this.ativo = ativo;

        if (BasicFunctions.isNotEmpty(organizacaoAgendamento)) {
            this.organizacaoAgendamento = new OrganizacaoDTO(organizacaoAgendamento);
        }
        if (BasicFunctions.isNotEmpty(tipoAgendamentos)) {
            this.tipoAgendamento = new TipoAgendamentoDTO(tipoAgendamentos);
        }
        if (BasicFunctions.isNotEmpty(statusAgendamento)) {
            this.statusAgendamento = new StatusAgendamentoDTO(statusAgendamento);
        }
        if (BasicFunctions.isNotEmpty(pessoaAgendamento)) {
            this.pessoaAgendamento = new PessoaDTO(pessoaAgendamento);
        }
        if (BasicFunctions.isNotEmpty(profissionalAgendamento)) {
            this.profissionalAgendamento = new UsuarioDTO(profissionalAgendamento);
        }
    }

    public AgendamentoDTO(Agendamento agendamento) {

        if (BasicFunctions.isNotEmpty(agendamento)) {
            this.id = agendamento.getId();

            if (BasicFunctions.isNotEmpty(agendamento.getAgendamentoOld())) {
                this.agendamentoOld = agendamento.getAgendamentoOld().getId();
            }

            if (BasicFunctions.isNotEmpty(agendamento.getTipoAgendamento())) {
                this.tipoAgendamento = new TipoAgendamentoDTO(agendamento.getTipoAgendamento());
            }

            if (BasicFunctions.isNotEmpty(agendamento.getPessoaAgendamento())) {
                this.pessoaAgendamento = new PessoaDTO(agendamento.getPessoaAgendamento());
            }

            if (BasicFunctions.isNotEmpty(agendamento.getProfissionalAgendamento())) {
                this.profissionalAgendamento = new UsuarioDTO(agendamento.getProfissionalAgendamento());
            }

            if (BasicFunctions.isNotEmpty(agendamento.getOrganizacaoAgendamento())) {
                this.organizacaoAgendamento = new OrganizacaoDTO(agendamento.getOrganizacaoAgendamento());

                if (BasicFunctions.isNotEmpty(agendamento.getOrganizacaoAgendamento().getEndereco())) {
                    this.endereco = new EnderecoDTO(agendamento.getOrganizacaoAgendamento().getEndereco());
                }
            }

            if (BasicFunctions.isNotEmpty(agendamento.getStatusAgendamento())) {
                this.statusAgendamento = new StatusAgendamentoDTO(agendamento.getStatusAgendamento());
            }

            this.comPreferencia = agendamento.getComPreferencia();
            this.dataAgendamento = agendamento.getDataAgendamento();
            this.horarioAgendamento = agendamento.getHorarioAgendamento();
            this.ativo = agendamento.getAtivo();
        }
    }

    public AgendamentoDTO(Long id, LocalDate dataAgendamento, LocalTime horarioAgendamento, EnderecoDTO endereco, PessoaDTO pessoaAgendamento, OrganizacaoDTO organizacaoAgendamento, UsuarioDTO profissionalAgendamento, TipoAgendamentoDTO tipoAgendamento, StatusAgendamentoDTO statusAgendamento, Boolean comPreferencia, Long agendamentoOld, Boolean ativo) {
        this.id = id;
        this.dataAgendamento = dataAgendamento;
        this.horarioAgendamento = horarioAgendamento;
        this.endereco = endereco;
        this.pessoaAgendamento = pessoaAgendamento;
        this.organizacaoAgendamento = organizacaoAgendamento;
        this.profissionalAgendamento = profissionalAgendamento;
        this.tipoAgendamento = tipoAgendamento;
        this.statusAgendamento = statusAgendamento;
        this.comPreferencia = comPreferencia;
        this.agendamentoOld = agendamentoOld;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public PessoaDTO getPessoaAgendamento() {
        return pessoaAgendamento;
    }

    public void setPessoaAgendamento(PessoaDTO pessoaAgendamento) {
        this.pessoaAgendamento = pessoaAgendamento;
    }

    public OrganizacaoDTO getOrganizacaoAgendamento() {
        return organizacaoAgendamento;
    }

    public void setOrganizacaoAgendamento(OrganizacaoDTO organizacaoAgendamento) {
        this.organizacaoAgendamento = organizacaoAgendamento;
    }

    public UsuarioDTO getProfissionalAgendamento() {
        return profissionalAgendamento;
    }

    public void setProfissionalAgendamento(UsuarioDTO profissionalAgendamento) {
        this.profissionalAgendamento = profissionalAgendamento;
    }

    public TipoAgendamentoDTO getTipoAgendamento() {
        return tipoAgendamento;
    }

    public void setTipoAgendamento(TipoAgendamentoDTO tipoAgendamento) {
        this.tipoAgendamento = tipoAgendamento;
    }

    public StatusAgendamentoDTO getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(StatusAgendamentoDTO statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Long getAgendamentoOld() {
        return this.agendamentoOld;
    }

    public void setAgendamentoOld(Long agendamentoOld) {
        this.agendamentoOld = agendamentoOld;
    }

    public Boolean getComPreferencia() {
        return comPreferencia;
    }

    public void setComPreferencia(Boolean comPreferencia) {
        this.comPreferencia = comPreferencia;
    }


    public Boolean hasAgendamentoOld() {
        return BasicFunctions.isNotEmpty(this.agendamentoOld) && BasicFunctions.isValid(this.agendamentoOld);
    }
}
