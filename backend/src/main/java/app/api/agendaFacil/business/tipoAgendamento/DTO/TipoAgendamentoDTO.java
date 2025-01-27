package app.api.agendaFacil.business.tipoAgendamento.DTO;

import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.core.helpers.utils.BasicFunctions;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import java.util.ArrayList;
import java.util.List;

public class TipoAgendamentoDTO {

    private Long id;

    private String tipoAgendamento;

    @JsonIncludeProperties({"id", "nome", "celular"})
    private List<OrganizacaoDTO> organizacoes = new ArrayList<>();

    public TipoAgendamentoDTO() {
    }

    public TipoAgendamentoDTO(Long id, String tipoAgendamento) {
        this.id = id;
        this.tipoAgendamento = tipoAgendamento;
    }


    public TipoAgendamentoDTO(Long id) {
        this.id = id;
    }

    public TipoAgendamentoDTO(TipoAgendamento tipoAgendamento) {

        if (BasicFunctions.isNotEmpty(tipoAgendamento)) {

            this.id = tipoAgendamento.getId();
            this.tipoAgendamento = tipoAgendamento.getTipoAgendamento();

            if (BasicFunctions.isNotEmpty(tipoAgendamento.getOrganizacoes())) {
                this.organizacoes = new ArrayList<>();
                tipoAgendamento.getOrganizacoes().forEach(organizacao -> {
                    this.organizacoes.add(new OrganizacaoDTO(organizacao));
                });
            }
        }
    }


    public static List<TipoAgendamentoDTO> makeListTipoAgendamentoDTO(List<TipoAgendamento> listaTipoAgendamento) {

        List<TipoAgendamentoDTO> listDTOS = new ArrayList<>();

        listaTipoAgendamento.forEach(tipoAgendamento -> {

            TipoAgendamentoDTO tipoAgendamentoDTO = new TipoAgendamentoDTO(tipoAgendamento);

            listDTOS.add(tipoAgendamentoDTO);
        });
        return listDTOS;
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

    public List<OrganizacaoDTO> getOrganizacoes() {
        return organizacoes;
    }

    public void setOrganizacoes(List<OrganizacaoDTO> organizacoes) {
        this.organizacoes = organizacoes;
    }

    public void addOrganizacao(OrganizacaoDTO organizacaoDTO) {
        this.organizacoes.add(organizacaoDTO);
    }
}
