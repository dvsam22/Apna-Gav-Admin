package com.example.apnagavadmin.data.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class LocalizedString(
    val en: String = "",
    val hi: String = ""
) {
    /**
     * Non-composable way to get text. Use this in ViewModels or when language is manually passed.
     */
    fun get(currentLang: String? = null): String {
        val lang = currentLang ?: Locale.getDefault().language
        return if (lang.startsWith("hi")) hi.ifEmpty { en } else en.ifEmpty { hi }
    }
}

/**
 * Reactive way to get localized text in Compose.
 */
@Composable
fun LocalizedString.text(): String {
    val config = LocalConfiguration.current
    return get(config.locales[0].language)
}

fun String.toLocalized() = LocalizedString(en = this, hi = this)
