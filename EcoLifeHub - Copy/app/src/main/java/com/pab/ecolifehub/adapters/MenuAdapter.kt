package com.pab.ecolifehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.MenuItem

class MenuAdapter(
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardMenu)
        private val iconView: ImageView = itemView.findViewById(R.id.ivMenuIcon)
        private val titleView: TextView = itemView.findViewById(R.id.tvMenuTitle)
        private val descView: TextView = itemView.findViewById(R.id.tvMenuDescription)

        fun bind(menuItem: MenuItem) {
            iconView.setImageResource(menuItem.iconResId)
            titleView.text = menuItem.title
            descView.text = menuItem.description
            
            cardView.setOnClickListener {
                onItemClick(menuItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int = menuItems.size
}
