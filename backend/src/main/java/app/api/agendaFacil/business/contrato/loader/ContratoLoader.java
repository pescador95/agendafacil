package app.api.agendaFacil.business.contrato.loader;

import app.api.agendaFacil.business.contrato.entity.Contrato;
import app.api.agendaFacil.business.contrato.entity.TipoContrato;
import app.api.agendaFacil.business.contrato.repository.ContratoRepository;
import app.api.agendaFacil.business.organizacao.entity.Organizacao;
import app.api.agendaFacil.business.organizacao.repository.OrganizacaoRepository;
import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.QueryFilter;
import app.core.helpers.utils.BasicFunctions;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ContratoLoader {

    final ContratoLoader contratoLoader;
    final ContratoRepository contratoRepository;

    public ContratoLoader(ContratoLoader contratoLoader, ContratoRepository contratoRepository) {
        this.contratoLoader = contratoLoader;
        this.contratoRepository = contratoRepository;
    }

    public static TipoContrato getTipoContratoByUsuarioOrganizacaoDefault(Usuario pUsuario) {


        Organizacao organizacaoDefault = OrganizacaoRepository.findById(pUsuario.getOrganizacaoDefaultId());

        TipoContrato tipoContrato = null;

        Contrato contrato = ContratoRepository.findByOrganizacao(organizacaoDefault);

        if (BasicFunctions.isNotEmpty(contrato)) {
            tipoContrato = contrato.getTipoContrato();
        }
        return tipoContrato;
    }

    public Contrato loadByOrganizacao(Organizacao organizacao) {
        if (BasicFunctions.isNotEmpty(organizacao)) {
            return ContratoRepository.findByOrganizacao(organizacao);
        }
        return null;
    }

    public List<Contrato> list(QueryFilter queryFilter) {
        return contratoRepository.list(queryFilter);
    }

    public Integer count(QueryFilter queryFilter) {
        return contratoRepository.count(queryFilter);
    }
}