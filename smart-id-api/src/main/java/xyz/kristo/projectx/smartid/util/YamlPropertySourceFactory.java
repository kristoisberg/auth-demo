package xyz.kristo.projectx.smartid.util;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Collection;

public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null) {
            return super.createPropertySource(name, resource);
        }

        Collection<PropertySource<?>> propertySourceList =
                new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());

        if (propertySourceList.isEmpty()) {
           return super.createPropertySource(name, resource);
        }

        return propertySourceList.iterator().next();
    }
}
