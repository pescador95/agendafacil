package app.core.entities.context;

import app.api.agendaFacil.management.auth.DTO.AuthDTO;
import app.core.helper.BasicHelper;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.config.ConfigProvider;

@RequestScoped
public class AuthContextTest extends BasicHelper {

    public static final String DEFAULT_TENANT = "test";
    public static final String DEFAULT_BASE_URL = System.getenv("QUARKUS_BASEURL");

    private final String tenant;
    private final String baseurl;
    private final String username = ConfigProvider.getConfig().getValue("com.br.pescador95.username", String.class);
    private final String password = ConfigProvider.getConfig().getValue("com.br.pescador95.password", String.class);
    private final AuthDTO requestAuth;
    private String accessToken;
    private String refreshToken;


    public AuthContextTest() {
        this.tenant = DEFAULT_TENANT;
        this.baseurl = DEFAULT_BASE_URL;
        this.requestAuth = new AuthDTO(username, password);
    }

    public AuthContextTest(String tenant) {
        this.tenant = tenant;
        this.baseurl = DEFAULT_BASE_URL;
        this.requestAuth = new AuthDTO(username, password);
    }

    public AuthContextTest(String tenant, String baseurl) {
        this.tenant = tenant;
        this.baseurl = baseurl;
        this.requestAuth = new AuthDTO(username, password);
    }

    public AuthDTO getRequestAuth() {
        return requestAuth;
    }

    public String getTenant() {
        return tenant;
    }

    public String getTenantHeaderKey() {
        return "X-Tenant";
    }

    public String getBaseUrl() {
        return baseurl;
    }


    public String getRefreshTokenToJson() {
        return attributeToJson("refreshToken", refreshToken);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthorizationHeaderKey() {
        return "Authorization";
    }

    public String getAuthorizationHeaderValue() {
        return "Bearer " + this.getAccessToken();
    }

    public String getContentTypeJson() {
        return "application/json";
    }

}
