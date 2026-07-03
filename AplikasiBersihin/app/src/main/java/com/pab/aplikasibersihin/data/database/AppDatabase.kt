package com.pab.aplikasibersihin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pab.aplikasibersihin.data.database.dao.*
import com.pab.aplikasibersihin.data.database.entity.*
import com.pab.aplikasibersihin.data.model.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        ServiceEntity::class,
        OrderEntity::class,
        TransactionEntity::class,
        RewardEntity::class,
        PromoEntity::class,
        PickupEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun serviceDao(): ServiceDao
    abstract fun orderDao(): OrderDao
    abstract fun transactionDao(): TransactionDao
    abstract fun rewardDao(): RewardDao
    abstract fun promoDao(): PromoDao
    abstract fun pickupDao(): PickupDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bersihin_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(
                        database.userDao(),
                        database.serviceDao(),
                        database.promoDao(),
                        database.rewardDao()
                    )
                }
            }
        }

        suspend fun populateDatabase(
            userDao: UserDao,
            serviceDao: ServiceDao,
            promoDao: PromoDao,
            rewardDao: RewardDao
        ) {
            // Seed Users
            userDao.insertUser(
                UserEntity(
                    name = "Admin Bersih.in",
                    email = "admin@bersihin.com",
                    passwordHash = "admin123", // plaintext or simple match for local
                    phone = "081234567890",
                    address = "Kantor Pusat Bersih.in",
                    role = UserRole.ADMIN
                )
            )
            userDao.insertUser(
                UserEntity(
                    name = "Dimas Joestar (Petugas)",
                    email = "petugas1@bersihin.com",
                    passwordHash = "petugas123",
                    phone = "082345678901",
                    address = "Pos Lapangan Sudirman",
                    role = UserRole.OFFICER
                )
            )
            userDao.insertUser(
                UserEntity(
                    name = "Budi Pelanggan",
                    email = "customer@bersihin.com",
                    passwordHash = "customer123",
                    phone = "083456789012",
                    address = "Jl. Melati No. 45, Jakarta",
                    role = UserRole.CUSTOMER,
                    xp = 600, // Starts at SILVER
                    level = "SILVER"
                )
            )

            // Seed Services
            serviceDao.insertService(
                ServiceEntity(
                    name = "Cuci Reguler",
                    description = "Cuci basah & lipat rapi. Selesai dalam 3 hari.",
                    pricePerKg = 7000.0,
                    estimationDays = 3,
                    category = "Kiloan"
                )
            )
            serviceDao.insertService(
                ServiceEntity(
                    name = "Cuci Express",
                    description = "Layanan cepat kilat. Selesai dalam 1 hari.",
                    pricePerKg = 12000.0,
                    estimationDays = 1,
                    category = "Kiloan"
                )
            )
            serviceDao.insertService(
                ServiceEntity(
                    name = "Cuci Setrika",
                    description = "Cuci, keringkan, disetrika licin & wangi. Selesai 3 hari.",
                    pricePerKg = 10000.0,
                    estimationDays = 3,
                    category = "Kiloan"
                )
            )
            serviceDao.insertService(
                ServiceEntity(
                    name = "Setrika Saja",
                    description = "Hanya setrika pakaian lipat rapi. Selesai 2 hari.",
                    pricePerKg = 5000.0,
                    estimationDays = 2,
                    category = "Kiloan"
                )
            )
            serviceDao.insertService(
                ServiceEntity(
                    name = "Cuci Sepatu Premium",
                    description = "Dry clean & cuci detailing sepatu segala jenis.",
                    pricePerKg = 30000.0,
                    estimationDays = 3,
                    category = "Satuan"
                )
            )
            serviceDao.insertService(
                ServiceEntity(
                    name = "Cuci Bed Cover",
                    description = "Cuci bed cover ukuran single/double. Selesai 4 hari.",
                    pricePerKg = 25000.0,
                    estimationDays = 4,
                    category = "Satuan"
                )
            )

            // Seed Promos
            val oneMonth = 30L * 24L * 60L * 60L * 1000L
            promoDao.insertPromo(
                PromoEntity(
                    name = "Diskon Pembukaan",
                    description = "Diskon 10% untuk semua pelanggan tanpa minimal order.",
                    discountPercent = 0.10,
                    minOrderAmount = 0.0,
                    validUntil = System.currentTimeMillis() + oneMonth
                )
            )
            promoDao.insertPromo(
                PromoEntity(
                    name = "Cuci Hemat Akhir Bulan",
                    description = "Potongan 15% khusus dengan transaksi minimal Rp 50.000.",
                    discountPercent = 0.15,
                    minOrderAmount = 50000.0,
                    validUntil = System.currentTimeMillis() + oneMonth
                )
            )

            // Seed Rewards
            rewardDao.insertReward(
                RewardEntity(
                    name = "Voucher Diskon Rp 10.000",
                    description = "Dapatkan potongan langsung Rp 10.000 untuk transaksi berikutnya.",
                    xpCost = 150,
                    type = "DISCOUNT"
                )
            )
            rewardDao.insertReward(
                RewardEntity(
                    name = "Free Ongkir Pickup/Delivery",
                    description = "Gunakan voucher ini untuk layanan antar-jemput gratis.",
                    xpCost = 300,
                    type = "FREE_SHIPPING"
                )
            )
            rewardDao.insertReward(
                RewardEntity(
                    name = "Free Cuci Sepatu 1 Pasang",
                    description = "Tukarkan 600 XP untuk layanan cuci sepatu gratis.",
                    xpCost = 600,
                    type = "FREE_ITEM"
                )
            )
        }
    }
}
