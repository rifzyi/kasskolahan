package controller;

import koneksi.Koneksi;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AuthController {
    private User currentUser;
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && BCrypt.checkpw(password, rs.getString("password_hash"))) {
                    currentUser = new User(rs.getInt("id"), rs.getString("nama"), rs.getString("username"), rs.getString("password_hash"), rs.getString("role"), rs.getTimestamp("created_at"));
                    new AuditLogger().log(currentUser.getId(), "LOGIN", "users", null, username);
                    return currentUser;
                }
            }
        }
        return null;
    }
    public boolean gantiPassword(int userId, String passwordBaru) throws SQLException {
        String sql = "UPDATE users SET password_hash=? WHERE id=?";
        try (PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql)) { ps.setString(1, BCrypt.hashpw(passwordBaru, BCrypt.gensalt())); ps.setInt(2, userId); return ps.executeUpdate() > 0; }
    }
    public User getCurrentUser() { return currentUser; }
}
