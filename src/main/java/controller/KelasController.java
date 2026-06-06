// File: controller/KelasController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Kelas;
import util.Koneksi;
import util.SessionManager;

public class KelasController {
    public List<Kelas> getAll() {
        List<Kelas> list = new ArrayList<>();
        String sql = "SELECT id_kelas, kode_kelas, nama_kelas FROM kelas ORDER BY nama_kelas";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Kelas k) {
        String sql = "INSERT INTO kelas (kode_kelas, nama_kelas) VALUES (?, ?)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k.getKodeKelas()); ps.setString(2, k.getNamaKelas());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "INSERT_KELAS", k.getNamaKelas());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Kelas k) {
        String sql = "UPDATE kelas SET kode_kelas=?, nama_kelas=? WHERE id_kelas=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k.getKodeKelas()); ps.setString(2, k.getNamaKelas()); ps.setInt(3, k.getIdKelas());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "UPDATE_KELAS", k.getNamaKelas());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        if (hasActiveStudents(id)) return false;
        String sql = "DELETE FROM kelas WHERE id_kelas=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "DELETE_KELAS", "ID: " + id);
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private boolean hasActiveStudents(int id) {
        String sql = "SELECT COUNT(*) total FROM siswa WHERE id_kelas=? AND aktif=1";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() && rs.getInt("total") > 0; }
        } catch (SQLException e) { e.printStackTrace(); return true; }
    }

    private Kelas map(ResultSet rs) throws SQLException {
        Kelas k = new Kelas(); k.setIdKelas(rs.getInt("id_kelas")); k.setKodeKelas(rs.getString("kode_kelas")); k.setNamaKelas(rs.getString("nama_kelas")); return k;
    }
}
