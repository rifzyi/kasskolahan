// File: controller/PengeluaranController.java
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.KategoriPengeluaran;
import model.Pengeluaran;
import util.Koneksi;
import util.SessionManager;

public class PengeluaranController {
    public List<KategoriPengeluaran> getKategori() {
        List<KategoriPengeluaran> list = new ArrayList<>();
        String sql = "SELECT * FROM kategori_pengeluaran ORDER BY nama_kategori";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { KategoriPengeluaran k = new KategoriPengeluaran(); k.setIdKategoriPengeluaran(rs.getInt("id_kategori_pengeluaran")); k.setNamaKategori(rs.getString("nama_kategori")); list.add(k); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Pengeluaran> getAll(LocalDate from, LocalDate to) {
        List<Pengeluaran> list = new ArrayList<>();
        String sql = "SELECT pe.*, kk.nama_kategori, u.nama as nama_user FROM pengeluaran pe LEFT JOIN kategori_pengeluaran kk ON pe.id_kategori_pengeluaran = kk.id_kategori_pengeluaran LEFT JOIN users u ON pe.id_user_input = u.id_user WHERE pe.tanggal BETWEEN ? AND ? ORDER BY pe.tanggal DESC";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from)); ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(Pengeluaran p) { return save(p, false); }
    public boolean update(Pengeluaran p) { return save(p, true); }

    private boolean save(Pengeluaran p, boolean update) {
        String sql = update ? "UPDATE pengeluaran SET tanggal=?, id_kategori_pengeluaran=?, nominal=?, keterangan=? WHERE id_pengeluaran=?"
                : "INSERT INTO pengeluaran (tanggal, id_kategori_pengeluaran, nominal, keterangan, id_user_input) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(p.getTanggal())); ps.setInt(2, p.getIdKategoriPengeluaran()); ps.setDouble(3, p.getNominal()); ps.setString(4, p.getKeterangan()); ps.setInt(5, update ? p.getIdPengeluaran() : currentUserId());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), update ? "UPDATE_PENGELUARAN" : "INSERT_PENGELUARAN", "Nominal: " + p.getNominal());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM pengeluaran WHERE id_pengeluaran=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), "DELETE_PENGELUARAN", "ID: " + id);
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Pengeluaran map(ResultSet rs) throws SQLException {
        Pengeluaran p = new Pengeluaran(); p.setIdPengeluaran(rs.getInt("id_pengeluaran")); p.setTanggal(rs.getDate("tanggal").toLocalDate()); p.setIdKategoriPengeluaran(rs.getInt("id_kategori_pengeluaran")); p.setNominal(rs.getDouble("nominal")); p.setKeterangan(rs.getString("keterangan")); p.setIdUserInput(rs.getInt("id_user_input")); p.setNamaKategori(rs.getString("nama_kategori")); p.setNamaUser(rs.getString("nama_user")); return p;
    }
    private int currentUserId() { return SessionManager.getInstance().getCurrentUserId(); }
}
