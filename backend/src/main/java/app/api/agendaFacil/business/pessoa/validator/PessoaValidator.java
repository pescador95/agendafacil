package app.api.agendaFacil.business.pessoa.validator;

import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.pessoa.entity.Pessoa;
import app.api.agendaFacil.business.pessoa.repository.PessoaRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@ApplicationScoped
public class PessoaValidator {

    final PessoaValidator pessoaValidator;

    public PessoaValidator(PessoaValidator pessoaValidator) {
        this.pessoaValidator = pessoaValidator;
    }

    public void validaDadosPessoa(Pessoa pPessoa, SecurityContext context, Responses responses) {

        Usuario usuarioAuth = Contexto.getContextUser(context);

        Organizacao organizacao = OrganizacaoRepository.findById(usuarioAuth.getOrganizacaoDefaultId());

        if (BasicFunctions.isNotEmpty(pPessoa)) {
            if (BasicFunctions.isEmpty(pPessoa.getDataNascimento()) || BasicFunctions.isNotEmpty(pPessoa.getDataNascimento()) && !Contexto.dataValida(pPessoa.getDataNascimento(), organizacao)) {
                responses.setStatus(400);
                responses.setMessages("Data de nascimento inválida!");
            }
            if (cpfJaUtilizado(pPessoa)) {
                responses.setStatus(409);
                responses.setMessages("Já existe uma pessoa cadastrada com o CPF informado!");
            }
        }
    }

    public Boolean cpfJaUtilizado(Pessoa entity) {
        if (BasicFunctions.isNotEmpty(entity, entity.getCpf())) {
            List<Pessoa> pessoas = PessoaRepository.listByCpf(entity.getCpf(), entity.getAtivo());
            return BasicFunctions.isNotEmpty(pessoas)
                    && pessoas.stream().anyMatch(pessoa -> !pessoa.getId().equals(entity.getId()) && pessoa.getAtivo().equals(true));
        }
        return Boolean.FALSE;
    }

}
