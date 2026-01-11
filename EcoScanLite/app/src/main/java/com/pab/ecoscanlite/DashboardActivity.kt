package com.pab.ecoscanlite

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.pab.ecoscanlite.adapter.LimbahAdapter
import com.pab.ecoscanlite.model.Limbah

/**
 * DashboardActivity - Halaman utama setelah login
 * Features:
 * - Menampilkan welcome message dengan username dari Intent
 * - RecyclerView berisi 10 jenis limbah (hardcoded)
 * - Navigasi ke DetailActivity saat item diklik
 * - Navigasi ke AboutActivity via button
 */
class DashboardActivity : AppCompatActivity() {

    // UI Components
    private lateinit var tvWelcome: TextView
    private lateinit var rvLimbah: RecyclerView
    private lateinit var btnAbout: MaterialButton

    // Data
    private lateinit var limbahList: ArrayList<Limbah>
    private lateinit var limbahAdapter: LimbahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        
        setupWindowInsets()
        initViews()
        setupWelcomeMessage()
        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        tvWelcome = findViewById(R.id.tvWelcome)
        rvLimbah = findViewById(R.id.rvLimbah)
        btnAbout = findViewById(R.id.btnAbout)
    }

    private fun setupWelcomeMessage() {
        // Get username from Intent
        val username = intent.getStringExtra("EXTRA_USERNAME") ?: "User"
        tvWelcome.text = getString(R.string.welcome_message, username)
    }

    private fun setupRecyclerView() {
        // Initialize hardcoded data
        limbahList = getLimbahData()

        // Setup adapter with click listener
        limbahAdapter = LimbahAdapter(limbahList) { limbah ->
            navigateToDetail(limbah)
        }

        // Setup RecyclerView
        rvLimbah.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = limbahAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupClickListeners() {
        btnAbout.setOnClickListener {
            navigateToAbout()
        }
    }

    /**
     * Hardcoded data 10 jenis limbah
     */
    private fun getLimbahData(): ArrayList<Limbah> {
        return arrayListOf(
            Limbah(
                id = 1,
                nama = "Limbah Plastik",
                imageResId = R.drawable.ic_plastik,
                deskripsi = "Limbah plastik adalah sampah yang berasal dari bahan plastik seperti botol minuman, kantong plastik, kemasan makanan, sedotan, dan berbagai produk plastik sekali pakai lainnya. Plastik membutuhkan waktu ratusan tahun untuk terurai secara alami dan dapat mencemari tanah serta lautan.",
                tips = "• Kurangi penggunaan plastik sekali pakai\n• Gunakan tas belanja yang bisa dipakai ulang\n• Kumpulkan dan cuci plastik bekas sebelum disetor ke bank sampah\n• Pisahkan berdasarkan jenis plastik (PET, HDPE, dll)\n• Hindari membakar plastik karena menghasilkan gas beracun"
            ),
            Limbah(
                id = 2,
                nama = "Limbah Kertas",
                imageResId = R.drawable.ic_kertas,
                deskripsi = "Limbah kertas meliputi koran bekas, majalah, kardus, kertas HVS, buku tulis, dan kemasan kertas lainnya. Kertas merupakan bahan yang dapat didaur ulang menjadi produk kertas baru, namun harus dalam kondisi kering dan bersih.",
                tips = "• Pisahkan kertas dari sampah basah agar tidak rusak\n• Lipat kardus dengan rapi untuk menghemat ruang\n• Jangan campurkan kertas berlaminasi atau kertas foto\n• Setor ke bank sampah atau pengepul kertas bekas\n• Gunakan kertas bekas untuk catatan atau kerajinan"
            ),
            Limbah(
                id = 3,
                nama = "Limbah Elektronik",
                imageResId = R.drawable.ic_elektronik,
                deskripsi = "Limbah elektronik atau e-waste adalah perangkat elektronik yang sudah tidak terpakai seperti handphone, laptop, TV, charger, kabel, dan peralatan rumah tangga elektronik. E-waste mengandung bahan berbahaya seperti timbal dan merkuri yang perlu penanganan khusus.",
                tips = "• Jangan buang elektronik ke tempat sampah biasa\n• Setor ke tempat pengumpulan e-waste resmi\n• Hapus data pribadi sebelum menyerahkan perangkat\n• Pertimbangkan untuk memperbaiki sebelum membuang\n• Donasikan jika masih berfungsi dengan baik"
            ),
            Limbah(
                id = 4,
                nama = "Limbah Organik",
                imageResId = R.drawable.ic_organik,
                deskripsi = "Limbah organik adalah sampah yang berasal dari makhluk hidup dan mudah terurai secara alami. Contohnya sisa makanan, kulit buah, sayuran busuk, daun kering, dan sisa tanaman. Limbah organik dapat diolah menjadi kompos yang bermanfaat untuk pertanian.",
                tips = "• Pisahkan dari sampah anorganik\n• Jadikan kompos dengan metode sederhana di rumah\n• Gunakan sebagai pupuk organik untuk tanaman\n• Hindari membuang ke saluran air\n• Olah menjadi pakan ternak jika memungkinkan"
            ),
            Limbah(
                id = 5,
                nama = "Limbah Kaca",
                imageResId = R.drawable.ic_kaca,
                deskripsi = "Limbah kaca termasuk botol kaca, gelas pecah, cermin, dan kaca jendela. Kaca dapat didaur ulang hingga 100% tanpa kehilangan kualitas. Namun, kaca pecah perlu penanganan hati-hati karena dapat menyebabkan luka.",
                tips = "• Bungkus kaca pecah dengan koran atau kain tebal\n• Beri label 'PECAH BELAH' pada wadah penyimpanan\n• Setor botol kaca ke pengepul barang bekas\n• Pisahkan kaca bening dan berwarna\n• Jangan campurkan dengan keramik atau porselen"
            ),
            Limbah(
                id = 6,
                nama = "Limbah Logam",
                imageResId = R.drawable.ic_logam,
                deskripsi = "Limbah logam meliputi kaleng minuman, kaleng makanan, paku berkarat, besi bekas, aluminium, dan kawat. Logam merupakan material bernilai tinggi yang dapat didaur ulang berulang kali tanpa kehilangan sifatnya.",
                tips = "• Kumpulkan kaleng bekas dan bersihkan\n• Pisahkan berdasarkan jenis logam (besi, aluminium, tembaga)\n• Jual ke pengepul logam atau bank sampah\n• Ratakan kaleng untuk menghemat ruang\n• Hati-hati dengan logam tajam atau berkarat"
            ),
            Limbah(
                id = 7,
                nama = "Limbah Tekstil",
                imageResId = R.drawable.ic_tekstil,
                deskripsi = "Limbah tekstil adalah pakaian bekas, kain perca, seprai, handuk, dan produk tekstil lainnya yang tidak terpakai. Industri fashion adalah salah satu penyumbang polusi terbesar, sehingga pengelolaan tekstil bekas sangat penting.",
                tips = "• Donasikan pakaian layak pakai ke yang membutuhkan\n• Jadikan kain bekas sebagai lap atau majun\n• Upcycle menjadi produk kerajinan baru\n• Setor ke bank sampah yang menerima tekstil\n• Hindari membuang tekstil ke TPA"
            ),
            Limbah(
                id = 8,
                nama = "Limbah Medis Rumah Tangga",
                imageResId = R.drawable.ic_medis,
                deskripsi = "Limbah medis rumah tangga termasuk masker bekas, obat kadaluarsa, perban, jarum suntik insulin, dan alat tes kesehatan. Limbah ini berpotensi menyebarkan penyakit dan memerlukan penanganan khusus.",
                tips = "• Pisahkan dari sampah rumah tangga biasa\n• Masukkan jarum ke wadah tahan tusuk\n• Buang obat kadaluarsa ke apotek atau puskesmas\n• Gunting masker sebelum dibuang\n• Bungkus rapat dengan plastik berlabel"
            ),
            Limbah(
                id = 9,
                nama = "Limbah B3",
                imageResId = R.drawable.ic_b3,
                deskripsi = "Limbah B3 (Bahan Berbahaya dan Beracun) meliputi baterai, cat, pestisida, pembersih kimia, lampu neon, dan oli bekas. Limbah ini sangat berbahaya bagi kesehatan dan lingkungan jika tidak ditangani dengan benar.",
                tips = "• JANGAN buang ke tempat sampah biasa\n• Simpan di wadah tertutup rapat dan berlabel\n• Bawa ke TPS B3 atau tempat pengumpulan khusus\n• Jangan mencampur berbagai jenis B3\n• Hubungi dinas lingkungan hidup untuk informasi"
            ),
            Limbah(
                id = 10,
                nama = "Limbah Minyak Jelantah",
                imageResId = R.drawable.ic_minyak,
                deskripsi = "Minyak jelantah adalah minyak goreng bekas yang sudah tidak layak digunakan. Jika dibuang sembarangan, minyak jelantah dapat mencemari tanah dan air. Namun, minyak ini dapat diolah menjadi biodiesel yang ramah lingkungan.",
                tips = "• Jangan buang ke saluran air atau tanah\n• Tampung dalam botol plastik bekas\n• Biarkan dingin sebelum disimpan\n• Setor ke pengepul minyak jelantah\n• Dapat dijual untuk produksi biodiesel atau sabun"
            )
        )
    }

    private fun navigateToDetail(limbah: Limbah) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_NAMA", limbah.nama)
            putExtra("EXTRA_IMAGE_RES_ID", limbah.imageResId)
            putExtra("EXTRA_DESKRIPSI", limbah.deskripsi)
            putExtra("EXTRA_TIPS", limbah.tips)
        }
        startActivity(intent)
    }

    private fun navigateToAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}
