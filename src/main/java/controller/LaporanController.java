// File: controller/LaporanController.java
package controller;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Pengaturan;
import util.Koneksi;
import util.UIUtils;

public class LaporanController {
    public List<Map<String, Object>> getLaporan(LocalDate from, LocalDate to) {
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "SELECT tanggal, jenis, kategori, nominal, keterangan FROM ("
                + "SELECT p.tanggal, 'Pemasukan' jenis, kp.nama_kategori kategori, p.nominal, p.keterangan FROM pemasukan p JOIN kategori_pemasukan kp ON p.id_kategori_pemasukan=kp.id_kategori_pemasukan WHERE p.tanggal BETWEEN ? AND ? "
                + "UNION ALL SELECT pe.tanggal, 'Pengeluaran' jenis, kk.nama_kategori kategori, pe.nominal, pe.keterangan FROM pengeluaran pe JOIN kategori_pengeluaran kk ON pe.id_kategori_pengeluaran=kk.id_kategori_pengeluaran WHERE pe.tanggal BETWEEN ? AND ?"
                + ") x ORDER BY tanggal ASC";
        try (Connection con = Koneksi.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from)); ps.setDate(2, Date.valueOf(to)); ps.setDate(3, Date.valueOf(from)); ps.setDate(4, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { Map<String, Object> row = new LinkedHashMap<>(); row.put("tanggal", rs.getDate("tanggal").toLocalDate()); row.put("jenis", rs.getString("jenis")); row.put("kategori", rs.getString("kategori")); row.put("nominal", rs.getDouble("nominal")); row.put("keterangan", rs.getString("keterangan")); list.add(row); }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public void exportCSV(List<Map<String, Object>> data, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Tanggal;Jenis;Kategori;Nominal;Keterangan"); writer.newLine();
            for (Map<String, Object> row : data) { writer.write(row.get("tanggal") + ";" + row.get("jenis") + ";" + row.get("kategori") + ";" + UIUtils.formatRupiah((double) row.get("nominal")) + ";" + safe(row.get("keterangan"))); writer.newLine(); }
        }
    }

    public void exportPDF(List<Map<String, Object>> data, File file, Pengaturan setting) throws IOException {
        try (PdfWriter writer = new PdfWriter(file); PdfDocument pdf = new PdfDocument(writer); Document document = new Document(pdf)) {
            document.add(new Paragraph(setting == null ? "SKM Madrasah" : setting.getNamaSekolah()).setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Laporan Keuangan - Tanggal Cetak: " + LocalDate.now()).setTextAlignment(TextAlignment.CENTER));
            Table table = new Table(new float[]{2, 2, 3, 3, 4}).useAllAvailableWidth();
            String[] headers = {"Tanggal", "Jenis", "Kategori", "Nominal", "Keterangan"};
            for (String header : headers) table.addHeaderCell(new Cell().add(new Paragraph(header).setFontColor(ColorConstants.WHITE)).setBackgroundColor(new DeviceRgb(0x1E, 0x3A, 0x5F)));
            for (Map<String, Object> row : data) {
                table.addCell(String.valueOf(row.get("tanggal"))); table.addCell(String.valueOf(row.get("jenis"))); table.addCell(String.valueOf(row.get("kategori"))); table.addCell(UIUtils.formatRupiah((double) row.get("nominal"))); table.addCell(safe(row.get("keterangan")));
            }
            document.add(table);
        }
    }

    private String safe(Object value) { return value == null ? "" : String.valueOf(value).replace(";", ","); }
}
