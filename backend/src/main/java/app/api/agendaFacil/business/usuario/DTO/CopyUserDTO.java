package app.api.agendaFacil.business.usuario.DTO;

import app.core.helpers.utils.BasicFunctions;

public class CopyUserDTO {

    private UsuarioDTO usuarioOrigem;

    private UsuarioDTO usuarioDestino;

    private Boolean privilegios = false;

    private Boolean tipoAgendamentos = false;

    private Boolean organizacaoDefault = false;

    private Boolean organizacoes = false;

    private Boolean configuradorAgendamento = false;

    private Boolean configuradorAgendamentoEspecial = false;


    public CopyUserDTO() {

    }

    public CopyUserDTO(UsuarioDTO usuarioOrigem, UsuarioDTO usuarioDestino) {

        this.usuarioOrigem = usuarioOrigem;
        this.usuarioDestino = usuarioDestino;
    }

    public CopyUserDTO(UsuarioDTO usuarioOrigem, UsuarioDTO usuarioDestino, Boolean privilegios, Boolean tipoAgendamentos, Boolean organizacaoDefault, Boolean organizacoes, Boolean configuradorAgendamento, Boolean configuradorAgendamentoEspecial) {

        this.usuarioOrigem = usuarioOrigem;
        this.usuarioDestino = usuarioDestino;
        this.privilegios = privilegios;
        this.tipoAgendamentos = tipoAgendamentos;
        this.organizacaoDefault = organizacaoDefault;
        this.organizacoes = organizacoes;
        this.configuradorAgendamento = configuradorAgendamento;
        this.configuradorAgendamentoEspecial = configuradorAgendamentoEspecial;
    }

    public UsuarioDTO getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(UsuarioDTO usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public UsuarioDTO getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(UsuarioDTO usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Boolean getPrivilegios() {
        return BasicFunctions.isNotEmpty(privilegios) && privilegios;
    }

    public void setPrivilegios(Boolean privilegios) {
        this.privilegios = privilegios;
    }

    public Boolean copyTipoAgendamentos() {
        return BasicFunctions.isNotEmpty(tipoAgendamentos) && tipoAgendamentos;
    }

    public Boolean copyOrganizacaoDefault() {
        return BasicFunctions.isNotEmpty(organizacaoDefault) && organizacaoDefault;
    }

    public Boolean copyOrganizacoes() {
        return BasicFunctions.isNotEmpty(organizacoes) && organizacoes;
    }

    public Boolean copyConfiguradorAgendamento() {
        return BasicFunctions.isNotEmpty(configuradorAgendamento) && configuradorAgendamento;
    }

    public Boolean copyConfiguradorAgendamentoEspecial() {
        return BasicFunctions.isNotEmpty(configuradorAgendamentoEspecial) && configuradorAgendamentoEspecial;
    }

    public Boolean getTipoAgendamentos() {
        return tipoAgendamentos;
    }

    public void setTipoAgendamentos(Boolean tipoAgendamentos) {
        this.tipoAgendamentos = tipoAgendamentos;
    }

    public Boolean getOrganizacaoDefault() {
        return organizacaoDefault;
    }

    public void setOrganizacaoDefault(Boolean organizacaoDefault) {
        this.organizacaoDefault = organizacaoDefault;
    }

    public Boolean getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(Boolean organizacoes) {
        this.organizacoes = organizacoes;
    }

    public Boolean getConfiguradorAgendamento() {
        return configuradorAgendamento;
    }

    public void setConfiguradorAgendamento(Boolean configuradorAgendamento) {
        this.configuradorAgendamento = configuradorAgendamento;
    }

    public Boolean getConfiguradorAgendamentoEspecial() {
        return configuradorAgendamentoEspecial;
    }

    public void setConfiguradorAgendamentoEspecial(Boolean configuradorAgendamentoEspecial) {
        this.configuradorAgendamentoEspecial = configuradorAgendamentoEspecial;
    }
}
