CREATE DATABASE IF NOT EXISTS kas_sekolah CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE kas_sekolah;

DROP TABLE IF EXISTS audit_log, tabungan, spp_tagihan, pengeluaran, pemasukan, siswa, chart_of_account, kategori_pengeluaran, kategori_pemasukan, kelas, pengaturan, users;

CREATE TABLE users (id INT AUTO_INCREMENT PRIMARY KEY, nama VARCHAR(100) NOT NULL, username VARCHAR(50) NOT NULL UNIQUE, password_hash VARCHAR(255) NOT NULL, role ENUM('Super Admin','Kasir') NOT NULL DEFAULT 'Kasir', created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE kelas (id_kelas INT AUTO_INCREMENT PRIMARY KEY, kode_kelas VARCHAR(20) NOT NULL UNIQUE, nama_kelas VARCHAR(100) NOT NULL);
CREATE TABLE siswa (id_siswa INT AUTO_INCREMENT PRIMARY KEY, nis VARCHAR(40) NOT NULL UNIQUE, nama_siswa VARCHAR(120) NOT NULL, id_kelas INT, jenis_kelamin ENUM('L','P') NOT NULL, alamat TEXT, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CONSTRAINT fk_siswa_kelas FOREIGN KEY (id_kelas) REFERENCES kelas(id_kelas) ON UPDATE CASCADE ON DELETE SET NULL);
CREATE TABLE kategori_pemasukan (id INT AUTO_INCREMENT PRIMARY KEY, nama_kategori VARCHAR(100) NOT NULL, nominal_default DECIMAL(15,2) DEFAULT 0);
CREATE TABLE kategori_pengeluaran (id INT AUTO_INCREMENT PRIMARY KEY, nama_kategori VARCHAR(100) NOT NULL);
CREATE TABLE chart_of_account (id INT AUTO_INCREMENT PRIMARY KEY, kode_akun VARCHAR(20) NOT NULL UNIQUE, nama_akun VARCHAR(100) NOT NULL, tipe ENUM('ASET','KEWAJIBAN','MODAL','PENDAPATAN','BEBAN') NOT NULL);
CREATE TABLE pemasukan (id_pemasukan INT AUTO_INCREMENT PRIMARY KEY, tanggal DATE NOT NULL, id_siswa INT NULL, id_kategori_pemasukan INT NOT NULL, nominal DECIMAL(15,2) NOT NULL, keterangan TEXT, created_by INT, CONSTRAINT fk_pemasukan_siswa FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa) ON UPDATE CASCADE ON DELETE SET NULL, CONSTRAINT fk_pemasukan_kategori FOREIGN KEY (id_kategori_pemasukan) REFERENCES kategori_pemasukan(id), CONSTRAINT fk_pemasukan_user FOREIGN KEY (created_by) REFERENCES users(id));
CREATE TABLE pengeluaran (id_pengeluaran INT AUTO_INCREMENT PRIMARY KEY, tanggal DATE NOT NULL, id_kategori_pengeluaran INT NOT NULL, nominal DECIMAL(15,2) NOT NULL, keterangan TEXT, created_by INT, CONSTRAINT fk_pengeluaran_kategori FOREIGN KEY (id_kategori_pengeluaran) REFERENCES kategori_pengeluaran(id), CONSTRAINT fk_pengeluaran_user FOREIGN KEY (created_by) REFERENCES users(id));
CREATE TABLE spp_tagihan (id INT AUTO_INCREMENT PRIMARY KEY, id_siswa INT NOT NULL, bulan INT NOT NULL, tahun INT NOT NULL, nominal DECIMAL(15,2) NOT NULL, status ENUM('BELUM_LUNAS','LUNAS') NOT NULL DEFAULT 'BELUM_LUNAS', tanggal_bayar DATE NULL, UNIQUE KEY uk_spp (id_siswa, bulan, tahun), CONSTRAINT fk_spp_siswa FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa) ON DELETE CASCADE);
CREATE TABLE tabungan (id_tabungan INT AUTO_INCREMENT PRIMARY KEY, id_siswa INT NOT NULL, tanggal DATE NOT NULL, jenis ENUM('SETOR','TARIK') NOT NULL, nominal DECIMAL(15,2) NOT NULL, saldo_akhir DECIMAL(15,2) NOT NULL, keterangan TEXT, CONSTRAINT fk_tabungan_siswa FOREIGN KEY (id_siswa) REFERENCES siswa(id_siswa) ON DELETE CASCADE);
CREATE TABLE pengaturan (id INT AUTO_INCREMENT PRIMARY KEY, nama_sekolah VARCHAR(150) NOT NULL, alamat TEXT, telepon VARCHAR(30), email VARCHAR(100), kepala_sekolah VARCHAR(100), bendahara VARCHAR(100), logo_path VARCHAR(255));
CREATE TABLE audit_log (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, aksi VARCHAR(100) NOT NULL, tabel_target VARCHAR(100), data_lama TEXT NULL, data_baru TEXT NULL, waktu TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL);

INSERT INTO users(nama, username, password_hash, role) VALUES ('Administrator', 'admin', '$2a$10$HVmpNtYwgFfMxyLECAh.B.Y5EAhlddCQzOOlCUSz2hpPVGYcMjG8.', 'Super Admin');
INSERT INTO kelas(kode_kelas, nama_kelas) VALUES ('VII-A','Kelas VII A'),('VII-B','Kelas VII B'),('VIII-A','Kelas VIII A'),('IX-A','Kelas IX A');
INSERT INTO siswa(nis,nama_siswa,id_kelas,jenis_kelamin,alamat) VALUES ('2026001','Ahmad Fauzi',1,'L','Jl. Merdeka 1'),('2026002','Siti Aminah',2,'P','Jl. Melati 2'),('2026003','Budi Santoso',3,'L','Jl. Kenanga 3');
INSERT INTO kategori_pemasukan(nama_kategori, nominal_default) VALUES ('SPP', 250000),('Dana BOS',0),('Donasi',0),('Sewa Fasilitas',0);
INSERT INTO kategori_pengeluaran(nama_kategori) VALUES ('Listrik'),('Internet'),('ATK'),('Gaji Guru/Staf'),('Operasional');
INSERT INTO chart_of_account(kode_akun,nama_akun,tipe) VALUES ('101','Kas','ASET'),('102','Bank','ASET'),('401','Pendapatan SPP','PENDAPATAN'),('501','Biaya Operasional','BEBAN');
INSERT INTO spp_tagihan(id_siswa,bulan,tahun,nominal,status) VALUES (1,1,2026,250000,'BELUM_LUNAS'),(2,1,2026,250000,'BELUM_LUNAS'),(3,1,2026,250000,'LUNAS');
INSERT INTO pengaturan(nama_sekolah, alamat, telepon, email, kepala_sekolah, bendahara, logo_path) VALUES ('Madrasah Contoh', 'Jl. Pendidikan No. 1', '021-123456', 'info@madrasah.sch.id', 'Drs. Kepala Sekolah', 'Bendahara Madrasah', '');
