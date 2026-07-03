package com.pab.moneytracker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.pab.moneytracker.model.AuthProvider
import com.pab.moneytracker.model.Category
import com.pab.moneytracker.model.Transaction
import com.pab.moneytracker.model.TransactionType
import com.pab.moneytracker.model.User
import java.security.MessageDigest

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "money_tracker.db"
        private const val DATABASE_VERSION = 2

        // Users table
        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_EMAIL = "email"
        const val COLUMN_USER_PASSWORD = "password"
        const val COLUMN_USER_FIRST_NAME = "first_name"
        const val COLUMN_USER_LAST_NAME = "last_name"
        const val COLUMN_USER_AUTH_PROVIDER = "auth_provider"

        // Transactions table
        const val TABLE_TRANSACTIONS = "transactions"
        const val COLUMN_ID = "id"
        const val COLUMN_TRANSACTION_USER_ID = "user_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AMOUNT = "amount"
        const val COLUMN_TYPE = "type"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_DATE = "date"
        const val COLUMN_CREATED_AT = "created_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create users table
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_EMAIL TEXT UNIQUE NOT NULL,
                $COLUMN_USER_PASSWORD TEXT,
                $COLUMN_USER_FIRST_NAME TEXT NOT NULL,
                $COLUMN_USER_LAST_NAME TEXT NOT NULL,
                $COLUMN_USER_AUTH_PROVIDER TEXT NOT NULL DEFAULT 'EMAIL'
            )
        """.trimIndent()
        db.execSQL(createUsersTable)

        // Create transactions table
        val createTransactionsTable = """
            CREATE TABLE $TABLE_TRANSACTIONS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TRANSACTION_USER_ID INTEGER NOT NULL,
                $COLUMN_TITLE TEXT NOT NULL,
                $COLUMN_AMOUNT REAL NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL,
                $COLUMN_CATEGORY TEXT NOT NULL DEFAULT 'OTHER',
                $COLUMN_DATE INTEGER NOT NULL,
                $COLUMN_CREATED_AT INTEGER NOT NULL,
                FOREIGN KEY ($COLUMN_TRANSACTION_USER_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID) ON DELETE CASCADE
            )
        """.trimIndent()
        db.execSQL(createTransactionsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSACTIONS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    // ==================== USER OPERATIONS ====================

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun registerUser(email: String, password: String, firstName: String, lastName: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_EMAIL, email.lowercase())
            put(COLUMN_USER_PASSWORD, hashPassword(password))
            put(COLUMN_USER_FIRST_NAME, firstName)
            put(COLUMN_USER_LAST_NAME, lastName)
            put(COLUMN_USER_AUTH_PROVIDER, AuthProvider.EMAIL.name)
        }
        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun loginUser(email: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?",
            arrayOf(email.lowercase(), hashPassword(password)),
            null, null, null
        )

        var user: User? = null
        cursor.use {
            if (it.moveToFirst()) {
                user = User(
                    id = it.getLong(it.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    email = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    firstName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME)),
                    lastName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME)),
                    authProvider = AuthProvider.valueOf(it.getString(it.getColumnIndexOrThrow(COLUMN_USER_AUTH_PROVIDER)))
                )
            }
        }
        db.close()
        return user
    }

    fun loginOrCreateSocialUser(email: String, firstName: String, lastName: String, provider: AuthProvider): User? {
        val db = writableDatabase
        
        // Check if user exists
        val cursor = db.query(
            TABLE_USERS, null,
            "$COLUMN_USER_EMAIL = ?",
            arrayOf(email.lowercase()),
            null, null, null
        )

        var user: User? = null
        cursor.use {
            if (it.moveToFirst()) {
                user = User(
                    id = it.getLong(it.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    email = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    firstName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME)),
                    lastName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME)),
                    authProvider = AuthProvider.valueOf(it.getString(it.getColumnIndexOrThrow(COLUMN_USER_AUTH_PROVIDER)))
                )
            }
        }

        if (user == null) {
            // Create new user
            val values = ContentValues().apply {
                put(COLUMN_USER_EMAIL, email.lowercase())
                put(COLUMN_USER_PASSWORD, "")
                put(COLUMN_USER_FIRST_NAME, firstName)
                put(COLUMN_USER_LAST_NAME, lastName)
                put(COLUMN_USER_AUTH_PROVIDER, provider.name)
            }
            val id = db.insert(TABLE_USERS, null, values)
            if (id != -1L) {
                user = User(id, email.lowercase(), "", firstName, lastName, provider)
            }
        }

        db.close()
        return user
    }

    fun getUserById(userId: Long): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS, null,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId.toString()),
            null, null, null
        )

        var user: User? = null
        cursor.use {
            if (it.moveToFirst()) {
                user = User(
                    id = it.getLong(it.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    email = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_EMAIL)),
                    firstName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_FIRST_NAME)),
                    lastName = it.getString(it.getColumnIndexOrThrow(COLUMN_USER_LAST_NAME)),
                    authProvider = AuthProvider.valueOf(it.getString(it.getColumnIndexOrThrow(COLUMN_USER_AUTH_PROVIDER)))
                )
            }
        }
        db.close()
        return user
    }

    fun updateUserName(userId: Long, firstName: String, lastName: String): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_FIRST_NAME, firstName)
            put(COLUMN_USER_LAST_NAME, lastName)
        }
        val result = db.update(TABLE_USERS, values, "$COLUMN_USER_ID = ?", arrayOf(userId.toString()))
        db.close()
        return result
    }

    fun deleteUserAndData(userId: Long): Boolean {
        val db = writableDatabase
        db.delete(TABLE_TRANSACTIONS, "$COLUMN_TRANSACTION_USER_ID = ?", arrayOf(userId.toString()))
        val result = db.delete(TABLE_USERS, "$COLUMN_USER_ID = ?", arrayOf(userId.toString()))
        db.close()
        return result > 0
    }

    fun isEmailExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USER_ID),
            "$COLUMN_USER_EMAIL = ?",
            arrayOf(email.lowercase()),
            null, null, null
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // ==================== TRANSACTION OPERATIONS ====================

    fun insertTransaction(transaction: Transaction): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TRANSACTION_USER_ID, transaction.userId)
            put(COLUMN_TITLE, transaction.title)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_TYPE, transaction.type.name)
            put(COLUMN_CATEGORY, transaction.category.name)
            put(COLUMN_DATE, transaction.date)
            put(COLUMN_CREATED_AT, transaction.createdAt)
        }
        val id = db.insert(TABLE_TRANSACTIONS, null, values)
        db.close()
        return id
    }

    fun updateTransaction(transaction: Transaction): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, transaction.title)
            put(COLUMN_AMOUNT, transaction.amount)
            put(COLUMN_TYPE, transaction.type.name)
            put(COLUMN_CATEGORY, transaction.category.name)
            put(COLUMN_DATE, transaction.date)
        }
        val result = db.update(TABLE_TRANSACTIONS, values, "$COLUMN_ID = ?", arrayOf(transaction.id.toString()))
        db.close()
        return result
    }

    fun getAllTransactions(userId: Long): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TRANSACTIONS,
            null,
            "$COLUMN_TRANSACTION_USER_ID = ?",
            arrayOf(userId.toString()),
            null, null,
            "$COLUMN_DATE DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(COLUMN_ID))
                val tUserId = it.getLong(it.getColumnIndexOrThrow(COLUMN_TRANSACTION_USER_ID))
                val title = it.getString(it.getColumnIndexOrThrow(COLUMN_TITLE))
                val amount = it.getDouble(it.getColumnIndexOrThrow(COLUMN_AMOUNT))
                val typeStr = it.getString(it.getColumnIndexOrThrow(COLUMN_TYPE))
                val categoryStr = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val date = it.getLong(it.getColumnIndexOrThrow(COLUMN_DATE))
                val createdAt = it.getLong(it.getColumnIndexOrThrow(COLUMN_CREATED_AT))

                val type = TransactionType.valueOf(typeStr)
                val category = try {
                    Category.valueOf(categoryStr)
                } catch (e: Exception) {
                    Category.OTHER
                }
                transactions.add(Transaction(id, tUserId, title, amount, type, category, date, createdAt))
            }
        }
        db.close()
        return transactions
    }

    fun deleteTransaction(id: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_TRANSACTIONS, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun deleteAllTransactions(userId: Long): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_TRANSACTIONS, "$COLUMN_TRANSACTION_USER_ID = ?", arrayOf(userId.toString()))
        db.close()
        return result
    }

    fun getTotalBalance(userId: Long): Double {
        val db = readableDatabase
        var totalIncome = 0.0
        var totalExpense = 0.0

        val incomeCursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COLUMN_TRANSACTION_USER_ID = ? AND $COLUMN_TYPE = ?",
            arrayOf(userId.toString(), TransactionType.INCOME.name)
        )
        incomeCursor.use {
            if (it.moveToFirst()) totalIncome = it.getDouble(0)
        }

        val expenseCursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COLUMN_TRANSACTION_USER_ID = ? AND $COLUMN_TYPE = ?",
            arrayOf(userId.toString(), TransactionType.EXPENSE.name)
        )
        expenseCursor.use {
            if (it.moveToFirst()) totalExpense = it.getDouble(0)
        }

        db.close()
        return totalIncome - totalExpense
    }

    fun getTotalIncome(userId: Long): Double {
        val db = readableDatabase
        var total = 0.0
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COLUMN_TRANSACTION_USER_ID = ? AND $COLUMN_TYPE = ?",
            arrayOf(userId.toString(), TransactionType.INCOME.name)
        )
        cursor.use {
            if (it.moveToFirst()) total = it.getDouble(0)
        }
        db.close()
        return total
    }

    fun getTotalExpense(userId: Long): Double {
        val db = readableDatabase
        var total = 0.0
        val cursor = db.rawQuery(
            "SELECT SUM($COLUMN_AMOUNT) FROM $TABLE_TRANSACTIONS WHERE $COLUMN_TRANSACTION_USER_ID = ? AND $COLUMN_TYPE = ?",
            arrayOf(userId.toString(), TransactionType.EXPENSE.name)
        )
        cursor.use {
            if (it.moveToFirst()) total = it.getDouble(0)
        }
        db.close()
        return total
    }
}
