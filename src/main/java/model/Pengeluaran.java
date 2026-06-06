package model;

import java.sql.Date;

public class Pengeluaran {
    private int idPengeluaran;
    private Date tanggal;
    private int idKategoriPengeluaran;
    private double nominal;
    private String keterangan;
    private int createdBy;

    public Pengeluaran() {}

    public Pengeluaran(int idPengeluaran, Date tanggal, int idKategoriPengeluaran, double nominal, String keterangan, int createdBy) {
        this.idPengeluaran = idPengeluaran;
        this.tanggal = tanggal;
        this.idKategoriPengeluaran = idKategoriPengeluaran;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.createdBy = createdBy;
    }

    public int getIdPengeluaran() { return idPengeluaran; }
    public void setIdPengeluaran(int idPengeluaran) { this.idPengeluaran = idPengeluaran; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
    public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
}
