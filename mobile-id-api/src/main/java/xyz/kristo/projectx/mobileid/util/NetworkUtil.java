package xyz.kristo.projectx.mobileid.util;

public class NetworkUtil {
    private NetworkUtil() {}

    public static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }
}
