// File: model/Tabungan.java
package model;

import java.time.LocalDate;

public class Tabungan {
    private int idTabungan;
    private int idSiswa;
    private LocalDate tanggal;
    private String jenis;
    private double nominal;
    private double saldoSetelah;
    private String keterangan;
    private int idUserInput;
    private String namaSiswa;

    public int getIdTabungan() { return idTabungan; }
    public void setIdTabungan(int idTabungan) { this.idTabungan = idTabungan; }
    public int getIdSiswa() { return idSiswa; }
    public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
    public LocalDate getTanggal() { return tanggal; }
    public void setTanggal(LocalDate tanggal) { this.tanggal = tanggal; }
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public double getSaldoSetelah() { return saldoSetelah; }
    public void setSaldoSetelah(double saldoSetelah) { this.saldoSetelah = saldoSetelah; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
    public int getIdUserInput() { return idUserInput; }
    public void setIdUserInput(int idUserInput) { this.idUserInput = idUserInput; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
}
