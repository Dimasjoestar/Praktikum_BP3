package com.pab.moneytracker

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pab.moneytracker.adapter.TransactionAdapter
import com.pab.moneytracker.database.DatabaseHelper
import com.pab.moneytracker.databinding.ActivityMainBinding
import com.pab.moneytracker.databinding.DialogAddTransactionBinding
import com.pab.moneytracker.model.Category
import com.pab.moneytracker.model.Transaction
import com.pab.moneytracker.model.TransactionType
import com.pab.moneytracker.utils.NumberTextWatcher
import com.pab.moneytracker.utils.SessionManager
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private lateinit var transactionAdapter: TransactionAdapter
    
    private val currencyFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    private val profileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            updateNavHeader()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply dark mode before super.onCreate() to prevent flicker
        val prefs = getSharedPreferences("MoneyTrackerPrefs", MODE_PRIVATE)
        val isDark = prefs.getBoolean("darkMode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
        
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)
        sessionManager = SessionManager(this)

        setupRecyclerView()
        setupNavigationDrawer()
        setupClickListeners()
        refreshData()
    }

    private fun setupRecyclerView() {
        transactionAdapter = TransactionAdapter(
            hideNominal = sessionManager.isHideNominal,
            onEditClick = { transaction -> showEditTransactionDialog(transaction) },
            onDeleteClick = { transaction -> showDeleteConfirmation(transaction) }
        )
        binding.rvTransactions.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = transactionAdapter
        }
    }

    private fun setupNavigationDrawer() {
        updateNavHeader()

        // Setup switches
        val menu = binding.navigationView.menu
        val darkModeItem = menu.findItem(R.id.nav_dark_mode)
        val hideNominalItem = menu.findItem(R.id.nav_hide_nominal)

        val darkModeSwitch = darkModeItem.actionView as? SwitchCompat
        val hideNominalSwitch = hideNominalItem.actionView as? SwitchCompat

        darkModeSwitch?.isChecked = sessionManager.isDarkMode
        hideNominalSwitch?.isChecked = sessionManager.isHideNominal

        darkModeSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (sessionManager.isDarkMode != isChecked) {
                sessionManager.isDarkMode = isChecked
                recreate() // Recreate activity to apply theme
            }
        }

        hideNominalSwitch?.setOnCheckedChangeListener { _, isChecked ->
            sessionManager.isHideNominal = isChecked
            transactionAdapter.setHideNominal(isChecked)
            refreshBalanceDisplay()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    profileLauncher.launch(Intent(this, ProfileActivity::class.java))
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.nav_logout -> {
                    showLogoutConfirmation()
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun updateNavHeader() {
        val headerView = binding.navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val ivUserAvatar = headerView.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.ivUserAvatar)

        tvUserName.text = "${sessionManager.userFirstName} ${sessionManager.userLastName}"
        tvUserEmail.text = sessionManager.userEmail

        // Load profile photo
        val photoUri = sessionManager.profilePhotoUri
        if (!photoUri.isNullOrEmpty()) {
            try {
                ivUserAvatar.setImageURI(android.net.Uri.parse(photoUri))
            } catch (e: Exception) {
                ivUserAvatar.setImageResource(R.drawable.ic_default_avatar)
            }
        } else {
            ivUserAvatar.setImageResource(R.drawable.ic_default_avatar)
        }
    }

    private fun setupClickListeners() {
        binding.btnMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.fabAddTransaction.setOnClickListener {
            showAddTransactionDialog()
        }
    }

    private fun showAddTransactionDialog() {
        showTransactionDialog(null)
    }

    private fun showEditTransactionDialog(transaction: Transaction) {
        showTransactionDialog(transaction)
    }

    private fun showTransactionDialog(transaction: Transaction?) {
        val dialogBinding = DialogAddTransactionBinding.inflate(LayoutInflater.from(this))
        val isEdit = transaction != null

        // Setup category dropdown
        val categories = Category.entries.map { it.displayName }
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)
        dialogBinding.actvCategory.setAdapter(categoryAdapter)

        // Setup number formatting
        dialogBinding.etAmount.addTextChangedListener(NumberTextWatcher(dialogBinding.etAmount))

        // Setup date picker
        var selectedDate = transaction?.date ?: System.currentTimeMillis()
        dialogBinding.etDate.setText(dateFormat.format(Date(selectedDate)))
        
        dialogBinding.etDate.setOnClickListener {
            showDatePicker(selectedDate) { date ->
                selectedDate = date
                dialogBinding.etDate.setText(dateFormat.format(Date(date)))
            }
        }
        dialogBinding.tilDate.setEndIconOnClickListener {
            showDatePicker(selectedDate) { date ->
                selectedDate = date
                dialogBinding.etDate.setText(dateFormat.format(Date(date)))
            }
        }

        // Pre-fill for edit
        if (isEdit) {
            dialogBinding.etTitle.setText(transaction!!.title)
            dialogBinding.etAmount.setText(currencyFormat.format(transaction.amount.toLong()))
            dialogBinding.actvCategory.setText(transaction.category.displayName, false)
            if (transaction.type == TransactionType.INCOME) {
                dialogBinding.rbIncome.isChecked = true
            } else {
                dialogBinding.rbExpense.isChecked = true
            }
        } else {
            dialogBinding.actvCategory.setText(Category.OTHER.displayName, false)
        }

        val title = if (isEdit) getString(R.string.edit_transaction) else getString(R.string.add_transaction)
        val positiveText = if (isEdit) getString(R.string.update) else getString(R.string.save)

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setView(dialogBinding.root)
            .setPositiveButton(positiveText, null)
            .setNegativeButton(getString(R.string.cancel), null)
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val titleText = dialogBinding.etTitle.text.toString().trim()
                val amountStr = dialogBinding.etAmount.text.toString().trim()
                val categoryText = dialogBinding.actvCategory.text.toString()

                // Validation
                if (titleText.isEmpty()) {
                    dialogBinding.tilTitle.error = getString(R.string.error_empty_title)
                    return@setOnClickListener
                } else {
                    dialogBinding.tilTitle.error = null
                }

                if (amountStr.isEmpty()) {
                    dialogBinding.tilAmount.error = getString(R.string.error_empty_amount)
                    return@setOnClickListener
                } else {
                    dialogBinding.tilAmount.error = null
                }

                val amount = NumberTextWatcher.getCleanNumber(amountStr)
                if (amount <= 0) {
                    dialogBinding.tilAmount.error = getString(R.string.error_invalid_amount)
                    return@setOnClickListener
                } else {
                    dialogBinding.tilAmount.error = null
                }

                val type = if (dialogBinding.rbIncome.isChecked) TransactionType.INCOME else TransactionType.EXPENSE
                val category = Category.entries.find { it.displayName == categoryText } ?: Category.OTHER

                val newTransaction = Transaction(
                    id = transaction?.id ?: 0,
                    userId = sessionManager.userId,
                    title = titleText,
                    amount = amount,
                    type = type,
                    category = category,
                    date = selectedDate,
                    createdAt = transaction?.createdAt ?: System.currentTimeMillis()
                )

                val result = if (isEdit) {
                    databaseHelper.updateTransaction(newTransaction)
                } else {
                    databaseHelper.insertTransaction(newTransaction).toInt()
                }

                if (result > 0) {
                    val message = if (isEdit) getString(R.string.transaction_updated) else getString(R.string.transaction_added)
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    refreshData()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, getString(R.string.transaction_add_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    private fun showDatePicker(currentDate: Long, onDateSelected: (Long) -> Unit) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentDate

        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                onDateSelected(calendar.timeInMillis)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun showDeleteConfirmation(transaction: Transaction) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.confirm_delete_transaction, transaction.title))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                databaseHelper.deleteTransaction(transaction.id)
                Toast.makeText(this, getString(R.string.transaction_deleted), Toast.LENGTH_SHORT).show()
                refreshData()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showLogoutConfirmation() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.nav_logout))
            .setMessage(getString(R.string.confirm_logout))
            .setPositiveButton(getString(R.string.nav_logout)) { _, _ ->
                sessionManager.clearSession()
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun refreshData() {
        refreshBalanceDisplay()

        val transactions = databaseHelper.getAllTransactions(sessionManager.userId)
        transactionAdapter.updateData(transactions)

        if (transactions.isEmpty()) {
            binding.rvTransactions.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvTransactions.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.GONE
        }
    }

    private fun refreshBalanceDisplay() {
        val userId = sessionManager.userId
        val totalBalance = databaseHelper.getTotalBalance(userId)
        val totalIncome = databaseHelper.getTotalIncome(userId)
        val totalExpense = databaseHelper.getTotalExpense(userId)

        if (sessionManager.isHideNominal) {
            binding.tvTotalBalance.text = getString(R.string.hidden_amount)
            binding.tvTotalIncome.text = getString(R.string.hidden_amount)
            binding.tvTotalExpense.text = getString(R.string.hidden_amount)
        } else {
            binding.tvTotalBalance.text = getString(R.string.currency_format, currencyFormat.format(totalBalance))
            binding.tvTotalIncome.text = getString(R.string.currency_format, currencyFormat.format(totalIncome))
            binding.tvTotalExpense.text = getString(R.string.currency_format, currencyFormat.format(totalExpense))
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseHelper.close()
    }
}