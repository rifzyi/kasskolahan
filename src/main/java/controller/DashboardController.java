// File: controller/DashboardController.java
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import util.Koneksi;

public class DashboardController {
    public double getTotalPemasukan(int bulan, int tahun) { return getMonthlyTotal("pemasukan", bulan, tahun); }
    public double getTotalPengeluaran(int bulan, int tahun) { return getMonthlyTotal("pengeluaran", bulan, tahun); }

    private double getMonthlyTotal(String table, int bulan, int tahun) {
        String sql = "SELECT COALESCE(SUM(nominal),0) total FROM " + table + " WHERE MONTH(tanggal)=? AND YEAR(tanggal)=?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bulan);
            ps.setInt(2, tahun);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("total");
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public double getSaldoKeseluruhan() {
        String sql = "SELECT (SELECT COALESCE(SUM(nominal),0) FROM pemasukan) - (SELECT COALESCE(SUM(nominal),0) FROM pengeluaran) saldo";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble("saldo");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Map<String, Object>> getRecentTransactions(int limit) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT tanggal, jenis, nama_kategori, nominal, keterangan FROM ("
                + "SELECT tanggal, 'Pemasukan' as jenis, kp.nama_kategori, p.nominal, p.keterangan FROM pemasukan p "
                + "JOIN kategori_pemasukan kp ON p.id_kategori_pemasukan = kp.id_kategori_pemasukan "
                + "UNION ALL "
                + "SELECT tanggal, 'Pengeluaran' as jenis, kk.nama_kategori, pe.nominal, pe.keterangan FROM pengeluaran pe "
                + "JOIN kategori_pengeluaran kk ON pe.id_kategori_pengeluaran = kk.id_kategori_pengeluaran"
                + ") x ORDER BY tanggal DESC LIMIT ?";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("tanggal", rs.getDate("tanggal").toLocalDate());
                    row.put("jenis", rs.getString("jenis"));
                    row.put("nama_kategori", rs.getString("nama_kategori"));
                    row.put("nominal", rs.getDouble("nominal"));
                    row.put("keterangan", rs.getString("keterangan"));
                    list.add(row);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}
