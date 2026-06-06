// File: controller/AuditLogger.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.Koneksi;

public final class AuditLogger {
    private AuditLogger() {
    }

    public static void log(int userId, String aksi, String detail) {
        if (userId <= 0) {
            return;
        }
        String sql = "INSERT INTO audit_log (id_user, aksi, detail) VALUES (?, ?, ?)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, aksi);
            ps.setString(3, detail);
            ps.executeUpdate();
        } catch (SQLException ignored) {
        }
    }
}
