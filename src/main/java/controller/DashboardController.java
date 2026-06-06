package controller;

import koneksi.Koneksi;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DashboardController {
    public double scalarDouble(String sql) throws SQLException { try (PreparedStatement ps=Koneksi.getConnection().prepareStatement(sql); ResultSet rs=ps.executeQuery()){ return rs.next()?rs.getDouble(1):0; } }
    public int scalarInt(String sql) throws SQLException { return (int) scalarDouble(sql); }
    public double saldoKas() throws SQLException { return scalarDouble("SELECT COALESCE((SELECT SUM(nominal) FROM pemasukan),0)-COALESCE((SELECT SUM(nominal) FROM pengeluaran),0)"); }
    public double pemasukanBulanIni() throws SQLException { return scalarDouble("SELECT COALESCE(SUM(nominal),0) FROM pemasukan WHERE MONTH(tanggal)=MONTH(CURDATE()) AND YEAR(tanggal)=YEAR(CURDATE())"); }
    public double pengeluaranBulanIni() throws SQLException { return scalarDouble("SELECT COALESCE(SUM(nominal),0) FROM pengeluaran WHERE MONTH(tanggal)=MONTH(CURDATE()) AND YEAR(tanggal)=YEAR(CURDATE())"); }
    public int siswaMenunggak() throws SQLException { return scalarInt("SELECT COUNT(DISTINCT id_siswa) FROM spp_tagihan WHERE status='BELUM_LUNAS'"); }
    public java.util.List<Object[]> transaksiTerbaru() throws SQLException { String sql="SELECT tanggal,'Pemasukan',nominal,keterangan FROM pemasukan UNION ALL SELECT tanggal,'Pengeluaran',nominal,keterangan FROM pengeluaran ORDER BY tanggal DESC LIMIT 10"; return rows(sql); }
    public java.util.List<Object[]> tunggakan() throws SQLException { return rows("SELECT s.nis,s.nama_siswa,st.bulan,st.tahun,st.nominal FROM spp_tagihan st JOIN siswa s ON s.id_siswa=st.id_siswa WHERE st.status='BELUM_LUNAS' ORDER BY st.tahun, st.bulan LIMIT 20"); }
    public java.util.List<Object[]> grafik6Bulan() throws SQLException { return rows("SELECT DATE_FORMAT(m.bln,'%b %Y') bulan, COALESCE(p.total,0), COALESCE(g.total,0) FROM (SELECT DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL n MONTH),'%Y-%m-01') bln FROM (SELECT 0 n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) x) m LEFT JOIN (SELECT DATE_FORMAT(tanggal,'%Y-%m-01') bln,SUM(nominal) total FROM pemasukan GROUP BY 1) p ON p.bln=m.bln LEFT JOIN (SELECT DATE_FORMAT(tanggal,'%Y-%m-01') bln,SUM(nominal) total FROM pengeluaran GROUP BY 1) g ON g.bln=m.bln ORDER BY m.bln"); }
    private java.util.List<Object[]> rows(String sql) throws SQLException { java.util.List<Object[]> list=new ArrayList<>(); try(PreparedStatement ps=Koneksi.getConnection().prepareStatement(sql); ResultSet rs=ps.executeQuery()){ int c=rs.getMetaData().getColumnCount(); while(rs.next()){ Object[] r=new Object[c]; for(int i=0;i<c;i++)r[i]=rs.getObject(i+1); list.add(r);} } return list; }
}
