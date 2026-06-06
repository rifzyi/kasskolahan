package model;

import java.sql.Timestamp;

public class AuditLog {
    private int id;
    private int userId;
    private String aksi;
    private String tabelTarget;
    private String dataLama;
    private String dataBaru;
    private Timestamp waktu;

    public AuditLog() {}

    public AuditLog(int id, int userId, String aksi, String tabelTarget, String dataLama, String dataBaru, Timestamp waktu) {
        this.id = id;
        this.userId = userId;
        this.aksi = aksi;
        this.tabelTarget = tabelTarget;
        this.dataLama = dataLama;
        this.dataBaru = dataBaru;
        this.waktu = waktu;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getAksi() { return aksi; }
    public void setAksi(String aksi) { this.aksi = aksi; }
    public String getTabelTarget() { return tabelTarget; }
    public void setTabelTarget(String tabelTarget) { this.tabelTarget = tabelTarget; }
    public String getDataLama() { return dataLama; }
    public void setDataLama(String dataLama) { this.dataLama = dataLama; }
    public String getDataBaru() { return dataBaru; }
    public void setDataBaru(String dataBaru) { this.dataBaru = dataBaru; }
    public Timestamp getWaktu() { return waktu; }
    public void setWaktu(Timestamp waktu) { this.waktu = waktu; }
}
