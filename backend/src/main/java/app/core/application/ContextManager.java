package app.core.application;

import app.api.agendaFacil.business.usuario.entity.Usuario;
import app.core.application.DTO.Responses;
import app.core.helpers.utils.BasicFunctions;
import app.core.helpers.utils.Contexto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ApplicationScoped
public abstract class ContextManager {

    protected static Boolean traceMethods;
    protected static Boolean schedulerEnabled;

    static {
        try {
            traceMethods = Contexto.traceMethods();
            schedulerEnabled = Contexto.scheduleEnabled();
        } catch (Exception e) {
            traceMethods = false;
            schedulerEnabled = false;
            System.err.println("Erro ao carregar configurações do Contexto: " + e.getMessage());
        }
    }

    protected final SecurityContext securityContext;
    protected Usuario usuarioAuth;
    protected LocalDate dataContexto;
    protected LocalTime horarioContexto;
    protected LocalDateTime dataHoraContexto;
    protected Responses responses;
    protected Long count;
    protected String query;
    protected String tenant;

    protected ContextManager(SecurityContext securityContext) {
        this.securityContext = securityContext;
        try {
            if (this.securityContext != null) {
                this.usuarioAuth = Contexto.getContextUser(this.securityContext);
            }
        } catch (IllegalStateException e) {
            this.usuarioAuth = null;
            e.printStackTrace();
        } finally {
            this.dataContexto = Contexto.dataContexto();
            this.horarioContexto = Contexto.horarioContexto(Contexto.defaultZoneIdToString());
            this.dataHoraContexto = Contexto.dataHoraContexto();
        }
        this.count = 0L;
        this.query = null;
    }

    protected ContextManager() {
        this.securityContext = null;
        this.usuarioAuth = null;
        this.dataContexto = Contexto.dataContexto();
        this.horarioContexto = Contexto.horarioContexto(Contexto.defaultZoneIdToString());
        this.dataHoraContexto = Contexto.dataHoraContexto();
        this.count = 0L;
        this.query = null;
    }

    public static Boolean getTraceMethods() {
        return BasicFunctions.isNotEmpty(traceMethods) && traceMethods;
    }

    public static Boolean getSchedulerEnabled() {
        return BasicFunctions.isNotEmpty(schedulerEnabled) && schedulerEnabled;
    }

    public String getTenant() {
        if (BasicFunctions.isNotEmpty(this, this.tenant)) {
            return this.tenant.toLowerCase();
        }
        return "config".toLowerCase();
    }

    public void setTenant(String tenant) {
        if (BasicFunctions.isNotEmpty(tenant)) {
            this.tenant = tenant.toLowerCase();
        }
    }
}
