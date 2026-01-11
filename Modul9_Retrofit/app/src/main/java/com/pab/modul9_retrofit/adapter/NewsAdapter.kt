package com.pab.modul9_retrofit.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pab.modul9_retrofit.R
import com.pab.modul9_retrofit.model.NewsItem
import java.text.SimpleDateFormat
import java.util.Locale

class NewsAdapter(private var newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivNewsImage: ImageView = itemView.findViewById(R.id.ivNewsImage)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]

        holder.tvTitle.text = news.title ?: "No Title"
        holder.tvDescription.text = news.contentSnippet ?: ""
        holder.tvDate.text = formatDate(news.isoDate ?: "")

        Glide.with(holder.itemView.context)
            .load(news.image)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.ivNewsImage)

        holder.itemView.setOnClickListener {
            news.link?.let { link ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun updateData(newList: List<NewsItem>) {
        newsList = newList
        notifyDataSetChanged()
    }

    private fun formatDate(isoDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val date = inputFormat.parse(isoDate) ?: outputFormat.parse(isoDate)
            val displayFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            displayFormat.format(date!!)
        } catch (e: Exception) {
            isoDate
        }
    }
}
