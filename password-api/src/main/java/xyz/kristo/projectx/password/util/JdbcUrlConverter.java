package xyz.kristo.projectx.password.util;

import lombok.NonNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class JdbcUrlConverter {
    private static final Map<String, String> SCHEME_REPLACEMENTS = Map.of(
            "postgres", "postgresql"
    );

    private JdbcUrlConverter() {}

    public static String convert(@NonNull String url) {
        if (url.startsWith("jdbc:")) {
            return url;
        }

        try {
            URI uri = new URI(url);
            String scheme = SCHEME_REPLACEMENTS.getOrDefault(uri.getScheme(), uri.getScheme());
            Integer port = NetworkUtil.isValidPort(uri.getPort()) ? uri.getPort() : null;
            String params = convertUserInfoToParams(uri.getUserInfo());
            return format(scheme, uri.getHost(), port, uri.getPath(), params);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertUserInfoToParams(String userInfo) {
        if (userInfo == null) {
            return "";
        }

        String[] userInfoParts = userInfo.split(":", 2);
        return String.format("?user=%s&password=%s", userInfoParts[0], userInfoParts[1]);
    }

    private static String format(String scheme, String host, Integer port, String database, String params) {
        String hostname = formatHostname(host, port, database);
        return String.format("jdbc:%s://%s%s", scheme, hostname, params);
    }

    private static String formatHostname(String host, Integer port, @NonNull String database) {
        if (host == null) {
            return database;
        }

        if (port == null) {
            return String.format("%s%s", host, database);
        }

        return String.format("%s:%d%s", host, port, database);
    }
}
