package app.api.agendaFacil.business.pessoa.entity;

import app.api.agendaFacil.business.endereco.entity.Endereco;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.application.entity.Audit;
import app.core.helpers.utils.BasicFunctions;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;


@MappedSuperclass
public abstract class
Entidade extends Audit {

    @SequenceGenerator(name = "entidadeIdSequence", sequenceName = "entidade_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column()
    private Boolean ativo;

    @Column()
    private String nome;

    @Column()
    private String telefone;

    @Column()
    private String celular;

    @Column()
    private String email;

    @ManyToOne
    @JoinColumn(name = "enderecoId")
    private Endereco endereco;

    @Column()
    private Long telegramId;

    @Column()
    private Long whatsappId;

    @Column()
    private Boolean recebeNotifacaoWhatsapp = false;

    @Column()
    private Boolean recebeNotifacaoTelegram = false;

    @Column()
    private String tenant;


    protected Entidade() {
        super();
    }

    protected Entidade(String tenant) {
        super();
        this.setTenant(tenant);
    }

    protected Entidade(SecurityContext context) {
        super(context);
    }

    protected Entidade(EntidadeDTO entidade, SecurityContext context) {
        super(context);
        this.id = entidade.getId();
        this.ativo = Boolean.TRUE;
        this.nome = entidade.getNome();
        this.telefone = entidade.getTelefone();
        this.celular = entidade.getCelular();
        this.email = entidade.getEmail();
        this.endereco = entidade.getEndereco();
        this.telegramId = entidade.getTelegramId();
        this.whatsappId = entidade.getWhatsappId();
        this.recebeNotifacaoTelegram = entidade.getRecebeNotifacaoTelegram();
        this.recebeNotifacaoWhatsapp = entidade.getRecebeNotifacaoWhatsapp();
        this.tenant = entidade.getTenant();
    }

    protected Entidade(UsuarioDTO entidade) {
        super();
        this.id = entidade.getId();
        this.ativo = Boolean.TRUE;
        this.nome = entidade.loadPessoaNome();
        this.telefone = entidade.loadPessoaTelefone();
        this.celular = entidade.loadPessoaCelular();
        this.email = entidade.loadPessoaEmail();
        this.endereco = entidade.loadPessoaEndereco();
        this.telegramId = entidade.getTelegramId();
        this.whatsappId = entidade.getWhatsappId();
    }

    protected Entidade(PessoaDTO pessoaDTO) {

        this.id = pessoaDTO.getId();
        this.ativo = pessoaDTO.getAtivo();
        this.setNome(pessoaDTO.getNome());
        this.setTelefone(pessoaDTO.getTelefone());
        this.setCelular(pessoaDTO.getCelular());
        this.setEmail(pessoaDTO.getEmail());
        this.setWhatsappId(pessoaDTO.getWhatsappId());
        this.setTelegramId(pessoaDTO.getTelegramId());
        this.recebeNotifacaoTelegram = pessoaDTO.getRecebeNotifacaoTelegram();
        this.recebeNotifacaoWhatsapp = pessoaDTO.getRecebeNotifacaoWhatsapp();
        this.setTenant(pessoaDTO.getTenant());

        if (pessoaDTO.getEndereco() != null) {
            this.setEndereco(pessoaDTO.getEndereco());
        }
    }

    protected Entidade(Entidade entity, SecurityContext context) {
        super(context);
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            this.setNome(entity.getNome());
        }

        if (BasicFunctions.isValid(entity.getWhatsappId())) {
            this.setWhatsappId(entity.getWhatsappId());
        }
        if (BasicFunctions.isValid(entity.getTelegramId())) {
            this.setTelegramId(entity.getTelegramId());
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
        if (BasicFunctions.isNotEmpty(entity.getEndereco())) {
            this.setEndereco(entity.getEndereco());
        }
        this.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoWhatsapp());
        this.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoTelegram());
        this.setTenant(entity.getTenant());
        this.setAtivo(Boolean.TRUE);
        this.createAudit(context);
    }

    public Entidade(Long id, Boolean ativo, String nome, String telefone, String celular, String email, Endereco endereco, Long telegramId, Long whatsAppId, SecurityContext context) {
        super(context);
        this.id = id;
        this.ativo = ativo;
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.email = email;
        this.endereco = endereco;
        this.telegramId = telegramId;
        this.whatsappId = whatsAppId;
    }

    public Entidade pessoa(Entidade entityOld, Entidade entity, SecurityContext context) {
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            entityOld.setNome(entity.getNome());
        }
        if (BasicFunctions.isValid(entity.getWhatsappId())) {
            entityOld.setWhatsappId(entity.getWhatsappId());
        }
        if (BasicFunctions.isValid(entity.getTelegramId())) {
            entityOld.setTelegramId(entity.getTelegramId());
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
        if (BasicFunctions.isNotEmpty(entity.getEndereco())) {
            entityOld.setEndereco(entity.getEndereco());
        }
        if (BasicFunctions.isNotEmpty(entity.getRecebeNotifacaoWhatsapp())) {
            entityOld.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoWhatsapp());
        }
        if (BasicFunctions.isNotEmpty(entity.getRecebeNotifacaoTelegram())) {
            entityOld.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoTelegram());
        }
        if (BasicFunctions.isNotEmpty(entity.getTenant())) {
            entityOld.setTenant(entity.getTenant());
        }
        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Long getWhatsappId() {
        return whatsappId;
    }

    public void setWhatsappId(Long whatsappId) {
        this.whatsappId = whatsappId;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getRecebeNotifacaoWhatsapp() {
        return recebeNotifacaoWhatsapp;
    }

    public void setRecebeNotifacaoWhatsapp(Boolean recebeNotifacaoWhatsapp) {
        this.recebeNotifacaoWhatsapp = recebeNotifacaoWhatsapp;
    }

    public Boolean getRecebeNotifacaoTelegram() {
        return recebeNotifacaoTelegram;
    }

    public void setRecebeNotifacaoTelegram(Boolean recebeNotifacaoTelegram) {
        this.recebeNotifacaoTelegram = recebeNotifacaoTelegram;
    }

    public String getTenant() {
        if (BasicFunctions.isNotEmpty(this, this.tenant)) {
            return this.tenant.toLowerCase();
        }
        return "";
    }

    public void setTenant(String tenant) {
        if (BasicFunctions.isNotEmpty(tenant)) {
            this.tenant = tenant.toLowerCase();
        }
    }

}
