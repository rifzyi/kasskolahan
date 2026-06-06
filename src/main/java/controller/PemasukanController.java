// File: controller/PemasukanController.java
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.KategoriPemasukan;
import model.Pemasukan;
import util.Koneksi;
import util.SessionManager;

public class PemasukanController {
    public List<KategoriPemasukan> getKategori() {
        List<KategoriPemasukan> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori_pemasukan ORDER BY nama_kategori";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { KategoriPemasukan k = new KategoriPemasukan(); k.setIdKategoriPemasukan(rs.getInt("id_kategori_pemasukan")); k.setNamaKategori(rs.getString("nama_kategori")); k.setIsSpp(rs.getBoolean("is_spp")); k.setNominalDefault(rs.getDouble("nominal_default")); list.add(k); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Pemasukan> getAll(LocalDate from, LocalDate to) {
        List<Pemasukan> list = new ArrayList<>();
        String sql = "SELECT p.*, s.nama_siswa, kp.nama_kategori, u.nama as nama_user FROM pemasukan p LEFT JOIN siswa s ON p.id_siswa = s.id_siswa LEFT JOIN kategori_pemasukan kp ON p.id_kategori_pemasukan = kp.id_kategori_pemasukan LEFT JOIN users u ON p.id_user_input = u.id_user WHERE p.tanggal BETWEEN ? AND ? ORDER BY p.tanggal DESC";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from)); ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Pemasukan p) { return save(p, false); }
    public boolean update(Pemasukan p) { return save(p, true); }

    private boolean save(Pemasukan p, boolean update) {
        String sql = update ? "UPDATE pemasukan SET tanggal=?, id_siswa=?, id_kategori_pemasukan=?, nominal=?, keterangan=?, bulan_spp=? WHERE id_pemasukan=?"
                : "INSERT INTO pemasukan (tanggal, id_siswa, id_kategori_pemasukan, nominal, keterangan, bulan_spp, id_user_input) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fill(ps, p); if (update) ps.setInt(7, p.getIdPemasukan()); else ps.setInt(7, currentUserId());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), update ? "UPDATE_PEMASUKAN" : "INSERT_PEMASUKAN", "Nominal: " + p.getNominal());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pemasukan WHERE id_pemasukan=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), "DELETE_PEMASUKAN", "ID: " + id);
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private void fill(PreparedStatement ps, Pemasukan p) throws SQLException {
        ps.setDate(1, Date.valueOf(p.getTanggal()));
        if (p.getIdSiswa() == null || p.getIdSiswa() == 0) ps.setNull(2, Types.INTEGER); else ps.setInt(2, p.getIdSiswa());
        ps.setInt(3, p.getIdKategoriPemasukan()); ps.setDouble(4, p.getNominal()); ps.setString(5, p.getKeterangan()); ps.setString(6, p.getBulanSpp());
    }

    private Pemasukan map(ResultSet rs) throws SQLException {
        Pemasukan p = new Pemasukan(); p.setIdPemasukan(rs.getInt("id_pemasukan")); p.setTanggal(rs.getDate("tanggal").toLocalDate());
        int idSiswa = rs.getInt("id_siswa"); p.setIdSiswa(rs.wasNull() ? null : idSiswa); p.setIdKategoriPemasukan(rs.getInt("id_kategori_pemasukan")); p.setNominal(rs.getDouble("nominal")); p.setKeterangan(rs.getString("keterangan")); p.setBulanSpp(rs.getString("bulan_spp")); p.setIdUserInput(rs.getInt("id_user_input")); p.setNamaSiswa(rs.getString("nama_siswa")); p.setNamaKategori(rs.getString("nama_kategori")); p.setNamaUser(rs.getString("nama_user")); return p;
    }

    private int currentUserId() { return SessionManager.getInstance().getCurrentUserId(); }
}
