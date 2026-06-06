// File: model/Siswa.java
package model;

public class Siswa {
    private int idSiswa;
    private String nis;
    private String namaSiswa;
    private Integer idKelas;
    private String jenisKelamin;
    private String alamat;
    private boolean aktif = true;
    private String namaKelas;

    public int getIdSiswa() { return idSiswa; }
    public void setIdSiswa(int idSiswa) { this.idSiswa = idSiswa; }
    public String getNis() { return nis; }
    public void setNis(String nis) { this.nis = nis; }
    public String getNamaSiswa() { return namaSiswa; }
    public void setNamaSiswa(String namaSiswa) { this.namaSiswa = namaSiswa; }
    public Integer getIdKelas() { return idKelas; }
    public void setIdKelas(Integer idKelas) { this.idKelas = idKelas; }
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }
    public String getNamaKelas() { return namaKelas; }
    public void setNamaKelas(String namaKelas) { this.namaKelas = namaKelas; }
}
