package controller;

import koneksi.Koneksi;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditLogger {
    public void log(int userId, String aksi, String tabelTarget, String dataLama, String dataBaru) {
        String sql = "INSERT INTO audit_log(user_id, aksi, tabel_target, data_lama, data_baru) VALUES(?,?,?,?,?)";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setString(2, aksi); ps.setString(3, tabelTarget); ps.setString(4, dataLama); ps.setString(5, dataBaru); ps.executeUpdate();
        } catch (SQLException e) { System.err.println("Audit log gagal: " + e.getMessage()); }
    }
}
