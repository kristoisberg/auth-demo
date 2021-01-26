package xyz.kristo.projectx.common.rest;

import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

public abstract class BaseRestClient {
    private final RestTemplate restTemplate;
    private final Validator validator;
    private final String serviceUrl;

    public BaseRestClient(RestTemplate restTemplate, Validator validator, String serviceUrl) {
        this.restTemplate = restTemplate;
        this.validator = validator;
        this.serviceUrl = serviceUrl;
    }

    protected <T> void post(String endpoint, T entity) {
        validate(entity);
        restTemplate.postForLocation(getEndpointUrl(endpoint), entity);
    }

    protected <T, K> K post(String endpoint, T entity, Class<K> resultType) {
        validate(entity);
        return restTemplate.postForObject(getEndpointUrl(endpoint), entity, resultType);
    }

    private <T> void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private String getEndpointUrl(String endpoint) {
        return serviceUrl + "/" + endpoint;
    }
}
