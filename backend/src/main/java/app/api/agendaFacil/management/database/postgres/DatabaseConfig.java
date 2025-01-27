package app.api.agendaFacil.management.database.postgres;

import app.core.helpers.utils.Info;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.sql.*;

import static app.core.helpers.utils.BasicFunctions.log;
import static app.core.helpers.utils.Info.*;

@ConfigProperties(prefix = "quarkus.datasource.jdbc")
public class DatabaseConfig {

    @ConfigProperty(name = "url")
    private final String jdbcUrl;

    @ConfigProperty(name = "username")
    private final String username;

    @ConfigProperty(name = "password")
    private final String password;

    private final String defaultSchemaName;

    public DatabaseConfig() {
        this.jdbcUrl = databaseBaseURL();
        this.username = databaseUser();
        this.password = databasePassword();
        this.defaultSchemaName = Info.defaultSchemaName();
    }

    public static void executeUpdate(String sql) {

        try {

            DatabaseConfig databaseConfig = new DatabaseConfig();

            Connection connection = DriverManager.getConnection(databaseConfig.getJdbcUrl(), databaseConfig.getUsername(), databaseConfig.getPassword());

            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            log("Erro ao executar statement: " + e.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql) {

        try {

            DatabaseConfig databaseConfig = new DatabaseConfig();

            Connection connection = DriverManager.getConnection(databaseConfig.getJdbcUrl(), databaseConfig.getUsername(), databaseConfig.getPassword());

            Statement statement = connection.createStatement();

            return statement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            log("Erro ao executar query: " + e.getMessage());
        }
        return null;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDefaultSchemaName() {
        return defaultSchemaName;
    }

    public Database getDatabase() throws SQLException, DatabaseException {

        DatabaseConfig databaseConfig = new DatabaseConfig();

        Connection connection = DriverManager.getConnection(databaseConfig.getJdbcUrl(), databaseConfig.getUsername(), databaseConfig.getPassword());

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

        database.setDefaultSchemaName(databaseConfig.getDefaultSchemaName());

        return database;
    }
}