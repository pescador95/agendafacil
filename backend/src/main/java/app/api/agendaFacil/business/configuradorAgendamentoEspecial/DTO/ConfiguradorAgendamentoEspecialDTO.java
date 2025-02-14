package app.api.agendaFacil.business.configuradorAgendamentoEspecial.DTO;

import app.api.agendaFacil.business.configuradorAgendamentoEspecial.entity.ConfiguradorAgendamentoEspecial;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.tipoAgendamento.DTO.TipoAgendamentoDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.helpers.utils.BasicFunctions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConfiguradorAgendamentoEspecialDTO {

    private Long id;

    private String nome;

    private UsuarioDTO profissionalConfigurador;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private OrganizacaoDTO organizacaoConfigurador;

    private List<TipoAgendamentoDTO> tiposAgendamentos = new ArrayList<>();

    public ConfiguradorAgendamentoEspecialDTO() {
    }

    public ConfiguradorAgendamentoEspecialDTO(ConfiguradorAgendamentoEspecial configuradorAgendamentoEspecial) {

        if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial)) {

            this.id = configuradorAgendamentoEspecial.getId();

            this.nome = configuradorAgendamentoEspecial.getNome();

            this.profissionalConfigurador = new UsuarioDTO(configuradorAgendamentoEspecial.getProfissionalConfigurador());

            this.dataInicio = configuradorAgendamentoEspecial.getDataInicio();

            this.dataFim = configuradorAgendamentoEspecial.getDataFim();

            this.organizacaoConfigurador = new OrganizacaoDTO(configuradorAgendamentoEspecial.getOrganizacaoConfigurador());

            if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial.getTiposAgendamentos())) {
                this.tiposAgendamentos = new ArrayList<>();
                configuradorAgendamentoEspecial.getTiposAgendamentos().forEach(tipoAgendamento -> {
                    this.tiposAgendamentos.add(new TipoAgendamentoDTO(tipoAgendamento));
                });
            }
        }
    }

    public ConfiguradorAgendamentoEspecialDTO(ConfiguradorAgendamentoEspecial configuradorAgendamentoEspecial, List<TipoAgendamentoDTO> tiposAgendamentos) {

        if (BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial, tiposAgendamentos)) {

            this.id = configuradorAgendamentoEspecial.getId();

            this.nome = configuradorAgendamentoEspecial.getNome();

            this.profissionalConfigurador = new UsuarioDTO(configuradorAgendamentoEspecial.getProfissionalConfigurador());

            this.dataInicio = configuradorAgendamentoEspecial.getDataInicio();

            this.dataFim = configuradorAgendamentoEspecial.getDataFim();

            this.organizacaoConfigurador = new OrganizacaoDTO(configuradorAgendamentoEspecial.getOrganizacaoConfigurador());

            if (BasicFunctions.isNotEmpty(tiposAgendamentos)) {
                this.tiposAgendamentos = new ArrayList<>(tiposAgendamentos);
            }
        }
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

    public UsuarioDTO getProfissionalConfigurador() {
        return profissionalConfigurador;
    }

    public void setProfissionalConfigurador(UsuarioDTO profissionalConfigurador) {
        this.profissionalConfigurador = profissionalConfigurador;
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

    public OrganizacaoDTO getOrganizacaoConfigurador() {
        return organizacaoConfigurador;
    }

    public void setOrganizacaoConfigurador(OrganizacaoDTO organizacaoConfigurador) {
        this.organizacaoConfigurador = organizacaoConfigurador;
    }

    public List<TipoAgendamentoDTO> getTiposAgendamentos() {
        return tiposAgendamentos;
    }

    public void addTiposAgendamentos(TipoAgendamentoDTO tiposAgendamento) {
        this.tiposAgendamentos.add(tiposAgendamento);
    }
}
