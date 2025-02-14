package app.api.agendaFacil.management.recoveryPassword.service;

import app.api.agendaFacil.business.organizacao.loader.OrganizacaoLoader;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.business.pessoa.loader.PessoaLoader;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.api.agendaFacil.business.usuario.loader.UsuarioLoader;
import app.api.agendaFacil.management.recoveryPassword.manager.RecoveryPasswordManager;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.StringFunctions;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.ArrayList;

@RequestScoped
@Transactional
public class RecoveryPasswordService extends RecoveryPasswordManager {

    private Usuario usuario;
    private Entidade pessoa;

    public RecoveryPasswordService() {
        super();
    }

    @Inject
    public RecoveryPasswordService(SecurityContext context, Mailer mailer, UsuarioLoader usuarioLoader, PessoaLoader pessoaLoader, OrganizacaoLoader organizacaoLoader) {
        super(context, mailer, usuarioLoader, pessoaLoader, organizacaoLoader);
    }

    public Response sendEmail(String login) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            loadByLogin(login);

            if (usuarioEncontrado()) {

                String token = StringFunctions.createToken(20);
                String recoveryUrl = recoveryUrl(token);

                usuario.setDataToken(Contexto.dataHoraContexto(usuario.getOrganizacaoDefaultId()).plusMinutes(10));
                usuario.setToken(token);
                usuario.setAlterarSenha(Boolean.TRUE);
                usuario.persist();

                mailer.send(
                        Mail.withText(pessoa.getEmail(), mailSubject(), mailText(recoveryUrl)));

                responses.setData(usuario);
                responses.setMessages("Enviado uma nova senha para o email informado: " + pessoa.getEmail());
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response updatePassword(Usuario pUsuario) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            Usuario userUpdating;

            userUpdating = usuarioLoader.findByToken(pUsuario);

            if (BasicFunctions.isNull(userUpdating)) {
                responses.setMessages("Usuário não encontrado ou token inexistente.");
                responses.setStatus(400);
            }

            if (canChangePassword(userUpdating)) {

                userUpdating.setNewPassword(pUsuario.getPassword());
                userUpdating.persist();

                responses.setData(new UsuarioDTO(userUpdating));
                responses.setMessages("Senha atualizada com sucesso.");
                responses.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response returnCryptPassword(String password) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            String cryptoPassword = BasicFunctions.setCryptedPassword(password);
            responses.setMessages(new ArrayList<>());
            responses.setMessages((password));
            responses.setMessages(cryptoPassword);
            responses.setStatus(200);
            responses.setOk(true);
        } catch (Exception e) {
            e.printStackTrace();
            responses = new Responses(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Boolean canChangePassword(Usuario userChecking) {

        if (BasicFunctions.isNotEmpty(userChecking) && userChecking.canChangePassword()) {
            return Boolean.TRUE;
        }
        responses.setMessages("O prazo de 10 minutos do Token expirou.");
        responses.setStatus(400);
        return Boolean.FALSE;
    }

    public Boolean usuarioEncontrado() {

        if (BasicFunctions.isNotEmpty(usuario) && usuario.getAtivo() && BasicFunctions.isNotEmpty(pessoa)) {
            return Boolean.TRUE;
        }
        responses.setMessages("Não foi possível localizar um cadastro com o email informado.");
        responses.setStatus(400);
        return Boolean.FALSE;
    }


    public void loadByLogin(String login) {
        usuario = usuarioLoader.findByLoginEmail(login);
        if (BasicFunctions.isNotNull(usuario)) {
            pessoa = pessoaLoader.findByUsuario(usuario);
        }
    }

    public String mailSubject() {
        return "Agenda Fácil - Recuperação de Senha";
    }

    public String mailText(String recoveryUrl) {
        return "Olá, " + pessoa.getNome() + "!\n"
                + "Acesse o link a seguir para redefinir a sua senha: \n" + recoveryUrl;
    }

    public String recoveryUrl(String token) {
        return Contexto.getAgendaFacilUrl(getTenant()) + "recoverPassword?token=" + token;
    }
}
