package xyz.kristo.projectx.smartid.config;

import ee.sk.smartid.AuthenticationResponseValidator;
import ee.sk.smartid.SmartIdClient;
import ee.sk.smartid.rest.SmartIdRestConnector;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
public class SmartIdConfig {
    @Bean
    public SmartIdClient smartIdClient(
            @Value("${smartId.hostUrl}") String hostUrl,
            @Value("${smartId.relyingPartyUuid}") String relyingPartyUuid,
            @Value("${smartId.relyingPartyName}") String relyingPartyName,
            @Value("${smartId.sslCertificates}") Collection<String> certificatePaths) {
        String[] trustedCertificates = certificatePaths.stream()
                .map(this::readCertificateFromClassPath)
                .map(this::inputStreamToString)
                .collect(Collectors.toList())
                .toArray(new String[certificatePaths.size()]);

        SmartIdClient smartIdClient = new SmartIdClient();
        smartIdClient.setHostUrl(hostUrl);
        smartIdClient.setRelyingPartyUUID(relyingPartyUuid);
        smartIdClient.setRelyingPartyName(relyingPartyName);
        smartIdClient.setTrustedCertificates(trustedCertificates);
        return smartIdClient;
    }

    @Bean
    public SmartIdRestConnector smartIdRestConnector(@Value("${smartId.hostUrl}") String hostUrl) {
        return new SmartIdRestConnector(hostUrl);
    }

    @Bean
    public AuthenticationResponseValidator authenticationResponseValidator(
            @Value("${smartId.certificates}") Collection<String> certificatePaths) {
        AuthenticationResponseValidator responseValidator = new AuthenticationResponseValidator();
        responseValidator.clearTrustedCACertificates();

        certificatePaths.stream()
                .map(this::readCertificateFromClassPath)
                .forEach(certificate -> addResponseValidatorCertificate(responseValidator, certificate));

        return responseValidator;
    }

    private InputStream readCertificateFromClassPath(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String inputStreamToString(InputStream inputStream) {
        try {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addResponseValidatorCertificate(AuthenticationResponseValidator responseValidator,
                                                 InputStream certificate) {
        try {
            responseValidator.addTrustedCACertificate(IOUtils.toByteArray(certificate));
        } catch (CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
