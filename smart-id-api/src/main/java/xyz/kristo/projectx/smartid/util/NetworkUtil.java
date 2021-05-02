package xyz.kristo.projectx.smartid.util;

public class NetworkUtil {
    private NetworkUtil() {
        throw new IllegalAccessError("Utility class can not be instantiated");
    }

    public static boolean isValidPort(int port) {
        return port >= 1 && port <= 65535;
    }
}
