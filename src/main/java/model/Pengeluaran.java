// File: model/Pengeluaran.java
package model;

import java.time.LocalDate;

public class Pengeluaran {
    private int idPengeluaran;
    private LocalDate tanggal;
    private int idKategoriPengeluaran;
    private double nominal;
    private String keterangan;
    private int idUserInput;
    private String namaKategori;
    private String namaUser;

    public int getIdPengeluaran() { return idPengeluaran; }
    public void setIdPengeluaran(int idPengeluaran) { this.idPengeluaran = idPengeluaran; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public int getIdKategoriPengeluaran() { return idKategoriPengeluaran; }
    public void setIdKategoriPengeluaran(int idKategoriPengeluaran) { this.idKategoriPengeluaran = idKategoriPengeluaran; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getIdUserInput() { return idUserInput; }
    public void setIdUserInput(int idUserInput) { this.idUserInput = idUserInput; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getNamaUser() { return namaUser; }
    public void setNamaUser(String namaUser) { this.namaUser = namaUser; }
}
