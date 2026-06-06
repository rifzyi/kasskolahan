// File: model/Pemasukan.java
package model;

import java.time.LocalDate;

public class Pemasukan {
    private int idPemasukan;
    private LocalDate tanggal;
    private Integer idSiswa;
    private int idKategoriPemasukan;
    private double nominal;
    private String keterangan;
    private String bulanSpp;
    private int idUserInput;
    private String namaSiswa;
    private String namaKategori;
    private String namaUser;

    public int getIdPemasukan() { return idPemasukan; }
    public void setIdPemasukan(int idPemasukan) { this.idPemasukan = idPemasukan; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public Integer getIdSiswa() { return idSiswa; }
    public void setIdSiswa(Integer idSiswa) { this.idSiswa = idSiswa; }
    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public String getBulanSpp() { return bulanSpp; }
    public void setBulanSpp(String bulanSpp) { this.bulanSpp = bulanSpp; }
    public int getIdUserInput() { return idUserInput; }
    public void setIdUserInput(int idUserInput) { this.idUserInput = idUserInput; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public String getNamaUser() { return namaUser; }
    public void setNamaUser(String namaUser) { this.namaUser = namaUser; }
}
