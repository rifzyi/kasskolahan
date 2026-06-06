package model;

import java.sql.Timestamp;

public class Siswa {
    private int idSiswa;
    private String nis;
    private String namaSiswa;
    private int idKelas;
    private String jenisKelamin;
    private String alamat;
    private Timestamp createdAt;

    public Siswa() {}

    public Siswa(int idSiswa, String nis, String namaSiswa, int idKelas, String jenisKelamin, String alamat, Timestamp createdAt) {
        this.idSiswa = idSiswa;
        this.nis = nis;
        this.namaSiswa = namaSiswa;
        this.idKelas = idKelas;
        this.jenisKelamin = jenisKelamin;
        this.alamat = alamat;
        this.createdAt = createdAt;
    }

    public int getIdSiswa() { return idSiswa; }
    public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
    public String getNis() { return nis; }
    public void setNis(String nis) { this.nis = nis; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
    public int getIdKelas() { return idKelas; }
    public void setIdKelas(int idKelas) { this.idKelas = idKelas; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
