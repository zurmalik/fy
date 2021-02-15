package zur.fyayc.outbound.rest;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${trust.store}")
    private Resource trustStore;

    @Value("${trust.store.password}")
    private String trustStorePassword;

    @Value("${fyayc.orderapi.maxconnections}")
    private int maxconnections;

    @Value("${fyayc.orderapi.timeout}")
    private int timeout;

    @Bean
    public RestTemplate fyaycRestTemplate() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException,
            CertificateException, IOException {

        SSLContext sslContext = new SSLContextBuilder()
            .loadTrustMaterial(
                trustStore.getURL(),
                trustStorePassword.toCharArray()
            ).build();

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
            sslContext,
            new NoopHostnameVerifier() // TODO F 5 Check if it is 'Development' profile then add this otherwise let HostNameVerification happen
        );

        HttpClient httpClient = HttpClients
            .custom()
//            .setMaxConnPerRoute(maxconnections)
            .setSSLSocketFactory(socketFactory)
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        factory.setConnectTimeout(timeout);

        return new RestTemplate(factory);
    }
}
