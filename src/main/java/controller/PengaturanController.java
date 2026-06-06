// File: controller/PengaturanController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Pengaturan;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import util.Koneksi;
import util.SessionManager;

public class PengaturanController {
    public Pengaturan getPengaturan() {
        String sql = "SELECT * FROM pengaturan ORDER BY id_pengaturan LIMIT 1";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return mapPengaturan(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        Pengaturan p = new Pengaturan(); p.setNamaSekolah("SKM Madrasah"); return p;
    }

    public boolean savePengaturan(Pengaturan p) {
        String update = "UPDATE pengaturan SET nama_sekolah=?, alamat=?, telepon=?, kepala_sekolah=?, bendahara=?, logo_path=? WHERE id_pengaturan=?";
        String insert = "INSERT INTO pengaturan (nama_sekolah, alamat, telepon, kepala_sekolah, bendahara, logo_path) VALUES (?, ?, ?, ?, ?, ?)";
        boolean hasId = p.getIdPengaturan() > 0;
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(hasId ? update : insert)) {
            ps.setString(1, p.getNamaSekolah()); ps.setString(2, p.getAlamat()); ps.setString(3, p.getTelepon()); ps.setString(4, p.getKepalaSekolah()); ps.setString(5, p.getBendahara()); ps.setString(6, p.getLogoPath()); if (hasId) ps.setInt(7, p.getIdPengaturan());
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), "SAVE_PENGATURAN", p.getNamaSekolah());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id_user, nama, username, password, role FROM users ORDER BY nama";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapUser(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean saveUser(User u) {
        boolean update = u.getIdUser() > 0;
        String sqlWithPassword = update ? "UPDATE users SET nama=?, username=?, password=?, role=? WHERE id_user=?" : "INSERT INTO users (nama, username, password, role) VALUES (?, ?, ?, ?)";
        String sqlNoPassword = "UPDATE users SET nama=?, username=?, role=? WHERE id_user=?";
        boolean changePassword = u.getPassword() != null && !u.getPassword().isBlank();
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(update && !changePassword ? sqlNoPassword : sqlWithPassword, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNama()); ps.setString(2, u.getUsername());
            if (update && !changePassword) { ps.setString(3, u.getRole()); ps.setInt(4, u.getIdUser()); }
            else { ps.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(12))); ps.setString(4, u.getRole()); if (update) ps.setInt(5, u.getIdUser()); }
            boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), update ? "UPDATE_USER" : "INSERT_USER", u.getUsername());
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id_user=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); boolean ok = ps.executeUpdate() > 0;
            if (ok) AuditLogger.log(currentUserId(), "DELETE_USER", "ID: " + id);
            return ok;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    private Pengaturan mapPengaturan(ResultSet rs) throws SQLException {
        Pengaturan p = new Pengaturan(); p.setIdPengaturan(rs.getInt("id_pengaturan")); p.setNamaSekolah(rs.getString("nama_sekolah")); p.setAlamat(rs.getString("alamat")); p.setTelepon(rs.getString("telepon")); p.setKepalaSekolah(rs.getString("kepala_sekolah")); p.setBendahara(rs.getString("bendahara")); p.setLogoPath(rs.getString("logo_path")); return p;
    }
    private User mapUser(ResultSet rs) throws SQLException {
        User u = new User(); u.setIdUser(rs.getInt("id_user")); u.setNama(rs.getString("nama")); u.setUsername(rs.getString("username")); u.setPassword(rs.getString("password")); u.setRole(rs.getString("role")); return u;
    }
    private int currentUserId() { return SessionManager.getInstance().getCurrentUserId(); }
}
