package com.pab.ecolifehub.data

import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.*

object DataSource {

    // Menu Items for HomeActivity
    fun getMenuItems(): List<MenuItem> = listOf(
        MenuItem(
            id = 1,
            title = "Edukasi Limbah",
            description = "Pelajari tentang berbagai jenis limbah dan cara pengelolaannya",
            iconResId = R.drawable.ic_waste
        ),
        MenuItem(
            id = 2,
            title = "Gaya Hidup Hijau",
            description = "Tips dan trik hidup ramah lingkungan",
            iconResId = R.drawable.ic_tips
        ),
        MenuItem(
            id = 3,
            title = "Eco Challenge",
            description = "Tantangan untuk bumi yang lebih baik",
            iconResId = R.drawable.ic_challenge
        ),
        MenuItem(
            id = 4,
            title = "Kebiasaan Harian",
            description = "Checklist aktivitas eco-friendly harian",
            iconResId = R.drawable.ic_habit
        ),
        MenuItem(
            id = 5,
            title = "Tentang Aplikasi",
            description = "Informasi tentang EcoLife Hub",
            iconResId = R.drawable.ic_about
        )
    )

    // Waste Categories
    fun getWasteCategories(): List<WasteCategory> = listOf(
        WasteCategory(
            id = 1,
            name = "Limbah Plastik",
            description = "Sampah berbahan dasar plastik seperti botol, kantong, dan kemasan",
            iconResId = R.drawable.ic_plastic
        ),
        WasteCategory(
            id = 2,
            name = "Limbah Organik",
            description = "Sampah yang berasal dari makhluk hidup dan dapat terurai alami",
            iconResId = R.drawable.ic_organic
        ),
        WasteCategory(
            id = 3,
            name = "Limbah Elektronik",
            description = "Sampah dari peralatan elektronik yang sudah tidak terpakai",
            iconResId = R.drawable.ic_electronic
        ),
        WasteCategory(
            id = 4,
            name = "Limbah Kertas",
            description = "Sampah berbahan dasar kertas seperti koran, kardus, dan dokumen",
            iconResId = R.drawable.ic_paper
        ),
        WasteCategory(
            id = 5,
            name = "Limbah B3",
            description = "Limbah Bahan Berbahaya dan Beracun yang memerlukan penanganan khusus",
            iconResId = R.drawable.ic_b3
        ),
        WasteCategory(
            id = 6,
            name = "Limbah Kaca",
            description = "Sampah berbahan dasar kaca seperti botol, cermin, dan jendela pecah",
            iconResId = R.drawable.ic_glass
        ),
        WasteCategory(
            id = 7,
            name = "Limbah Tekstil",
            description = "Sampah dari bahan kain seperti pakaian bekas, karpet, dan gorden",
            iconResId = R.drawable.ic_textile
        ),
        WasteCategory(
            id = 8,
            name = "Limbah Logam",
            description = "Sampah berbahan logam seperti kaleng, besi tua, dan aluminium",
            iconResId = R.drawable.ic_metal
        ),
        WasteCategory(
            id = 9,
            name = "Limbah Medis",
            description = "Sampah dari fasilitas kesehatan seperti jarum suntik dan obat kadaluarsa",
            iconResId = R.drawable.ic_medical
        ),
        WasteCategory(
            id = 10,
            name = "Limbah Konstruksi",
            description = "Sampah dari aktivitas pembangunan seperti beton, kayu, dan puing",
            iconResId = R.drawable.ic_construction
        )
    )

    // Waste Details
    fun getPlasticWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Plastik",
        description = "Limbah plastik adalah sampah yang berasal dari produk berbahan dasar plastik. " +
                "Plastik merupakan polimer sintetis yang sangat sulit terurai secara alami. " +
                "Dibutuhkan waktu 100-1000 tahun untuk plastik terurai di alam. " +
                "Contoh limbah plastik: botol minuman, kantong plastik, sedotan, kemasan makanan, dan mainan plastik.",
        impact = "• Mencemari tanah dan air\n" +
                "• Membahayakan kehidupan laut (ikan, penyu, burung laut)\n" +
                "• Mikroplastik masuk ke rantai makanan manusia\n" +
                "• Menyumbat saluran air dan menyebabkan banjir\n" +
                "• Mengeluarkan gas beracun jika dibakar",
        tips = listOf(
            "Kurangi penggunaan plastik sekali pakai",
            "Bawa tas belanja sendiri",
            "Gunakan botol minum yang dapat dipakai ulang",
            "Pilah sampah plastik untuk didaur ulang",
            "Pilih produk dengan kemasan ramah lingkungan",
            "Hindari sedotan plastik, gunakan sedotan stainless atau bambu"
        ),
        imageResId = R.drawable.ic_plastic
    )

    fun getOrganicWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Organik",
        description = "Limbah organik adalah sampah yang berasal dari makhluk hidup dan dapat terurai " +
                "secara alami oleh mikroorganisme. Proses penguraian membutuhkan waktu beberapa minggu " +
                "hingga beberapa bulan. Contoh limbah organik: sisa makanan, kulit buah, daun kering, " +
                "ranting pohon, kotoran hewan, dan sisa tanaman.",
        impact = "• Menghasilkan gas metana yang berkontribusi pada pemanasan global\n" +
                "• Dapat menjadi sarang bakteri dan penyakit jika tidak dikelola\n" +
                "• Menimbulkan bau tidak sedap\n" +
                "• Menarik lalat dan hewan pengerat",
        tips = listOf(
            "Olah menjadi kompos untuk pupuk tanaman",
            "Pisahkan sampah organik dari sampah lainnya",
            "Gunakan komposter atau lubang biopori",
            "Kurangi sisa makanan dengan merencanakan porsi",
            "Manfaatkan sebagai pakan ternak jika memungkinkan",
            "Buat eco-enzyme dari limbah organik"
        ),
        imageResId = R.drawable.ic_organic
    )

    fun getElectronicWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Elektronik",
        description = "Limbah elektronik (e-waste) adalah sampah dari peralatan elektronik yang sudah " +
                "tidak terpakai atau rusak. E-waste mengandung berbagai logam berat dan bahan kimia " +
                "berbahaya. Contoh: smartphone, laptop, TV, kulkas, AC, baterai, dan kabel.",
        impact = "• Mengandung logam berat berbahaya (timbal, merkuri, kadmium)\n" +
                "• Mencemari tanah dan air tanah\n" +
                "• Menimbulkan risiko kesehatan bagi pekerja informal\n" +
                "• Membuang-buang material berharga yang bisa didaur ulang\n" +
                "• Melepaskan dioksin jika dibakar",
        tips = listOf(
            "Donasikan elektronik yang masih berfungsi",
            "Serahkan ke pusat daur ulang e-waste resmi",
            "Jangan membuang baterai sembarangan",
            "Pertimbangkan untuk memperbaiki sebelum membeli baru",
            "Beli produk elektronik berkualitas dan tahan lama",
            "Ikuti program trade-in dari produsen elektronik"
        ),
        imageResId = R.drawable.ic_electronic
    )

    fun getPaperWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Kertas",
        description = "Limbah kertas adalah sampah berbahan dasar kertas. Kertas terbuat dari serat " +
                "selulosa kayu dan relatif mudah didaur ulang. Namun, produksi kertas dari pohon " +
                "menyebabkan deforestasi. Contoh: koran, majalah, kardus, buku, tisu, dan kemasan kertas.",
        impact = "• Produksi kertas menyebabkan penebangan hutan\n" +
                "• Proses produksi membutuhkan banyak air dan energi\n" +
                "• Menghasilkan gas metana saat membusuk di TPA\n" +
                "• Limbah tinta dan pemutih mencemari air",
        tips = listOf(
            "Gunakan kertas bolak-balik",
            "Pilih produk kertas daur ulang",
            "Kurangi penggunaan tisu, gunakan serbet kain",
            "Digitalisasi dokumen jika memungkinkan",
            "Pisahkan kertas untuk didaur ulang",
            "Hindari mencetak jika tidak perlu"
        ),
        imageResId = R.drawable.ic_paper
    )

    fun getB3WasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah B3 (Bahan Berbahaya dan Beracun)",
        description = "Limbah B3 adalah limbah yang mengandung bahan berbahaya dan beracun yang dapat " +
                "membahayakan kesehatan manusia dan lingkungan. Limbah ini memerlukan penanganan khusus " +
                "dan tidak boleh dibuang sembarangan. Contoh: cat, pestisida, obat kadaluarsa, " +
                "baterai, oli bekas, lampu neon, dan produk pembersih kimia.",
        impact = "• Sangat berbahaya bagi kesehatan manusia\n" +
                "• Mencemari tanah dan air dalam jangka panjang\n" +
                "• Dapat menyebabkan keracunan dan penyakit serius\n" +
                "• Merusak ekosistem dan membunuh organisme\n" +
                "• Beberapa bersifat karsinogenik (penyebab kanker)",
        tips = listOf(
            "Jangan membuang ke tempat sampah biasa",
            "Serahkan ke fasilitas pengolahan limbah B3",
            "Simpan di wadah tertutup rapat",
            "Jangan mencampur dengan limbah lainnya",
            "Kembalikan obat kadaluarsa ke apotek",
            "Gunakan produk ramah lingkungan sebagai alternatif"
        ),
        imageResId = R.drawable.ic_b3
    )

    fun getGlassWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Kaca",
        description = "Limbah kaca adalah sampah berbahan dasar kaca yang berasal dari berbagai produk. " +
                "Kaca terbuat dari pasir silika dan dapat didaur ulang tanpa kehilangan kualitas. " +
                "Namun, kaca membutuhkan waktu sangat lama untuk terurai di alam (lebih dari 1 juta tahun). " +
                "Contoh: botol minuman, cermin, jendela pecah, lampu, dan peralatan makan kaca.",
        impact = "• Dapat melukai manusia dan hewan jika pecah\n" +
                "• Membutuhkan waktu sangat lama untuk terurai\n" +
                "• Produksi kaca baru membutuhkan energi tinggi\n" +
                "• Mencemari tanah jika tidak dikelola dengan baik\n" +
                "• Berbahaya bagi satwa liar yang terperangkap",
        tips = listOf(
            "Pisahkan kaca berdasarkan warna untuk daur ulang",
            "Bungkus kaca pecah dengan koran sebelum dibuang",
            "Gunakan kembali botol kaca untuk wadah penyimpanan",
            "Serahkan ke bank sampah atau pengepul kaca",
            "Hindari membuang kaca bersama sampah organik",
            "Kreasikan kaca bekas menjadi kerajinan tangan"
        ),
        imageResId = R.drawable.ic_glass
    )

    fun getTextileWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Tekstil",
        description = "Limbah tekstil adalah sampah dari bahan kain dan serat yang mencakup pakaian bekas, " +
                "karpet, gorden, sprei, dan produk tekstil lainnya. Industri fashion adalah salah satu " +
                "penyumbang polusi terbesar di dunia. Tekstil sintetis seperti polyester membutuhkan " +
                "ratusan tahun untuk terurai. Contoh: pakaian rusak, sepatu bekas, tas kain, dan selimut.",
        impact = "• Menyumbang 10% emisi karbon global\n" +
                "• Tekstil sintetis melepaskan mikroplastik saat dicuci\n" +
                "• Pewarna tekstil mencemari sungai dan tanah\n" +
                "• Menghabiskan lahan TPA yang terbatas\n" +
                "• Proses produksi membutuhkan banyak air",
        tips = listOf(
            "Donasikan pakaian layak pakai ke panti asuhan",
            "Jual atau tukar pakaian bekas di thrift shop",
            "Ubah pakaian lama menjadi kain lap atau kerajinan",
            "Pilih pakaian berkualitas yang tahan lama",
            "Cuci pakaian dengan air dingin untuk hemat energi",
            "Belanja fashion secara bijak dan tidak impulsif"
        ),
        imageResId = R.drawable.ic_textile
    )

    fun getMetalWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Logam",
        description = "Limbah logam adalah sampah berbahan dasar logam yang sangat berharga untuk didaur ulang. " +
                "Logam dapat didaur ulang berkali-kali tanpa kehilangan kualitas. Daur ulang logam menghemat " +
                "energi hingga 95% dibanding menambang logam baru. Contoh: kaleng minuman, kaleng makanan, " +
                "kawat, pipa, besi tua, dan aluminium foil.",
        impact = "• Menambang logam baru merusak habitat alami\n" +
                "• Proses penambangan menghasilkan emisi tinggi\n" +
                "• Logam berat dapat mencemari tanah dan air\n" +
                "• Membuang logam di TPA membuang sumber daya berharga\n" +
                "• Proses pembuatan logam baru sangat boros energi",
        tips = listOf(
            "Kumpulkan dan jual ke pengepul barang bekas",
            "Pisahkan logam dari sampah lainnya",
            "Cuci kaleng bekas sebelum didaur ulang",
            "Manfaatkan kaleng bekas untuk pot tanaman",
            "Serahkan ke bank sampah atau drop point",
            "Pilih produk dengan kemasan logam yang bisa didaur ulang"
        ),
        imageResId = R.drawable.ic_metal
    )

    fun getMedicalWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Medis",
        description = "Limbah medis adalah sampah yang dihasilkan dari aktivitas pelayanan kesehatan yang " +
                "bersifat infeksius dan berbahaya. Limbah ini memerlukan penanganan khusus karena dapat " +
                "menularkan penyakit. Contoh: jarum suntik, perban bekas, botol infus, obat kadaluarsa, " +
                "sarung tangan medis, dan alat rapid test.",
        impact = "• Risiko penularan penyakit infeksius tinggi\n" +
                "• Jarum suntik berbahaya bagi pemulung dan petugas kebersihan\n" +
                "• Pencemaran lingkungan dan sumber air\n" +
                "• Resistensi antibiotik dari obat yang dibuang\n" +
                "• Bahaya radiasi dari limbah radioaktif medis",
        tips = listOf(
            "Kembalikan obat kadaluarsa ke apotek atau puskesmas",
            "Gunakan wadah khusus untuk jarum suntik bekas",
            "Jangan membuang di tempat sampah rumah tangga",
            "Pisahkan masker bekas dari sampah lainnya",
            "Gunting masker sebelum dibuang agar tidak disalahgunakan",
            "Hubungi dinas kesehatan untuk pembuangan limbah medis"
        ),
        imageResId = R.drawable.ic_medical
    )

    fun getConstructionWasteDetail(): WasteDetail = WasteDetail(
        name = "Limbah Konstruksi",
        description = "Limbah konstruksi adalah sampah yang dihasilkan dari aktivitas pembangunan, renovasi, " +
                "dan pembongkaran bangunan. Limbah ini seringkali bervolume besar dan beragam jenisnya. " +
                "Sebagian besar material konstruksi sebenarnya bisa didaur ulang atau digunakan kembali. " +
                "Contoh: beton pecah, batu bata, kayu bekas, keramik, genteng, dan pipa PVC.",
        impact = "• Memenuhi lahan TPA dengan cepat\n" +
                "• Debu konstruksi mencemari udara\n" +
                "• Limbah cat dan pelarut mencemari tanah\n" +
                "• Material berbahaya seperti asbes mengancam kesehatan\n" +
                "• Penambangan material baru merusak lingkungan",
        tips = listOf(
            "Rencanakan pembangunan untuk meminimalkan limbah",
            "Gunakan kembali material yang masih layak",
            "Jual atau donasikan material bekas yang bagus",
            "Pisahkan material berdasarkan jenisnya",
            "Gunakan jasa pengelola limbah konstruksi resmi",
            "Pilih material ramah lingkungan dan tahan lama"
        ),
        imageResId = R.drawable.ic_construction
    )

    // Green Tips
    fun getGreenTips(): List<GreenTip> = listOf(
        GreenTip(
            id = 1,
            title = "Hemat Listrik",
            description = "Matikan lampu dan peralatan elektronik saat tidak digunakan. Gunakan lampu LED yang hemat energi.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 2,
            title = "Hemat Air",
            description = "Tutup keran saat menyikat gigi. Gunakan air bekas cucian untuk menyiram tanaman.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 3,
            title = "Kurangi Plastik Sekali Pakai",
            description = "Bawa tas belanja, botol minum, dan wadah makanan sendiri. Tolak sedotan plastik.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 4,
            title = "Naik Transportasi Umum",
            description = "Kurangi emisi karbon dengan naik bus, kereta, atau sepeda. Ajak teman untuk berbagi kendaraan.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 5,
            title = "Tanam Pohon",
            description = "Mulai urban gardening di rumah. Tanam sayuran sendiri atau pohon yang bermanfaat.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 6,
            title = "Pilah Sampah",
            description = "Pisahkan sampah organik, plastik, kertas, dan limbah berbahaya untuk memudahkan daur ulang.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 7,
            title = "Konsumsi Lokal",
            description = "Beli produk lokal untuk mengurangi jejak karbon dari transportasi. Dukung petani setempat.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 8,
            title = "Hindari Food Waste",
            description = "Rencanakan menu mingguan, belanja sesuai kebutuhan, dan olah sisa makanan menjadi kompos.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 9,
            title = "Gunakan Produk Ramah Lingkungan",
            description = "Pilih produk dengan label eco-friendly, biodegradable, atau kemasan minimal.",
            iconResId = R.drawable.ic_tips
        ),
        GreenTip(
            id = 10,
            title = "Edukasi Orang Sekitar",
            description = "Bagikan pengetahuan tentang gaya hidup hijau kepada keluarga dan teman.",
            iconResId = R.drawable.ic_tips
        )
    )

    // Eco Challenges
    fun getEcoChallenges(): List<EcoChallenge> = listOf(
        EcoChallenge(
            id = 1,
            title = "30 Hari Tanpa Plastik",
            description = "Tantangan untuk tidak menggunakan plastik sekali pakai selama 30 hari penuh. " +
                    "Bawa tas belanja sendiri, gunakan botol minum reusable, dan tolak kemasan plastik.",
            benefit = "Mengurangi sampah plastik hingga 2kg per bulan dan menginspirasi orang lain untuk ikut.",
            difficulty = "Menengah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 2,
            title = "Zero Food Waste Week",
            description = "Selama satu minggu, pastikan tidak ada makanan yang terbuang. Rencanakan " +
                    "menu, belanja sesuai kebutuhan, dan olah sisa makanan.",
            benefit = "Menghemat uang dan mengurangi emisi metana dari sampah makanan yang membusuk.",
            difficulty = "Mudah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 3,
            title = "Bike to Work Challenge",
            description = "Gunakan sepeda untuk pergi bekerja atau beraktivitas selama 2 minggu. " +
                    "Sehat untuk tubuh dan ramah untuk lingkungan!",
            benefit = "Mengurangi emisi karbon, menghemat BBM, dan meningkatkan kesehatan tubuh.",
            difficulty = "Menengah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 4,
            title = "Plant a Tree",
            description = "Tanam minimal satu pohon di halaman rumah, lingkungan sekitar, atau ikut " +
                    "program penanaman pohon komunitas.",
            benefit = "Satu pohon dapat menyerap 22kg CO2 per tahun dan menyediakan oksigen untuk 2 orang.",
            difficulty = "Mudah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 5,
            title = "Electricity Free Hour",
            description = "Matikan semua peralatan elektronik selama 1 jam setiap hari. Gunakan waktu " +
                    "untuk membaca, berbincang, atau berjalan-jalan.",
            benefit = "Menghemat listrik dan mengurangi ketergantungan pada gadget.",
            difficulty = "Mudah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 6,
            title = "Composting Champion",
            description = "Buat kompos dari sampah organik dapur selama sebulan. Gunakan komposter " +
                    "atau metode lubang biopori.",
            benefit = "Mengurangi sampah ke TPA dan menghasilkan pupuk gratis untuk tanaman.",
            difficulty = "Menengah",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 7,
            title = "Eco-Friendly Shopping",
            description = "Selama sebulan, belanja hanya produk dengan kemasan ramah lingkungan atau " +
                    "beli di toko bulk (tanpa kemasan).",
            benefit = "Mengurangi sampah kemasan dan mendukung bisnis berkelanjutan.",
            difficulty = "Sulit",
            iconResId = R.drawable.ic_challenge
        ),
        EcoChallenge(
            id = 8,
            title = "Water Conservation Week",
            description = "Kurangi penggunaan air sebanyak 20% selama seminggu. Mandi lebih cepat, " +
                    "tutup keran saat tidak digunakan.",
            benefit = "Menghemat air bersih dan mengurangi tagihan air bulanan.",
            difficulty = "Mudah",
            iconResId = R.drawable.ic_challenge
        )
    )

    // Daily Habits
    fun getDailyHabits(): List<DailyHabit> = listOf(
        DailyHabit(1, "Matikan lampu saat tidak digunakan"),
        DailyHabit(2, "Bawa botol minum sendiri"),
        DailyHabit(3, "Bawa tas belanja sendiri"),
        DailyHabit(4, "Pisahkan sampah organik dan anorganik"),
        DailyHabit(5, "Mandi maksimal 5 menit"),
        DailyHabit(6, "Cabut charger dari stop kontak"),
        DailyHabit(7, "Gunakan transportasi umum atau sepeda"),
        DailyHabit(8, "Hindari makanan kemasan plastik"),
        DailyHabit(9, "Habiskan makanan tanpa sisa"),
        DailyHabit(10, "Siram tanaman dengan air bekas cucian"),
        DailyHabit(11, "Matikan AC saat keluar ruangan"),
        DailyHabit(12, "Gunakan kertas bolak-balik")
    )
}
