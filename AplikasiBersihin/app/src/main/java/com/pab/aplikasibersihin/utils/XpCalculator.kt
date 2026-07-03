package com.pab.aplikasibersihin.utils

import com.pab.aplikasibersihin.data.model.MemberLevel

object XpCalculator {

    data class Progression(
        val currentLevel: MemberLevel,
        val nextLevel: MemberLevel?,
        val progressPercentage: Float,
        val xpInCurrentLevel: Int,
        val xpRequiredForNext: Int,
        val remainingXp: Int
    )

    fun calculateProgression(xp: Int): Progression {
        val currentLevel = MemberLevel.getLevelFromXp(xp)
        
        val nextLevel = when (currentLevel) {
            MemberLevel.BRONZE -> MemberLevel.SILVER
            MemberLevel.SILVER -> MemberLevel.GOLD
            MemberLevel.GOLD -> MemberLevel.PLATINUM
            MemberLevel.PLATINUM -> null
        }

        if (nextLevel == null) {
            // Max level reached
            return Progression(
                currentLevel = currentLevel,
                nextLevel = null,
                progressPercentage = 1.0f,
                xpInCurrentLevel = xp,
                xpRequiredForNext = currentLevel.minXp,
                remainingXp = 0
            )
        }

        val rangeStart = currentLevel.minXp
        val rangeEnd = nextLevel.minXp
        val rangeTotal = rangeEnd - rangeStart
        val currentProgress = (xp - rangeStart).coerceIn(0, rangeTotal)

        val progressPercentage = currentProgress.toFloat() / rangeTotal.toFloat()
        val remainingXp = rangeEnd - xp

        return Progression(
            currentLevel = currentLevel,
            nextLevel = nextLevel,
            progressPercentage = progressPercentage,
            xpInCurrentLevel = currentProgress,
            xpRequiredForNext = rangeTotal,
            remainingXp = remainingXp
        )
    }
}
