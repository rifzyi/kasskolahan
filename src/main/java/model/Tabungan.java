package model;

import java.sql.Date;

public class Tabungan {
    private int idTabungan;
    private int idSiswa;
    private Date tanggal;
    private String jenis;
    private double nominal;
    private double saldoAkhir;
    private String keterangan;

    public Tabungan() {}

    public Tabungan(int idTabungan, int idSiswa, Date tanggal, String jenis, double nominal, double saldoAkhir, String keterangan) {
        this.idTabungan = idTabungan;
        this.idSiswa = idSiswa;
        this.tanggal = tanggal;
        this.jenis = jenis;
        this.nominal = nominal;
        this.saldoAkhir = saldoAkhir;
        this.keterangan = keterangan;
    }

    public int getIdTabungan() { return idTabungan; }
    public void setIdTabungan(int idTabungan) { this.idTabungan = idTabungan; }
    public int getIdSiswa() { return idSiswa; }
    public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
    public Date getTanggal() { return tanggal; }
    public void setTanggal(Date tanggal) { this.tanggal = tanggal; }
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }
    public double getNominal() { return nominal; }
    public void setNominal(double nominal) { this.nominal = nominal; }
    public double getSaldoAkhir() { return saldoAkhir; }
    public void setSaldoAkhir(double saldoAkhir) { this.saldoAkhir = saldoAkhir; }
    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
