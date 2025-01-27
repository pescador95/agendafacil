package app.core.application.config;

import app.core.application.entity.EntityBase;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.ws.rs.core.SecurityContext;

@Table(name = "applicationconfig", schema = "config")
@Entity
public class ApplicationConfig extends EntityBase {


    @Column()
    @SequenceGenerator(name = "applicationIdSequence", sequenceName = "application_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column()
    private String agendaFacilUrl;

    @Column()
    private Boolean scheduleEnabled;

    @Column()
    private Integer quarkusPort;

    @Column()
    private String quarkusUrl;

    @Column()
    private String quarkusBaseUrl;

    @Column()
    private String whatsappBaseUrl;

    @Column()
    private String telegramBaseUrl;

    @Column()
    private String redisBaseUrl;

    @Column()
    private String emailUser;

    @Column()
    private String emailPassword;

    @Column()
    private String profile;

    @Column()
    private Boolean traceMethods;

    public ApplicationConfig() {
        super();
    }

    @Inject
    protected ApplicationConfig(SecurityContext context) {
        super(context);
    }

    protected ApplicationConfig(Boolean scheduleEnabled, Integer quarkusPort, String quarkusUrl, String quarkusBaseUrl, String whatsappBaseUrl, String telegramBaseUrl, String redisBaseUrl, String emailUser, String emailPassword, String agendaFacilUrl, String profile) {
        super();
        this.scheduleEnabled = scheduleEnabled;
        this.quarkusPort = quarkusPort;
        this.quarkusUrl = quarkusUrl;
        this.quarkusBaseUrl = quarkusBaseUrl;
        this.whatsappBaseUrl = whatsappBaseUrl;
        this.telegramBaseUrl = telegramBaseUrl;
        this.redisBaseUrl = redisBaseUrl;
        this.emailUser = emailUser;
        this.emailPassword = emailPassword;
        this.agendaFacilUrl = agendaFacilUrl;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getScheduleEnabled() {
        return scheduleEnabled;
    }

    public Integer getQuarkusPort() {
        return quarkusPort;
    }

    public String getQuarkusUrl() {
        return quarkusUrl;
    }

    public String getQuarkusBaseUrl() {
        return quarkusBaseUrl;
    }

    public String getWhatsappBaseUrl() {
        return whatsappBaseUrl;
    }

    public String getTelegramBaseUrl() {
        return telegramBaseUrl;
    }

    public String getRedisBaseUrl() {
        return redisBaseUrl;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public String getAgendaFacilUrl() {
        return agendaFacilUrl;
    }

    public String getProfile() {
        return profile;
    }

    public Boolean getTraceMethods() {
        return traceMethods;
    }
}
