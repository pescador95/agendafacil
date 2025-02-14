package app.core.liquibase.manager;

import app.api.agendaFacil.management.database.postgres.DatabaseConfig;
import app.core.application.ContextManager;
import io.quarkus.liquibase.LiquibaseFactory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public abstract class LiquibaseManager extends ContextManager {

    protected final LiquibaseFactory liquibaseFactory;
    protected DatabaseConfig databaseConfig;

    protected LiquibaseManager() {
        super();
        this.liquibaseFactory = null;
    }

    protected LiquibaseManager(LiquibaseFactory liquibaseFactory) {
        super();
        this.liquibaseFactory = liquibaseFactory;
    }


}
