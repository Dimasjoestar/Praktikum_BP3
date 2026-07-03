package com.pab.ecolifehub.utils

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PreferencesHelper {

    private const val PREF_NAME = "ecolifehub_prefs"
    private const val KEY_COMPLETED_CHALLENGES = "completed_challenges"
    private const val KEY_COMPLETED_HABITS_TODAY = "completed_habits_today"
    private const val KEY_HABITS_LAST_DATE = "habits_last_date"
    private const val KEY_TOTAL_HABITS_COMPLETED = "total_habits_completed"
    private const val KEY_DAYS_ACTIVE = "days_active"
    private const val KEY_LAST_ACTIVE_DATE = "last_active_date"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // ==================== CHALLENGES ====================

    /**
     * Get list of completed challenge IDs
     */
    fun getCompletedChallenges(context: Context): Set<Int> {
        val prefs = getPreferences(context)
        val challengesString = prefs.getString(KEY_COMPLETED_CHALLENGES, "") ?: ""
        if (challengesString.isEmpty()) return emptySet()
        return challengesString.split(",").mapNotNull { it.toIntOrNull() }.toSet()
    }

    /**
     * Add a challenge to completed list
     */
    fun addCompletedChallenge(context: Context, challengeId: Int) {
        val currentChallenges = getCompletedChallenges(context).toMutableSet()
        currentChallenges.add(challengeId)
        val challengesString = currentChallenges.joinToString(",")
        getPreferences(context).edit().putString(KEY_COMPLETED_CHALLENGES, challengesString).apply()
    }

    /**
     * Check if a challenge is completed
     */
    fun isChallengeCompleted(context: Context, challengeId: Int): Boolean {
        return getCompletedChallenges(context).contains(challengeId)
    }

    /**
     * Get total number of completed challenges
     */
    fun getCompletedChallengesCount(context: Context): Int {
        return getCompletedChallenges(context).size
    }

    // ==================== DAILY HABITS ====================

    /**
     * Get list of completed habit IDs for today
     */
    fun getCompletedHabitsToday(context: Context): Set<Int> {
        val prefs = getPreferences(context)
        val lastDate = prefs.getString(KEY_HABITS_LAST_DATE, "") ?: ""
        val today = getTodayDate()

        // Reset habits if it's a new day
        if (lastDate != today) {
            prefs.edit()
                .putString(KEY_HABITS_LAST_DATE, today)
                .putString(KEY_COMPLETED_HABITS_TODAY, "")
                .apply()
            return emptySet()
        }

        val habitsString = prefs.getString(KEY_COMPLETED_HABITS_TODAY, "") ?: ""
        if (habitsString.isEmpty()) return emptySet()
        return habitsString.split(",").mapNotNull { it.toIntOrNull() }.toSet()
    }

    /**
     * Save habit status (completed or not)
     */
    fun saveHabitStatus(context: Context, habitId: Int, isCompleted: Boolean) {
        val prefs = getPreferences(context)
        val today = getTodayDate()
        
        // Ensure we're working with today's data
        val lastDate = prefs.getString(KEY_HABITS_LAST_DATE, "") ?: ""
        val currentHabits = if (lastDate != today) {
            prefs.edit().putString(KEY_HABITS_LAST_DATE, today).apply()
            mutableSetOf()
        } else {
            getCompletedHabitsToday(context).toMutableSet()
        }

        val previouslyCompleted = currentHabits.contains(habitId)

        if (isCompleted) {
            currentHabits.add(habitId)
            // Increment total if this is a new completion
            if (!previouslyCompleted) {
                incrementTotalHabitsCompleted(context)
            }
        } else {
            currentHabits.remove(habitId)
            // Decrement total if was previously completed
            if (previouslyCompleted) {
                decrementTotalHabitsCompleted(context)
            }
        }

        val habitsString = currentHabits.joinToString(",")
        prefs.edit().putString(KEY_COMPLETED_HABITS_TODAY, habitsString).apply()
    }

    /**
     * Check if a habit is completed today
     */
    fun isHabitCompletedToday(context: Context, habitId: Int): Boolean {
        return getCompletedHabitsToday(context).contains(habitId)
    }

    /**
     * Get total number of habits completed (all time)
     */
    fun getTotalHabitsCompleted(context: Context): Int {
        return getPreferences(context).getInt(KEY_TOTAL_HABITS_COMPLETED, 0)
    }

    private fun incrementTotalHabitsCompleted(context: Context) {
        val current = getTotalHabitsCompleted(context)
        getPreferences(context).edit().putInt(KEY_TOTAL_HABITS_COMPLETED, current + 1).apply()
    }

    private fun decrementTotalHabitsCompleted(context: Context) {
        val current = getTotalHabitsCompleted(context)
        if (current > 0) {
            getPreferences(context).edit().putInt(KEY_TOTAL_HABITS_COMPLETED, current - 1).apply()
        }
    }

    // ==================== DAYS ACTIVE ====================

    /**
     * Get the number of days the user has been active
     */
    fun getDaysActive(context: Context): Int {
        return getPreferences(context).getInt(KEY_DAYS_ACTIVE, 0)
    }

    /**
     * Check and update days active counter
     * Call this when app is opened
     */
    fun checkAndUpdateDaysActive(context: Context) {
        val prefs = getPreferences(context)
        val lastActiveDate = prefs.getString(KEY_LAST_ACTIVE_DATE, "") ?: ""
        val today = getTodayDate()

        if (lastActiveDate != today) {
            val currentDays = getDaysActive(context)
            prefs.edit()
                .putString(KEY_LAST_ACTIVE_DATE, today)
                .putInt(KEY_DAYS_ACTIVE, currentDays + 1)
                .apply()
        }
    }
}
