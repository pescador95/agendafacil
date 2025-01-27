package app.core.application.DTO;

import app.api.agendaFacil.business.contrato.DTO.ContratoDTO;

import java.nio.file.Path;

public class CopyFilesDTO {

    Path sourceDir;
    Path targetDir;
    String author;
    Boolean traceMethods;
    ContratoDTO contrato;
    String tenant;

    public CopyFilesDTO() {

    }

    public CopyFilesDTO(Path sourceDir, Path targetDir, String author, Boolean traceMethods, ContratoDTO contrato, String tenant) {
        this.sourceDir = sourceDir;
        this.targetDir = targetDir;
        this.author = author;
        this.traceMethods = traceMethods;
        this.contrato = contrato;
        this.tenant = tenant;
    }

    public Path getSourceDir() {
        return sourceDir;
    }

    public Path getTargetDir() {
        return targetDir;
    }

    public String getAuthor() {
        return author;
    }

    public Boolean getTraceMethods() {
        return traceMethods;
    }

    public ContratoDTO getContrato() {
        return contrato;
    }

    public void setContrato(ContratoDTO contrato) {
        this.contrato = contrato;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
