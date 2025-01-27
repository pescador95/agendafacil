package app.core.liquibase.service;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;
import app.core.application.DTO.CopyFilesDTO;
import app.core.application.DTO.Responses;
import app.core.application.tenant.Tenant;
import app.core.application.tenant.TenantRepository;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.Info;
import app.core.liquibase.manager.LiquibaseManager;
import io.quarkus.liquibase.LiquibaseFactory;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import static app.api.agendaFacil.management.database.postgres.DatabaseConfig.executeQuery;
import static app.api.agendaFacil.management.database.postgres.DatabaseConfig.executeUpdate;
import static app.core.helpers.utils.BasicFunctions.log;
import static app.core.helpers.utils.Info.*;

@RequestScoped
public class LiquibaseService extends LiquibaseManager {

    public LiquibaseService() {
        super();
    }

    @Inject
    public LiquibaseService(LiquibaseFactory liquibaseFactory) {
        super(liquibaseFactory);
    }

    private static void copyFiles(@NotNull CopyFilesDTO copyFilesDTO) throws IOException {

        Path sourceDir = copyFilesDTO.getSourceDir();
        Path targetDir = copyFilesDTO.getTargetDir();
        String author = copyFilesDTO.getAuthor();
        ContratoDTO contrato = copyFilesDTO.getContrato();
        String tenant = copyFilesDTO.getTenant();

        Integer numeromaximosessoes;
        Long tipoContrato;
        LocalDate dataContrato, dataTerminoContrato;
        String finalNumeromaximosessoes, finalDataContrato, finalDataTerminoContrato, finalTipoContrato;

        if (BasicFunctions.isNotEmpty(contrato)) {

            numeromaximosessoes = contrato.getNumeroMaximoSessoes();
            tipoContrato = contrato.getTipoContrato();
            dataContrato = contrato.getDataContrato();
            dataTerminoContrato = contrato.getDataTerminoContrato();

            if (BasicFunctions.isEmpty(dataContrato)) {
                dataContrato = Contexto.dataContexto();
            }
            if (BasicFunctions.isEmpty(dataTerminoContrato)) {
                dataTerminoContrato = Contexto.dataContexto().plusYears(1);
            }

            if (BasicFunctions.isEmpty(numeromaximosessoes)) {
                numeromaximosessoes = 5;
            }

            if (BasicFunctions.isEmpty(tipoContrato)) {
                tipoContrato = 1L;
            }

            finalDataContrato = dataContrato.toString();
            finalDataTerminoContrato = dataTerminoContrato.toString();
            finalNumeromaximosessoes = numeromaximosessoes.toString();
            finalTipoContrato = tipoContrato.toString();

            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {

                        try {

                            String fileName = file.getFileName().toString().replace("template", tenant);
                            Path targetFile = targetDir.resolve(fileName);

                            if (Files.exists(targetFile)) {
                                log("Arquivo já existente! " + targetFile, getTraceMethods());
                            }
                            if (!Files.exists(targetFile)) {
                                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);

                                replaceInFile(targetFile, "template", tenant, getTraceMethods());
                                replaceInFile(targetFile, "authortemplate", author, getTraceMethods());

                                if (BasicFunctions.isNotEmpty(contrato, finalDataContrato)) {
                                    replaceInFile(targetFile, "valuedatacontrato", finalDataContrato, getTraceMethods());
                                }
                                if (BasicFunctions.isNotEmpty(contrato, finalDataTerminoContrato)) {
                                    replaceInFile(targetFile, "valuedataterminocontrato", finalDataTerminoContrato, getTraceMethods());
                                }
                                if (BasicFunctions.isNotEmpty(contrato, finalNumeromaximosessoes)) {
                                    replaceInFile(targetFile, "valuenumeromaximosessoes", finalNumeromaximosessoes, getTraceMethods());
                                }
                                if (BasicFunctions.isNotEmpty(contrato, finalTipoContrato)) {
                                    replaceInFile(targetFile, "valuetipocontrato", finalTipoContrato, getTraceMethods());
                                }
                                log("Arquivo copiado com sucesso: " + targetFile, getTraceMethods());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.err.println("Erro ao copiar o arquivo: " + e.getMessage());
                        }
                    });
        }
    }

    private static void replaceInFile(Path file, String search, String replace, Boolean traceMethods) throws IOException {
        String content = new String(Files.readAllBytes(file));
        content = content.replaceAll("\\b" + search + "\\b", replace);
        Files.write(file, content.getBytes());
        log("Arquivo alterado com sucesso: " + file, traceMethods);
    }


    public Response copyNewMigrationsAndRun() throws LiquibaseException {

        try {

            List<Tenant> tenants = TenantRepository.listAll();

            responses = new Responses(200, "Migrations copiadas e executadas com sucesso");

            tenants.forEach(tenant -> {

                try {

                    copyFiles(copyByOrigin(null, tenant.getTenant()));
                } catch (IOException e) {
                    e.printStackTrace();
                    responses = new Responses(500, e.getMessage());
                    log("Erro ao copiar migrations: " + e.getMessage(), getTraceMethods());
                    throw new RuntimeException(e);
                }
            });
            executeMigration(false, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public Response copyCreateAndExecuteMigrationsByContrato(ContratoDTO contrato) {

        try {

            responses = new Responses(200, Info.mensagemContrato(contrato));

            CopyFilesDTO copyFilesDTO = copyByOrigin(contrato, contrato.getTenant());

            copyFiles(copyFilesDTO);

            createSchema(contrato.getTenant());

            executeMigration(false, true, true);

        } catch (IOException | LiquibaseException e) {
            e.printStackTrace();
            responses = new Responses(500, e.getMessage());
            log("Erro ao criar ao copiar e executar migrations: " + e.getMessage(), getTraceMethods());
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public CopyFilesDTO copyByOrigin(ContratoDTO contrato, String tenant) {

        File templateDir = new File(templateDirPath());

        File migrationDir = new File(migrationDirPath(tenant));

        if (migrationDir.exists()) {
            log("Migração já existe.", getTraceMethods());
        }
        if (!migrationDir.exists()) {

            migrationDir.mkdirs();

            log("Migração criada com sucesso.", getTraceMethods());
        }
        return new CopyFilesDTO(templateDir.toPath(), migrationDir.toPath(), migrationAuthor(), getTraceMethods(), contrato, tenant);
    }

    public Response executeMigration(Boolean dropAll, Boolean validate, Boolean update) throws LiquibaseException {

        try (Liquibase liquibase = liquibaseFactory.createLiquibase()) {


            if (dropAll) {
                liquibase.dropAll();
            }
            if (validate) {
                liquibase.validate();
                log("Migrations validadas.", getTraceMethods());
            }
            if (update) {
                liquibase.update(liquibaseFactory.createContexts(), liquibaseFactory.createLabels());
                log("Migrations executadas.", getTraceMethods());
            }
            responses = new Responses(200, "Migrations executadas com sucesso");
        } catch (LiquibaseException e) {
            e.printStackTrace();
            responses.setStatus(500);
            throw new RuntimeException(e);
        }
        return Response.ok(responses).status(responses.getStatus()).build();
    }

    public void createSchema(String schemaName) {

        try {

            ResultSet result = executeQuery("SELECT COUNT(*) FROM pg_catalog.pg_namespace WHERE nspname = " + "'" + schemaName + "'");

            if (result.next()) {
                if (result.getInt(1) > 0) {
                    log("Schema já existe.", getTraceMethods());
                    return;
                }
            }

            executeUpdate("CREATE SCHEMA " + schemaName);
            executeUpdate("GRANT ALL PRIVILEGES ON SCHEMA " + schemaName + " TO " + databaseConfig.getUsername());

            log("Schema criado com sucesso.", getTraceMethods());
        } catch (Exception e) {
            log("Erro ao criar schema: " + e.getMessage(), getTraceMethods());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
