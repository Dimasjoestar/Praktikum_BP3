package com.pab.ecolifehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.DailyHabit

class DailyHabitAdapter(
    private val habits: MutableList<DailyHabit>,
    private val onHabitChecked: (DailyHabit, Boolean) -> Unit
) : RecyclerView.Adapter<DailyHabitAdapter.DailyHabitViewHolder>() {

    inner class DailyHabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardHabit)
        private val checkBox: CheckBox = itemView.findViewById(R.id.cbHabit)
        private val titleView: TextView = itemView.findViewById(R.id.tvHabitTitle)
        private val numberView: TextView = itemView.findViewById(R.id.tvHabitNumber)

        fun bind(habit: DailyHabit, position: Int) {
            titleView.text = habit.title
            numberView.text = (position + 1).toString()
            checkBox.isChecked = habit.isCompleted
            
            // Update text style based on completion
            updateTextStyle(habit.isCompleted)
            
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                habit.isCompleted = isChecked
                updateTextStyle(isChecked)
                onHabitChecked(habit, isChecked)
            }
            
            cardView.setOnClickListener {
                checkBox.isChecked = !checkBox.isChecked
            }
        }
        
        private fun updateTextStyle(isCompleted: Boolean) {
            if (isCompleted) {
                titleView.alpha = 0.6f
                titleView.paintFlags = titleView.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                titleView.alpha = 1.0f
                titleView.paintFlags = titleView.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daily_habit, parent, false)
        return DailyHabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyHabitViewHolder, position: Int) {
        holder.bind(habits[position], position)
    }

    override fun getItemCount(): Int = habits.size
}
