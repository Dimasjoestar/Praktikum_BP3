package com.pab.ecolifehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.WasteCategory

class WasteCategoryAdapter(
    private val categories: List<WasteCategory>,
    private val onItemClick: (WasteCategory) -> Unit
) : RecyclerView.Adapter<WasteCategoryAdapter.WasteCategoryViewHolder>() {

    inner class WasteCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardWasteCategory)
        private val iconView: ImageView = itemView.findViewById(R.id.ivWasteIcon)
        private val titleView: TextView = itemView.findViewById(R.id.tvWasteName)
        private val descView: TextView = itemView.findViewById(R.id.tvWasteDescription)

        fun bind(category: WasteCategory) {
            iconView.setImageResource(category.iconResId)
            titleView.text = category.name
            descView.text = category.description
            
            cardView.setOnClickListener {
                onItemClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_waste_category, parent, false)
        return WasteCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: WasteCategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
