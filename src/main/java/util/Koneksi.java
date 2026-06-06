// File: util/Koneksi.java
package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Koneksi {
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/kas_sekolah"
            + "?useSSL=false&serverTimezone=Asia/Jakarta"
            + "&characterEncoding=utf8";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "";
    private static final Properties CONFIG = loadConfig();

    private Koneksi() {
    }

    public static Connection getConnection() throws SQLException {
        String url = CONFIG.getProperty("db.url", DEFAULT_URL);
        String user = CONFIG.getProperty("db.user", DEFAULT_USER);
        String pass = CONFIG.getProperty("db.password", DEFAULT_PASS);
        return DriverManager.getConnection(url, user, pass);
    }

    private static Properties loadConfig() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException ignored) {
        }
        return properties;
    }
}
