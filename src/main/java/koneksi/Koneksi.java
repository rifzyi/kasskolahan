package koneksi;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Koneksi {
    private static Connection connection;
    private Koneksi() {}
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties p = new Properties();
            try (FileInputStream in = new FileInputStream("config.properties")) { p.load(in); }
            catch (IOException e) { throw new SQLException("config.properties tidak ditemukan", e); }
            connection = DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.user"), p.getProperty("db.password"));
        }
        return connection;
    }
}
