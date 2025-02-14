package app.core.helpers.utils;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Singleton
public class HttpService {

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    @Inject
    Logger log;

    public CompletableFuture<HttpResponse<String>> GET(String url, String token) {
        log.info("-> HTTP Request / GET -" + url);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
                .header("X-Auth-Token", token)
                .timeout(Duration.ofSeconds(3))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    @Transactional
    public void post(String url, String tenant) {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Tenant", tenant)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        try {

            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (InterruptedException | IOException e) {
            if (Contexto.traceMethods()) {
                e.printStackTrace();
            }
        }
    }
}
