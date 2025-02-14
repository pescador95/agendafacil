package app.api.agendaFacil.business.endereco.loader;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.endereco.repository.EnderecoRepository;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EnderecoLoader {

    final EnderecoLoader enderecoLoader;
    final EnderecoRepository enderecoRepository;

    public EnderecoLoader(EnderecoLoader enderecoLoader, EnderecoRepository enderecoRepository) {
        this.enderecoLoader = enderecoLoader;
        this.enderecoRepository = enderecoRepository;
    }

    public static PanacheQuery<Endereco> find(String query) {
        return EnderecoRepository.find(query);
    }

    public Endereco loadEnderecoById(Endereco pEndereco) {

        if (BasicFunctions.isNotEmpty(pEndereco, pEndereco.getId())) {
            return EnderecoRepository.findById(pEndereco.getId());
        }
        return null;
    }

    public Endereco findById(Long pId) {
        if (BasicFunctions.isNotEmpty(pId)) {
            return EnderecoRepository.findById(pId);
        }
        return null;

    }

    public List<Endereco> listByIds(List<Long> pListEndereco, Boolean ativo) {
        return EnderecoRepository.listByIds(pListEndereco, ativo);
    }

    public List<Endereco> list(QueryFilter queryFilter) {
        return enderecoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return enderecoRepository.count(queryFilter);
    }
}