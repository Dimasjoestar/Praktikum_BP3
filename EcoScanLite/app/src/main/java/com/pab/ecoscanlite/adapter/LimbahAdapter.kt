package com.pab.ecoscanlite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pab.ecoscanlite.R
import com.pab.ecoscanlite.model.Limbah

/**
 * Adapter untuk menampilkan list Limbah di RecyclerView
 */
class LimbahAdapter(
    private val limbahList: ArrayList<Limbah>,
    private val onItemClick: (Limbah) -> Unit
) : RecyclerView.Adapter<LimbahAdapter.LimbahViewHolder>() {

    /**
     * ViewHolder untuk menyimpan referensi view dari item layout
     */
    inner class LimbahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivLimbah: ImageView = itemView.findViewById(R.id.ivLimbah)
        val tvNamaLimbah: TextView = itemView.findViewById(R.id.tvNamaLimbah)
        
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(limbahList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LimbahViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_limbah, parent, false)
        return LimbahViewHolder(view)
    }

    override fun onBindViewHolder(holder: LimbahViewHolder, position: Int) {
        val limbah = limbahList[position]
        
        holder.ivLimbah.setImageResource(limbah.imageResId)
        holder.tvNamaLimbah.text = limbah.nama
    }

    override fun getItemCount(): Int = limbahList.size
}
