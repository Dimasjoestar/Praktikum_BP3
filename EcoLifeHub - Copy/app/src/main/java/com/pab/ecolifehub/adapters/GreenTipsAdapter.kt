package com.pab.ecolifehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.GreenTip

class GreenTipsAdapter(
    private val tips: List<GreenTip>
) : RecyclerView.Adapter<GreenTipsAdapter.GreenTipViewHolder>() {

    inner class GreenTipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.ivTipIcon)
        private val titleView: TextView = itemView.findViewById(R.id.tvTipTitle)
        private val descView: TextView = itemView.findViewById(R.id.tvTipDescription)
        private val numberView: TextView = itemView.findViewById(R.id.tvTipNumber)

        fun bind(tip: GreenTip, position: Int) {
            iconView.setImageResource(tip.iconResId)
            titleView.text = tip.title
            descView.text = tip.description
            numberView.text = (position + 1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreenTipViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_green_tip, parent, false)
        return GreenTipViewHolder(view)
    }

    override fun onBindViewHolder(holder: GreenTipViewHolder, position: Int) {
        holder.bind(tips[position], position)
    }

    override fun getItemCount(): Int = tips.size
}
