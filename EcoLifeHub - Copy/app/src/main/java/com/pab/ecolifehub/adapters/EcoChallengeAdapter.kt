package com.pab.ecolifehub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.models.EcoChallenge

class EcoChallengeAdapter(
    private val challenges: List<EcoChallenge>,
    private val onItemClick: (EcoChallenge) -> Unit
) : RecyclerView.Adapter<EcoChallengeAdapter.EcoChallengeViewHolder>() {

    inner class EcoChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: MaterialCardView = itemView.findViewById(R.id.cardChallenge)
        private val iconView: ImageView = itemView.findViewById(R.id.ivChallengeIcon)
        private val titleView: TextView = itemView.findViewById(R.id.tvChallengeTitle)
        private val difficultyView: TextView = itemView.findViewById(R.id.tvChallengeDifficulty)
        private val descView: TextView = itemView.findViewById(R.id.tvChallengeDescription)

        fun bind(challenge: EcoChallenge) {
            iconView.setImageResource(challenge.iconResId)
            titleView.text = challenge.title
            difficultyView.text = challenge.difficulty
            descView.text = challenge.description
            
            // Set difficulty color
            val difficultyColor = when (challenge.difficulty) {
                "Mudah" -> itemView.context.getColor(R.color.success)
                "Menengah" -> itemView.context.getColor(R.color.warning)
                "Sulit" -> itemView.context.getColor(R.color.error)
                else -> itemView.context.getColor(R.color.text_secondary)
            }
            difficultyView.setTextColor(difficultyColor)
            
            cardView.setOnClickListener {
                onItemClick(challenge)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoChallengeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_eco_challenge, parent, false)
        return EcoChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EcoChallengeViewHolder, position: Int) {
        holder.bind(challenges[position])
    }

    override fun getItemCount(): Int = challenges.size
}
