package app.api.agendaFacil.business.configuradorNotificacao.DTO;

import java.time.LocalTime;

public class ConfiguradorNotificacaoDTO {

    private Long id;

    private String mensagem;

    private Long dataIntervalo;

    private LocalTime horaMinutoIntervalo;

    public ConfiguradorNotificacaoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Long getDataIntervalo() {
        return dataIntervalo;
    }

    public LocalTime getHoraMinutoIntervalo() {
        return horaMinutoIntervalo;
    }
}
