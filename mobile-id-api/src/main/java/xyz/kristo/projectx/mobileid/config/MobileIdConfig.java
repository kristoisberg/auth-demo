package xyz.kristo.projectx.mobileid.config;

import ee.sk.mid.MidAuthenticationResponseValidator;
import ee.sk.mid.MidClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class MobileIdConfig {
    @Bean
    public MidClient midClient(
            @Value("${mobileId.hostUrl}") String hostUrl,
            @Value("${mobileId.relyingPartyUuid}") String relyingPartyUuid,
            @Value("${mobileId.relyingPartyName}") String relyingPartyName,
            @Value("${mobileId.sslCertificates}") Collection<String> certificatePaths) {
        String[] trustedCertificates = certificatePaths.stream()
                .map(this::readCertificateFromClassPath)
                .map(this::inputStreamToString)
                .collect(Collectors.toList())
                .toArray(new String[certificatePaths.size()]);

        return MidClient.newBuilder()
                .withHostUrl(hostUrl)
                .withRelyingPartyUUID(relyingPartyUuid)
                .withRelyingPartyName(relyingPartyName)
                .withTrustedCertificates(trustedCertificates)
                .build();
    }

    @Bean
    public MidAuthenticationResponseValidator midAuthenticationResponseValidator(
            @Value("${mobileId.certificates}") Collection<String> certificatePaths
    ) {
        CertificateFactory certificateFactory = createX509CertificateFactory();

        List<X509Certificate> trustedCertificates = certificatePaths.stream()
                .map(this::readCertificateFromClassPath)
                .map(certificate -> createX509Certificate(certificateFactory, certificate))
                .collect(Collectors.toList());

        return new MidAuthenticationResponseValidator(trustedCertificates);
    }

    private CertificateFactory createX509CertificateFactory() {
        try {
            return CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }

    private X509Certificate createX509Certificate(CertificateFactory certificateFactory, InputStream certificate) {
        try {
            return (X509Certificate) certificateFactory.generateCertificate(certificate);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
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
}
