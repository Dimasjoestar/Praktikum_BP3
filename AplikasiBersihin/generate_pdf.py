from fpdf import FPDF
import os

class PDF(FPDF):
    def header(self):
        self.set_font('Arial', 'B', 15)
        self.set_text_color(0, 128, 128) # Teal color
        self.cell(0, 10, 'Struktur Program Aplikasi Bersihin', 0, 1, 'C')
        self.line(10, 22, 200, 22)
        self.ln(10)

    def footer(self):
        self.set_y(-15)
        self.set_font('Arial', 'I', 8)
        self.set_text_color(128)
        self.cell(0, 10, f'Halaman {self.page_no()}', 0, 0, 'C')

    def chapter_title(self, title):
        self.set_font('Arial', 'B', 12)
        self.set_text_color(0, 50, 50)
        self.cell(0, 10, title, 0, 1, 'L')
        self.ln(2)

    def chapter_subtitle(self, subtitle):
        self.set_font('Arial', 'B', 11)
        self.set_text_color(0, 100, 100)
        self.cell(0, 8, subtitle, 0, 1, 'L')

    def chapter_body(self, body):
        self.set_font('Arial', '', 11)
        self.set_text_color(0)
        # Using multi_cell for text wrapping
        self.multi_cell(0, 6, body)
        self.ln(4)

pdf = PDF()
pdf.add_page()

pdf.chapter_title('1. Pendahuluan')
pdf.chapter_body('Aplikasi Bersihin menggunakan arsitektur MVVM (Model-View-ViewModel) yang direkomendasikan secara resmi oleh Google. Antarmuka penggunanya (UI) dibangun secara modern menggunakan Android Jetpack Compose.')

pdf.chapter_title('2. Struktur Utama (Package Com.pab.aplikasibersihin)')

pdf.chapter_subtitle('A. Paket data (Pusat Data)')
pdf.chapter_body('Bertanggung jawab atas pengelolaan data, baik dari database lokal maupun logika bisnis tingkat bawah.\n'
                 '- database: Berisi AppDatabase.kt (konfigurasi Room DB lokal SQLite) dan Converters.kt (untuk mengonversi tipe data kompleks).\n'
                 '- dao (Data Access Object): Kumpulan antarmuka (seperti UserDao, OrderDao) yang berisi perintah SQL untuk membaca/menulis ke tabel tertentu.\n'
                 '- entity: Kelas cetak biru (blueprint) dari tabel-tabel di database (misal: UserEntity merepresentasikan tabel users).\n'
                 '- model: Berisi Enums.kt yang menyimpan nilai-nilai statis berulang seperti peran (ADMIN, CUSTOMER).\n'
                 '- repository: Sebagai jembatan penghubung (mediator) antara Database dan ViewModel. Tempat menaruh logika seperti login dan register (AuthRepository.kt).')

pdf.chapter_subtitle('B. Paket ui (Antarmuka / Tampilan)')
pdf.chapter_body('Berisi semua kode visual (layar) yang berinteraksi langsung dengan pengguna.\n'
                 '- admin: Halaman-halaman khusus panel Admin (Kelola Layanan, Promo, Pesanan, Laporan).\n'
                 '- auth: Halaman Login dan Registrasi.\n'
                 '- components: Elemen desain kecil yang dipakai berulang-ulang di berbagai layar (tombol gradient, pelacak status, progress bar, dialog konfirmasi).\n'
                 '- customer: Layar khusus pelanggan (Membuat Order, Riwayat, Profil, Promo).\n'
                 '- officer: Layar khusus petugas lapangan (Daftar Penjemputan, Detail Tugas).\n'
                 '- navigation: Mengatur perpindahan (routing) antar-layar menggunakan Navigasi Compose (NavGraph.kt).\n'
                 '- theme: Mengatur warna primer, jenis huruf (typography), dan gaya dasar UI aplikasi.')

pdf.chapter_subtitle('C. Paket utils (Alat Bantu)')
pdf.chapter_body('Kumpulan fungsi utilitas ringan yang bisa dipanggil dari mana saja.\n'
                 '- Mengatur format kalender/tanggal (DateFormatter.kt).\n'
                 '- Mengatur format Rupiah (CurrencyFormatter.kt).\n'
                 '- Kalkulasi Level & XP (XpCalculator.kt).\n'
                 '- Pengingat Sesi Login Pengguna (SessionManager.kt).')

pdf.chapter_subtitle('D. Paket viewmodel (Penghubung Data & Layar)')
pdf.chapter_body('Berisi kelas-kelas pengatur logika interaksi layar (seperti AuthViewModel, CustomerViewModel).\n'
                 'Fungsinya: Menyimpan state (data sementara layar) agar tidak hilang saat rotasi layar HP, serta meminta data dari Repository untuk ditampilkan di layar (UI).')

pdf.chapter_title('3. Alur Kerja (Work Flow) Singkat')
pdf.chapter_body('Data dan interaksi dalam aplikasi mengalir dengan siklus berikut:\n'
                 'Layar (UI) -> ViewModel -> Repository -> DAO -> Database Lokal (Entity)\n\n'
                 'Penjelasan Alur:\n'
                 '1. Pengguna memencet tombol di Layar (UI).\n'
                 '2. Layar memanggil fungsi di ViewModel.\n'
                 '3. ViewModel meminta data ke Repository.\n'
                 '4. Repository memanggil fungsi SQL di DAO.\n'
                 '5. DAO menyimpan/mengambil data dari Database (Entity) lalu mengembalikannya secara berjenjang ke Layar.')

out_path = os.path.join(os.getcwd(), 'Struktur_Aplikasi_Bersihin.pdf')
# Using latin-1 encoding explicitly handling any special characters if present, though text is ascii standard.
pdf.output(out_path, 'F')
print(f"PDF saved successfully to: {out_path}")
