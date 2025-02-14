package app.api.agendaFacil.business.organizacao.entity;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.organizacao.DTO.OrganizacaoDTO;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.entity.Entidade;
import app.api.agendaFacil.management.timeZone.entity.TimeZone;
import app.api.agendaFacil.management.timeZone.repository.TimeZoneRepository;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import app.core.helpers.utils.StringFunctions;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

@Entity
public class Organizacao extends Entidade {

    @Column()
    @SequenceGenerator(name = "organizacaoIdSequence", sequenceName = "organizacao_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn(name = "id")
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "timeZoneId")
    private TimeZone timeZone;

    @Column()
    private String cnpj;

    @Column()
    private Boolean ativo;

    public Organizacao() {
        super();
    }

    public Organizacao(SecurityContext context) {
        super(context);
    }

    public Organizacao(OrganizacaoDTO entity) {
        super();
        if (BasicFunctions.isNotEmpty(entity)) {

            this.id = entity.getId();
            this.ativo = entity.getAtivo();
            this.timeZone = entity.getTimeZone();
            this.setNome(entity.getNome());
            this.setCnpj(entity.getCnpj());
            this.setTelefone(entity.getTelefone());
            this.setCelular(entity.getCelular());
            this.setEmail(entity.getEmail());
            this.setEndereco(new Endereco(entity.getEndereco()));
            this.setWhatsappId(entity.getWhatsappId());
            this.setTelegramId(entity.getTelegramId());
            this.setRecebeNotifacaoTelegram(entity.getRecebeNotificacaoTelegram());
            this.setRecebeNotifacaoWhatsapp(entity.getRecebeNotificacaoWhatsapp());
            this.setTenant(entity.getTenant());
        }
    }

    public Organizacao(Long id) {
        super();
        this.id = id;
    }

    public Organizacao(Long id, String cnpj, TimeZone timeZone) {
        super();
        this.id = id;
        this.cnpj = StringFunctions.extractNumericDigits(cnpj);
        this.timeZone = timeZone;
    }

    public Organizacao(EntidadeDTO entity, SecurityContext context, String tenant) {
        super(context);

        if (BasicFunctions.isNotEmpty(entity)) {

            if (BasicFunctions.isNotEmpty(entity.getId())) {
                this.setId(entity.getId());
            }
            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                this.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(entity.getCnpj())) {
                this.setCnpj(entity.getCnpj());
            }
            if (BasicFunctions.isNotEmpty(entity.getTelefone())) {
                this.setTelefone(entity.getTelefone());
            }
            if (BasicFunctions.isNotEmpty(entity.getCelular())) {
                this.setCelular(entity.getCelular());
            }
            if (BasicFunctions.isNotEmpty(entity.getEmail())) {
                this.setEmail(entity.getEmail());
            }

            if (BasicFunctions.isNotEmpty(entity.getTimeZone())
                    && BasicFunctions.isValid(entity.getTimeZone().getId())) {
                this.timeZone = TimeZoneRepository.findById(entity.getTimeZone().getId());
            }
            this.setEndereco(entity.getEndereco());
            this.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoTelegram());
            this.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoWhatsapp());
            this.setWhatsappId(entity.getWhatsappId());
            this.setTelegramId(entity.getTelegramId());
        }
        this.setTenant(tenant);

        this.createAudit(context);
        this.setAtivo(Boolean.TRUE);

    }

    public Organizacao(Organizacao entity, SecurityContext context) {
        super(context);

        if (BasicFunctions.isNotEmpty(entity)) {

            if (BasicFunctions.isNotEmpty(entity.getId())) {
                this.setId(entity.getId());
            }

            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                this.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(entity.getCnpj())) {
                this.setCnpj(entity.getCnpj());
            }
            if (BasicFunctions.isNotEmpty(entity.getTelefone())) {
                this.setTelefone(entity.getTelefone());
            }
            if (BasicFunctions.isNotEmpty(entity.getCelular())) {
                this.setCelular(entity.getCelular());
            }
            if (BasicFunctions.isNotEmpty(entity.getEmail())) {
                this.setEmail(entity.getEmail());
            }
            this.setEndereco(entity.getEndereco());
            this.setWhatsappId(entity.getWhatsappId());
            this.setTelegramId(entity.getTelegramId());
            this.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoTelegram());
            this.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoWhatsapp());
            this.setTenant(entity.getTenant());

            if (BasicFunctions.isNotEmpty(entity.getTimeZone())
                    && BasicFunctions.isValid(entity.getTimeZone().getId())) {
                this.timeZone = TimeZoneRepository.findById(entity.getTimeZone().getId());
            }
        }

        this.createAudit(context);
        this.setAtivo(Boolean.TRUE);

    }

    public Organizacao organizacao(Organizacao entityOld, EntidadeDTO entity, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(entity)) {

            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                entityOld.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(entity.getCnpj())) {
                entityOld.setCnpj(entity.getCnpj());
            }
            if (BasicFunctions.isNotEmpty(entity.getTelefone())) {
                entityOld.setTelefone(entity.getTelefone());
            }
            if (BasicFunctions.isNotEmpty(entity.getCelular())) {
                entityOld.setCelular(entity.getCelular());
            }
            if (BasicFunctions.isNotEmpty(entity.getEmail())) {
                entityOld.setEmail(entity.getEmail());
            }

            if (BasicFunctions.isNotEmpty(entity.getTimeZone())
                    && BasicFunctions.isValid(entity.getTimeZone().getId())) {
                entityOld.timeZone = TimeZoneRepository.findById(entity.getTimeZone().getId());
            }
            if (BasicFunctions.isNotEmpty(entity.getEndereco())) {
                entityOld.setEndereco(entity.getEndereco());
            }
            if (BasicFunctions.isNotEmpty(entity.getWhatsappId())) {
                entityOld.setWhatsappId(entity.getWhatsappId());
            }
            if (BasicFunctions.isNotEmpty(entity.getTelegramId())) {
                entityOld.setTelegramId(entity.getTelegramId());
            }
            if (BasicFunctions.isNotEmpty(entity.getRecebeNotifacaoTelegram())) {
                entityOld.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoTelegram());
            }
            if (BasicFunctions.isNotEmpty(entity.getRecebeNotifacaoWhatsapp())) {
                entityOld.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoWhatsapp());
            }
        }

        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Organizacao organizacao(Organizacao entityOld, Organizacao pOrganizacao, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(pOrganizacao)) {

            if (BasicFunctions.isNotEmpty(pOrganizacao.getNome())) {
                entityOld.setNome(pOrganizacao.getNome());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getCnpj())) {
                entityOld.setCnpj(pOrganizacao.getCnpj());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getTelefone())) {
                entityOld.setTelefone(pOrganizacao.getTelefone());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getCelular())) {
                entityOld.setCelular(pOrganizacao.getCelular());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getEmail())) {
                entityOld.setEmail(pOrganizacao.getEmail());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getRecebeNotifacaoTelegram())) {
                entityOld.setRecebeNotifacaoTelegram(pOrganizacao.getRecebeNotifacaoTelegram());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getRecebeNotifacaoWhatsapp())) {
                entityOld.setRecebeNotifacaoWhatsapp(pOrganizacao.getRecebeNotifacaoWhatsapp());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getWhatsappId())) {
                entityOld.setWhatsappId(pOrganizacao.getWhatsappId());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getTelegramId())) {
                entityOld.setTelegramId(pOrganizacao.getTelegramId());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getTimeZone())
                    && BasicFunctions.isValid(pOrganizacao.getTimeZone().getId())) {
                entityOld.timeZone = TimeZoneRepository.findById(pOrganizacao.getTimeZone().getId());
            }
            if (BasicFunctions.isNotEmpty(pOrganizacao.getTenant())) {
                entityOld.setTenant(pOrganizacao.getTenant());
            }
        }

        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Organizacao deletarOrganizacao(Organizacao entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public Organizacao reativarOrganizacao(Organizacao entity, SecurityContext context) {

        entity.setAtivo(Boolean.TRUE);
        entity.restoreAudit(context);
        return entity;
    }

    public Boolean cnpjJaUtilizado(Organizacao pOrganizacao) {
        List<Organizacao> organizacoesExistentes = Organizacao.list(
                "cnpj = ?1 and ativo = true",
                pOrganizacao.getCnpj());

        return BasicFunctions.isNotEmpty(organizacoesExistentes) && organizacoesExistentes.stream()
                .anyMatch(organizacao -> !organizacao.getId().equals(pOrganizacao.getId()));
    }

    public TimeZone getTimeZone() {
        if (BasicFunctions.isNotEmpty(this.timeZone)) {
            return this.timeZone;
        }
        return new TimeZone(Contexto.defaultZoneIdToString(), Contexto.defaultTimeZoneOffset());
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getTimeZoneOffset() {
        if (BasicFunctions.isNotEmpty(this.timeZone)) {
            return String.valueOf(this.timeZone.getTimeZoneOffset());
        }
        return Contexto.defaultTimeZoneOffset();
    }

    public String getZoneId() {
        if (BasicFunctions.isNotEmpty(this.timeZone)) {
            return this.timeZone.getTimeZoneId();
        }
        return Contexto.defaultZoneIdToString();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = StringFunctions.extractNumericDigits(cnpj);
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
