package com.pab.moneytracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pab.moneytracker.R
import com.pab.moneytracker.databinding.ItemTransactionBinding
import com.pab.moneytracker.model.Category
import com.pab.moneytracker.model.Transaction
import com.pab.moneytracker.model.TransactionType
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(
    private var transactions: MutableList<Transaction> = mutableListOf(),
    private var hideNominal: Boolean = false,
    private val onEditClick: (Transaction) -> Unit,
    private val onDeleteClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private val currencyFormat = NumberFormat.getNumberInstance(Locale("id", "ID"))
    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    inner class TransactionViewHolder(
        private val binding: ItemTransactionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: Transaction) {
            binding.apply {
                val context = root.context

                // Set title
                tvTransactionTitle.text = transaction.title

                // Set category
                tvTransactionCategory.text = transaction.category.displayName

                // Set date
                tvTransactionDate.text = dateFormat.format(Date(transaction.date))

                // Set category icon
                val iconRes = getCategoryIcon(transaction.category)
                ivCategoryIcon.setImageResource(iconRes)

                // Set amount and color based on type
                val amountText = if (hideNominal) {
                    context.getString(R.string.hidden_amount)
                } else {
                    when (transaction.type) {
                        TransactionType.INCOME -> "+Rp ${currencyFormat.format(transaction.amount)}"
                        TransactionType.EXPENSE -> "-Rp ${currencyFormat.format(transaction.amount)}"
                    }
                }
                tvTransactionAmount.text = amountText

                val amountColor = when (transaction.type) {
                    TransactionType.INCOME -> R.color.colorIncome
                    TransactionType.EXPENSE -> R.color.colorExpense
                }
                tvTransactionAmount.setTextColor(ContextCompat.getColor(context, amountColor))

                // Click listeners
                btnEdit.setOnClickListener { onEditClick(transaction) }
                btnDelete.setOnClickListener { onDeleteClick(transaction) }
            }
        }

        private fun getCategoryIcon(category: Category): Int {
            return when (category) {
                Category.FOOD_DRINK -> R.drawable.ic_food
                Category.SHOPPING -> R.drawable.ic_shopping
                Category.HOME -> R.drawable.ic_home_category
                Category.TRANSPORT -> R.drawable.ic_transport
                Category.VEHICLE -> R.drawable.ic_vehicle
                Category.INVESTMENT -> R.drawable.ic_investment
                Category.OTHER -> R.drawable.ic_other
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position])
    }

    override fun getItemCount(): Int = transactions.size

    fun updateData(newTransactions: List<Transaction>) {
        transactions.clear()
        transactions.addAll(newTransactions)
        notifyDataSetChanged()
    }

    fun setHideNominal(hide: Boolean) {
        hideNominal = hide
        notifyDataSetChanged()
    }

    fun getTransactionAt(position: Int): Transaction = transactions[position]
}
