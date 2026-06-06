// File: model/KategoriPemasukan.java
package model;

public class KategoriPemasukan {
    private int idKategoriPemasukan;
    private String namaKategori;
    private boolean isSpp;
    private double nominalDefault;

    public int getIdKategoriPemasukan() { return idKategoriPemasukan; }
    public void setIdKategoriPemasukan(int idKategoriPemasukan) { this.idKategoriPemasukan = idKategoriPemasukan; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public boolean isIsSpp() { return isSpp; }
    public boolean isSpp() { return isSpp; }
    public void setIsSpp(boolean isSpp) { this.isSpp = isSpp; }
    public void setSpp(boolean spp) { isSpp = spp; }
    public double getNominalDefault() { return nominalDefault; }
    public void setNominalDefault(double nominalDefault) { this.nominalDefault = nominalDefault; }
}
