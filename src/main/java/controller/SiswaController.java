// File: controller/SiswaController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import model.Siswa;
import util.Koneksi;
import util.SessionManager;

public class SiswaController {
    public List<Siswa> getAll() {
        String sql = "SELECT s.*, k.nama_kelas FROM siswa s LEFT JOIN kelas k ON s.id_kelas = k.id_kelas WHERE s.aktif = 1 ORDER BY s.nama_siswa";
        return query(sql, null);
    }

    public List<Siswa> getByKelas(int idKelas) {
        String sql = "SELECT s.*, k.nama_kelas FROM siswa s LEFT JOIN kelas k ON s.id_kelas = k.id_kelas WHERE s.aktif = 1 AND s.id_kelas=? ORDER BY s.nama_siswa";
        return query(sql, idKelas);
    }

    private List<Siswa> query(String sql, Integer idKelas) {
        List<Siswa> list = new ArrayList<>();
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            if (idKelas != null) ps.setInt(1, idKelas);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Siswa s) {
        String sql = "INSERT INTO siswa (nis, nama_siswa, id_kelas, jenis_kelamin, alamat, aktif) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            fill(ps, s); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "INSERT_SISWA", s.getNamaSiswa());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Siswa s) {
        String sql = "UPDATE siswa SET nis=?, nama_siswa=?, id_kelas=?, jenis_kelamin=?, alamat=? WHERE id_siswa=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            fill(ps, s); ps.setInt(6, s.getIdSiswa()); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "UPDATE_SISWA", s.getNamaSiswa());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "UPDATE siswa SET aktif=0 WHERE id_siswa=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(SessionManager.getInstance().getCurrentUserId(), "DELETE_SISWA", "ID: " + id);
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private void fill(PreparedStatement ps, Siswa s) throws SQLException {
        ps.setString(1, s.getNis()); ps.setString(2, s.getNamaSiswa());
        if (s.getIdKelas() == null || s.getIdKelas() == 0) ps.setNull(3, Types.INTEGER); else ps.setInt(3, s.getIdKelas());
        ps.setString(4, s.getJenisKelamin()); ps.setString(5, s.getAlamat());
    }

    private Siswa map(ResultSet rs) throws SQLException {
        Siswa s = new Siswa(); s.setIdSiswa(rs.getInt("id_siswa")); s.setNis(rs.getString("nis")); s.setNamaSiswa(rs.getString("nama_siswa"));
        int idKelas = rs.getInt("id_kelas"); s.setIdKelas(rs.wasNull() ? null : idKelas); s.setJenisKelamin(rs.getString("jenis_kelamin"));
        s.setAlamat(rs.getString("alamat")); s.setAktif(rs.getBoolean("aktif")); s.setNamaKelas(rs.getString("nama_kelas")); return s;
    }
}
