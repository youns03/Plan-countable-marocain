package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.LanguageMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesRepository(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("plan_comptable_prefs", Context.MODE_PRIVATE)

    private val _favorites = MutableStateFlow<Set<String>>(loadFavorites())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _recentSearches = MutableStateFlow<List<String>>(loadRecentSearches())
    val recentSearches: StateFlow<List<String>> = _recentSearches.asStateFlow()

    private val _languageMode = MutableStateFlow(loadLanguageMode())
    val languageMode: StateFlow<LanguageMode> = _languageMode.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(loadDarkThemeSetting())
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow() // null = system

    private val _largeFont = MutableStateFlow(loadLargeFontSetting())
    val largeFont: StateFlow<Boolean> = _largeFont.asStateFlow()

    private fun loadFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    }

    fun toggleFavorite(code: String) {
        val current = _favorites.value.toMutableSet()
        if (current.contains(code)) {
            current.remove(code)
        } else {
            current.add(code)
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
        _favorites.value = current
    }

    fun isFavorite(code: String): Boolean {
        return _favorites.value.contains(code)
    }

    private fun loadRecentSearches(): List<String> {
        val raw = prefs.getString(KEY_RECENT_SEARCHES, "") ?: ""
        return if (raw.isEmpty()) emptyList() else raw.split("|||")
    }

    fun addRecentSearch(query: String) {
        val q = query.trim()
        if (q.isEmpty()) return
        val current = _recentSearches.value.toMutableList()
        current.remove(q)
        current.add(0, q)
        val trimmed = current.take(15)
        prefs.edit().putString(KEY_RECENT_SEARCHES, trimmed.joinToString("|||")).apply()
        _recentSearches.value = trimmed
    }

    fun clearRecentSearches() {
        prefs.edit().remove(KEY_RECENT_SEARCHES).apply()
        _recentSearches.value = emptyList()
    }

    private fun loadLanguageMode(): LanguageMode {
        val raw = prefs.getString(KEY_LANGUAGE_MODE, LanguageMode.BILINGUAL.name)
        return try {
            LanguageMode.valueOf(raw ?: LanguageMode.BILINGUAL.name)
        } catch (e: Exception) {
            LanguageMode.BILINGUAL
        }
    }

    fun setLanguageMode(mode: LanguageMode) {
        prefs.edit().putString(KEY_LANGUAGE_MODE, mode.name).apply()
        _languageMode.value = mode
    }

    private fun loadDarkThemeSetting(): Boolean? {
        return if (prefs.contains(KEY_DARK_THEME)) {
            prefs.getBoolean(KEY_DARK_THEME, false)
        } else {
            null // follow system
        }
    }

    fun setDarkTheme(isDark: Boolean?) {
        if (isDark == null) {
            prefs.edit().remove(KEY_DARK_THEME).apply()
        } else {
            prefs.edit().putBoolean(KEY_DARK_THEME, isDark).apply()
        }
        _isDarkTheme.value = isDark
    }

    private fun loadLargeFontSetting(): Boolean {
        return prefs.getBoolean(KEY_LARGE_FONT, false)
    }

    fun setLargeFont(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_LARGE_FONT, enabled).apply()
        _largeFont.value = enabled
    }

    companion object {
        private const val KEY_FAVORITES = "pref_favorites"
        private const val KEY_RECENT_SEARCHES = "pref_recent_searches"
        private const val KEY_LANGUAGE_MODE = "pref_language_mode"
        private const val KEY_DARK_THEME = "pref_dark_theme"
        private const val KEY_LARGE_FONT = "pref_large_font"

        @Volatile
        private var INSTANCE: PreferencesRepository? = null

        fun getInstance(context: Context): PreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesRepository(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
}
