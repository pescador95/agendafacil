package app.api.agendaFacil.business.pessoa.entity;


import app.api.agendaFacil.business.genero.entity.Genero;
import app.api.agendaFacil.business.genero.repository.GeneroRepository;
import app.api.agendaFacil.business.pessoa.DTO.EntidadeDTO;
import app.api.agendaFacil.business.pessoa.DTO.PessoaDTO;
import app.api.agendaFacil.business.usuario.DTO.UsuarioDTO;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.StringFunctions;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;

@Entity
public class Pessoa extends Entidade {

    @SequenceGenerator(name = "clienteIdSequence", sequenceName = "cliente_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @PrimaryKeyJoinColumn(name = "id")
    @Id
    private Long id;

    @Column()
    private String cpf;

    @Column()
    private LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "generoId")

    private Genero genero;
    @Column()
    private Boolean ativo;

    public Pessoa() {
        super();
    }

    public Pessoa(Long id) {
        super();
        this.id = id;
    }

    @Inject
    public Pessoa(SecurityContext context, String tenant) {
        super(context);
        this.setTenant(tenant);
        this.createAudit(context);
    }

    public Pessoa(EntidadeDTO entity, SecurityContext context, String tenant) {
        super(entity, context);

        if (BasicFunctions.isNotEmpty(entity)) {
            this.cpf = StringFunctions.extractNumericDigits(entity.getCpf());
            this.dataNascimento = entity.getDataNascimento();
            if (BasicFunctions.isNotEmpty(entity.getGenero())) {
                this.genero = GeneroRepository.findById(entity.getGenero());
            }
            this.ativo = entity.getAtivo();
        }
        this.setTenant(tenant);
        this.createAudit(context);
    }

    public Pessoa(UsuarioDTO entity) {
        super(entity);
        this.cpf = StringFunctions.extractNumericDigits(entity.loadPessoaCpf());
        this.dataNascimento = entity.loadPessoaDataNascimento();
        this.ativo = entity.getAtivo();
        this.setTenant(entity.getTenant());
    }

    public Pessoa(String cpf, LocalDate dataNascimento, Genero genero, Boolean ativo, String tenant) {
        super();
        this.cpf = StringFunctions.extractNumericDigits(cpf);
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.ativo = ativo;
        this.setTenant(tenant);
    }

    public Pessoa(Long id, String cpf, LocalDate dataNascimento, Genero genero, Boolean ativo) {
        super();
        this.id = id;
        this.cpf = StringFunctions.extractNumericDigits(cpf);
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.ativo = ativo;
    }

    public Pessoa(PessoaDTO entity, SecurityContext context) {
        super(entity);
        this.id = entity.getId();
        this.ativo = entity.getAtivo();
        this.setNome(entity.getNome());
        this.setDataNascimento(entity.getDataNascimento());
        this.setTelefone(entity.getTelefone());
        this.setCelular(entity.getCelular());
        this.setEmail(entity.getEmail());
        this.setCpf(entity.getCpf());
        this.setWhatsappId(entity.getWhatsappId());
        this.setTelegramId(entity.getTelegramId());
        this.setId(entity.getId());
        this.setEndereco(entity.getEndereco());
        if (entity.getGenero() != null) {
            this.setGenero(new Genero(entity.getGenero()));
        }
        if (entity.getEndereco() != null) {
            this.setEndereco(entity.getEndereco());
        }
        this.createAudit(context);
    }

    public Pessoa(PessoaDTO entity) {
        super(entity);
        this.id = entity.getId();
        this.ativo = entity.getAtivo();
        this.setNome(entity.getNome());
        this.setDataNascimento(entity.getDataNascimento());
        this.setTelefone(entity.getTelefone());
        this.setCelular(entity.getCelular());
        this.setEmail(entity.getEmail());
        this.setCpf(entity.getCpf());
        this.setWhatsappId(entity.getWhatsappId());
        this.setTelegramId(entity.getTelegramId());
        this.setId(entity.getId());
        this.setEndereco(entity.getEndereco());
        if (entity.getGenero() != null) {
            this.setGenero(new Genero(entity.getGenero()));
        }
        if (entity.getEndereco() != null) {
            this.setEndereco(entity.getEndereco());
        }
    }


    public Pessoa(Pessoa entity, Genero genero, SecurityContext context, String tenant) {
        super();
        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            this.setNome(entity.getNome());
        }
        if (BasicFunctions.isNotEmpty(genero) && BasicFunctions.isValid(genero)) {
            this.setGenero(genero);
        }
        if (BasicFunctions.isValid(entity.getDataNascimento())) {
            this.setDataNascimento(entity.getDataNascimento());
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
        this.setWhatsappId(entity.getWhatsappId());
        this.setTelegramId(entity.getTelegramId());
        this.setCpf(entity.getCpf());
        this.setAtivo(Boolean.TRUE);
        this.setTenant(tenant);
        this.createAudit(context);
    }

    public Pessoa(EntidadeDTO entity, Genero genero, SecurityContext context, String tenant) {
        super(context);

        if (BasicFunctions.isNotEmpty(entity)) {

            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                this.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(genero) && BasicFunctions.isValid(genero)) {
                this.setGenero(genero);
            }
            if (BasicFunctions.isValid(entity.getDataNascimento())) {
                this.setDataNascimento(entity.getDataNascimento());
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
            this.setRecebeNotifacaoTelegram(entity.getRecebeNotifacaoTelegram());
            this.setRecebeNotifacaoWhatsapp(entity.getRecebeNotifacaoWhatsapp());
            this.setCpf(entity.getCpf());
        }
        this.setAtivo(Boolean.TRUE);
        this.setTenant(tenant);
        this.createAudit(context);
    }

    public Pessoa pessoa(Pessoa entityOld, EntidadeDTO entity, Genero genero, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(entity)) {


            if (BasicFunctions.isNotEmpty(entity.getNome())) {
                entityOld.setNome(entity.getNome());
            }
            if (BasicFunctions.isNotEmpty(genero)) {
                entityOld.setGenero(genero);
            }
            if (BasicFunctions.isValid(entity.getDataNascimento())) {
                entityOld.setDataNascimento(entity.getDataNascimento());
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
        }
        entityOld.setWhatsappId(entity.getWhatsappId());
        entityOld.setTelegramId(entity.getTelegramId());
        entityOld.setCpf(entity.getCpf());
        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }


    public Pessoa pessoa(Pessoa entityOld, Pessoa entity, Genero genero, SecurityContext context) {

        if (BasicFunctions.isNotEmpty(entity.getNome())) {
            entityOld.setNome(entity.getNome());
        }
        if (BasicFunctions.isNotEmpty(genero)) {
            entityOld.setGenero(genero);
        }
        if (BasicFunctions.isValid(entity.getDataNascimento())) {
            entityOld.setDataNascimento(entity.getDataNascimento());
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
        entityOld.setWhatsappId(entity.getWhatsappId());
        entityOld.setTelegramId(entity.getTelegramId());
        entityOld.setCpf(entity.getCpf());
        entityOld.setAtivo(Boolean.TRUE);
        entityOld.updateAudit(context);
        return entityOld;
    }

    public Pessoa deletarPessoa(Pessoa entity, SecurityContext context) {

        entity.setAtivo(Boolean.FALSE);
        entity.deleteAudit(context);
        return entity;
    }

    public Pessoa reativarPessoa(Pessoa entity, SecurityContext context) {

        entity.setAtivo(Boolean.TRUE);
        entity.restoreAudit(context);
        return entity;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    private void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = StringFunctions.extractNumericDigits(cpf);
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}
