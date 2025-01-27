package app.api.agendaFacil.management.database.redis;

import app.core.application.ContextManager;
import io.vertx.mutiny.redis.client.RedisAPI;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public abstract class RedisManager extends ContextManager {

    protected final RedisAPI redisAPI;
    protected final SecurityContext context;

    protected RedisManager() {
        this.redisAPI = null;
        this.context = null;
    }

    protected RedisManager(SecurityContext context, RedisAPI redisAPI) {
        super(context);
        this.redisAPI = redisAPI;
        this.context = context;
    }
}
