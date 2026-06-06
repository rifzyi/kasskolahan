// File: controller/TabunganController.java
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Tabungan;
import util.Koneksi;
import util.SessionManager;

public class TabunganController {
    public boolean setor(Tabungan t) { t.setJenis("SETOR"); t.setSaldoSetelah(getSaldoAktif(t.getIdSiswa()) + t.getNominal()); return insert(t); }
    public boolean tarik(Tabungan t) { double saldo = getSaldoAktif(t.getIdSiswa()); if (t.getNominal() > saldo) return false; t.setJenis("TARIK"); t.setSaldoSetelah(saldo - t.getNominal()); return insert(t); }

    private boolean insert(Tabungan t) {
        String sql = "INSERT INTO tabungan_siswa (id_siswa, tanggal, jenis, nominal, saldo_setelah, keterangan, id_user_input) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, t.getIdSiswa()); ps.setDate(2, Date.valueOf(t.getTanggal())); ps.setString(3, t.getJenis()); ps.setDouble(4, t.getNominal()); ps.setDouble(5, t.getSaldoSetelah()); ps.setString(6, t.getKeterangan()); ps.setInt(7, currentUserId());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), "INSERT_TABUNGAN_" + t.getJenis(), "Siswa: " + t.getIdSiswa() + ", Nominal: " + t.getNominal());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Tabungan> getMutasiBySiswa(int idSiswa) {
        List<Tabungan> list = new ArrayList<>();
        String sql = "SELECT t.*, s.nama_siswa FROM tabungan_siswa t JOIN siswa s ON t.id_siswa=s.id_siswa WHERE t.id_siswa=? ORDER BY t.tanggal DESC, t.created_at DESC";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public double getSaldoAktif(int idSiswa) {
        String sql = "SELECT COALESCE((SELECT saldo_setelah FROM tabungan_siswa WHERE id_siswa = ? ORDER BY created_at DESC LIMIT 1), 0) as saldo";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) return rs.getDouble("saldo"); }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    private Tabungan map(ResultSet rs) throws SQLException {
        Tabungan t = new Tabungan(); t.setIdTabungan(rs.getInt("id_tabungan")); t.setIdSiswa(rs.getInt("id_siswa")); t.setTanggal(rs.getDate("tanggal").toLocalDate()); t.setJenis(rs.getString("jenis")); t.setNominal(rs.getDouble("nominal")); t.setSaldoSetelah(rs.getDouble("saldo_setelah")); t.setKeterangan(rs.getString("keterangan")); t.setIdUserInput(rs.getInt("id_user_input")); t.setNamaSiswa(rs.getString("nama_siswa")); return t;
    }
    private int currentUserId() { return SessionManager.getInstance().getCurrentUserId(); }
}
