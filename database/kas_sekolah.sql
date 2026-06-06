-- ============================================================
-- DATABASE: kas_sekolah
-- Engine: InnoDB | Charset: utf8mb4_unicode_ci
-- ============================================================

CREATE DATABASE IF NOT EXISTS kas_sekolah
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kas_sekolah;

-- TABLE 1: users
CREATE TABLE users (
  id_user     INT AUTO_INCREMENT PRIMARY KEY,
  nama        VARCHAR(100) NOT NULL,
  username    VARCHAR(50)  NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,  -- BCrypt hash, bukan plain text
  role        ENUM('admin','operator') NOT NULL DEFAULT 'operator',
  created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Akun default: username=admin, password=admin123 (BCrypt)
INSERT INTO users (nama, username, password, role) VALUES
  ('Administrator', 'admin',
   '$2a$12$2eHT3DJmgEsJq7nZ9LkXZeZ8Xv5L3Y6Qr2wN1mP4kH7sA9bC0dEf', 'admin');

-- TABLE 2: pengaturan
CREATE TABLE pengaturan (
  id_pengaturan   INT AUTO_INCREMENT PRIMARY KEY,
  nama_sekolah    VARCHAR(200) NOT NULL DEFAULT 'Madrasah Saya',
  alamat          TEXT,
  telepon         VARCHAR(20),
  kepala_sekolah  VARCHAR(100),
  bendahara       VARCHAR(100),
  logo_path       VARCHAR(255),
  updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                  ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO pengaturan (nama_sekolah) VALUES ('SKM Madrasah');

-- TABLE 3: kelas
CREATE TABLE kelas (
  id_kelas    INT AUTO_INCREMENT PRIMARY KEY,
  kode_kelas  VARCHAR(10) NOT NULL UNIQUE,
  nama_kelas  VARCHAR(50) NOT NULL
);

-- TABLE 4: siswa
CREATE TABLE siswa (
  id_siswa      INT AUTO_INCREMENT PRIMARY KEY,
  nis           VARCHAR(20) NOT NULL UNIQUE,
  nama_siswa    VARCHAR(100) NOT NULL,
  id_kelas      INT,
  jenis_kelamin ENUM('L','P') NOT NULL,
  alamat        TEXT,
  aktif         TINYINT(1) DEFAULT 1,
  FOREIGN KEY (id_kelas) REFERENCES kelas(id_kelas)
    ON DELETE SET NULL
);

-- TABLE 5: kategori_pemasukan
CREATE TABLE kategori_pemasukan (
  id_kategori_pemasukan INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori         VARCHAR(100) NOT NULL,
  is_spp                TINYINT(1) DEFAULT 0,
  nominal_default       DECIMAL(15,2) DEFAULT 0
);
INSERT INTO kategori_pemasukan (nama_kategori, is_spp, nominal_default) VALUES
  ('SPP Bulanan', 1, 200000),
  ('Uang Gedung', 0, 0),
  ('Infaq',       0, 0),
  ('Lain-lain',   0, 0);

-- TABLE 6: pemasukan
CREATE TABLE pemasukan (
  id_pemasukan          INT AUTO_INCREMENT PRIMARY KEY,
  tanggal               DATE NOT NULL,
  id_siswa              INT,
  id_kategori_pemasukan INT NOT NULL,
  nominal               DECIMAL(15,2) NOT NULL,
  keterangan            TEXT,
  bulan_spp             VARCHAR(7),  -- format YYYY-MM, diisi jika is_spp=true
  id_user_input         INT NOT NULL,
  created_at            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_siswa)
    REFERENCES siswa(id_siswa),
  FOREIGN KEY (id_kategori_pemasukan)
    REFERENCES kategori_pemasukan(id_kategori_pemasukan),
  FOREIGN KEY (id_user_input)
    REFERENCES users(id_user)
);

-- TABLE 7: kategori_pengeluaran
CREATE TABLE kategori_pengeluaran (
  id_kategori_pengeluaran INT AUTO_INCREMENT PRIMARY KEY,
  nama_kategori           VARCHAR(100) NOT NULL
);
INSERT INTO kategori_pengeluaran (nama_kategori) VALUES
  ('Gaji Guru'), ('ATK'), ('Listrik & Air'),
  ('Pemeliharaan'), ('Lain-lain');

-- TABLE 8: pengeluaran
CREATE TABLE pengeluaran (
  id_pengeluaran          INT AUTO_INCREMENT PRIMARY KEY,
  tanggal                 DATE NOT NULL,
  id_kategori_pengeluaran INT NOT NULL,
  nominal                 DECIMAL(15,2) NOT NULL,
  keterangan              TEXT,
  id_user_input           INT NOT NULL,
  created_at              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_kategori_pengeluaran)
    REFERENCES kategori_pengeluaran(id_kategori_pengeluaran),
  FOREIGN KEY (id_user_input)
    REFERENCES users(id_user)
);

-- TABLE 9: tabungan_siswa
CREATE TABLE tabungan_siswa (
  id_tabungan   INT AUTO_INCREMENT PRIMARY KEY,
  id_siswa      INT NOT NULL,
  tanggal       DATE NOT NULL,
  jenis         ENUM('SETOR','TARIK') NOT NULL,
  nominal       DECIMAL(15,2) NOT NULL,
  saldo_setelah DECIMAL(15,2) NOT NULL,
  keterangan    TEXT,
  id_user_input INT NOT NULL,
  created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_siswa)
    REFERENCES siswa(id_siswa),
  FOREIGN KEY (id_user_input)
    REFERENCES users(id_user)
);

-- TABLE 10: audit_log
CREATE TABLE audit_log (
  id_log    BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_user   INT NOT NULL,
  aksi      VARCHAR(100) NOT NULL,
  detail    TEXT,
  waktu     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- INDEX performa
CREATE INDEX idx_pemasukan_tanggal ON pemasukan(tanggal);
CREATE INDEX idx_pemasukan_siswa   ON pemasukan(id_siswa);
CREATE INDEX idx_pengeluaran_tgl   ON pengeluaran(tanggal);
CREATE INDEX idx_tabungan_siswa    ON tabungan_siswa(id_siswa);
CREATE INDEX idx_audit_waktu       ON audit_log(waktu);
