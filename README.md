# SKM - Sistem Keuangan Madrasah

Aplikasi desktop Java Swing untuk manajemen keuangan madrasah/sekolah menggunakan Java 17, FlatLaf, JDBC murni, MySQL 8, dan Maven.

## Fitur

- Login user dengan BCrypt.
- Manajemen kelas dan siswa.
- Pemasukan, termasuk logika SPP bulanan per siswa.
- Pengeluaran.
- Tabungan siswa dengan validasi saldo tarik.
- Dashboard KPI bulanan dan transaksi terbaru.
- Laporan periode dengan export CSV dan PDF.
- Pengaturan profil sekolah dan manajemen user.
- Audit log untuk aktivitas mutasi data.

## Menjalankan

1. Buat database dengan menjalankan `database/kas_sekolah.sql` di MySQL 8.
2. Pastikan koneksi default adalah `localhost:3306`, database `kas_sekolah`, user `root`, password kosong.
3. Opsional: buat `config.properties` di root proyek:

```properties
db.url=jdbc:mysql://localhost:3306/kas_sekolah?useSSL=false&serverTimezone=Asia/Jakarta&characterEncoding=utf8
db.user=root
db.password=
```

4. Jalankan aplikasi:

```bash
mvn clean compile
mvn exec:java
```

## Login Default

- Username: `admin`
- Password: `admin123`

## Struktur

Source utama berada di `src/main/java` dengan package `model`, `controller`, `form`, dan `util`.
