package app.api.agendaFacil.business.usuario.validator;

import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.tipoAgendamento.entity.TipoAgendamento;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UsuarioValidator {

    final UsuarioValidator usuarioValidator;

    public UsuarioValidator(UsuarioValidator usuarioValidator) {
        this.usuarioValidator = usuarioValidator;
    }

    public Boolean checkProfissional(Usuario profissional, Boolean comPreferencia) {

        if (BasicFunctions.isNull(profissional) && Boolean.TRUE.equals(comPreferencia)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public void validaUsuario(Usuario pUsuario, Responses responses, List<Organizacao> organizacoes, List<TipoAgendamento> tiposAgendamentos) {

        if (BasicFunctions.isEmpty(pUsuario)) {
            responses.setStatus(400);
            responses.setMessages("Por favor, verifique as informações!");
            return;
        }

        if (BasicFunctions.isNotEmpty(pUsuario) && BasicFunctions.isEmpty(pUsuario.getLogin())) {
            responses.setStatus(400);
            responses.setMessages("Por favor, verifique o login!");
            return;
        }

        if (BasicFunctions.isEmpty(pUsuario.getLogin(), pUsuario.getPassword(), pUsuario.getPrivilegio(), pUsuario.getOrganizacoes(), organizacoes, tiposAgendamentos)) {
            responses.setStatus(400);
            responses.setMessages("Informe os dados para atualizar o Usuário.");
        }
    }

}
