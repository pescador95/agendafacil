package app.api.agendaFacil.management.profile.service;

import app.api.agendaFacil.business.historicoPessoa.entity.HistoricoPessoa;
import app.api.agendaFacil.business.historicoPessoa.repository.HistoricoPessoaRepository;
import app.api.agendaFacil.management.profile.entity.MultiPartFormData;
import app.api.agendaFacil.management.profile.entity.Profile;
import app.api.agendaFacil.management.profile.manager.ProfileManager;
import app.api.agendaFacil.management.profile.repository.ProfileRepository;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.StringFunctions;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Transactional
public class ProfileService extends ProfileManager {

    public ProfileService() {
        super();
    }

    @Inject
    public ProfileService(SecurityContext context) {
        super(context);
    }

    public Profile findOne(Long id) {

        Profile profile = ProfileRepository.findById(id);

        if (BasicFunctions.isEmpty(profile)) {
            throw new RuntimeException("Arquivo não encontrado");
        }

        return profile;
    }

    public Response sendUpload(@NotNull MultiPartFormData file, String fileRefence, Long idHistoricoPessoa)
            throws IOException {

        responses = new Responses();
        responses.setMessages(new ArrayList<>());

        HistoricoPessoa historicoPessoa = HistoricoPessoaRepository.findById(idHistoricoPessoa);

        validaHistoricoPessoa(historicoPessoa, file);

        if (!responses.hasMessages()) {

            Profile profile = new Profile();

            String fileName = idHistoricoPessoa + "-" + file.getFile().fileName();

            profile.setOriginalName(file.getFile().fileName());

            profile.setKeyName(fileName);

            profile.setMimetype(file.getFile().contentType());

            profile.setFileSize(file.getFile().size());

            profile.setDataCriado(Contexto.dataHoraContexto());

            profile.setHistoricoPessoa(historicoPessoa);

            profile.setNomePessoa(profile.getHistoricoPessoa().getPessoa().getNome());

            profile.setFileReference(fileRefence);

            profile.persist();

            Files.copy(file.getFile().filePath(), Paths.get(directory + fileName));

            responses.setStatus(200);
            responses.setMessages("Arquivo adicionado com sucesso!");
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response removeUpload(List<Long> pListIdProfile) {

        try {

            responses = new Responses();
            responses.setMessages(new ArrayList<>());

            List<Profile> profiles;

            profiles = ProfileRepository.listByIds(pListIdProfile);
            int count = profiles.size();

            if (BasicFunctions.isNotEmpty(profiles)) {
                profiles.forEach((profile) -> {

                    try {
                        Profile.delete("id = ?1", profile.getId());
                        Files.deleteIfExists(Paths.get(directory + profile.getKeyName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });

            }
            responses.setStatus(200);
            responses.setMessages(Responses.DELETED, count);
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response count() {

        try {

            responses = new Responses();

            Long count = Profile.count();
            return Response.ok(count).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response listUploads(String sortQuery, Integer pageIndex, Integer pageSize, String strgOrder) {

        try {

            responses = new Responses();

            String queryString = "";

            String query = "id > " + "0" + " " + queryString + " " + "order by " + strgOrder + " " + sortQuery;

            PanacheQuery<Profile> profile = ProfileRepository.find(query);

            return Response.ok(profile.page(Page.of(pageIndex, pageSize)).list()).status(responses.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response get(Long id) {

        try {

            Profile profile = findOne(id);
            return Response.ok(profile).status(responses.getStatus()).build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            responses.setStatus(500);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    private void validaHistoricoPessoa(HistoricoPessoa historicoPessoa, MultiPartFormData file) {

        if (BasicFunctions.isEmpty(historicoPessoa)) {
            responses.setStatus(400);
            responses.setMessages("Por favor, verifique o Histórico da Entidade do anexo.");
        }

        if (!StringFunctions.getMimeTypes().contains(file.getFile().contentType())) {
            responses.setMessages(
                    "Tipo de arquivo não suportado. Aceito somente arquivos nos formatos: ppt, pptx, csv, doc, docx, txt, pdf, xlsx, xml, xls, jpg, jpeg, png e zip.");
        }

        if (file.getFile().size() > 1024 * 1024 * 4) {
            responses.setMessages("Arquivo muito grande.");
        }

        Profile profileCheck = ProfileRepository.findByHistoricoPessoa(historicoPessoa);
        if (BasicFunctions.isEmpty(profileCheck) || !profileCheck.getOriginalName().equals(file.getFile().fileName())) {
            responses.setStatus(400);
            responses.setMessages("Já existe um arquivo com o mesmo nome. Verifique!");
        }
    }
}