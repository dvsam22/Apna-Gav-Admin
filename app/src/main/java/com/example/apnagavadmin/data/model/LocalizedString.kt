package com.example.apnagavadmin.data.model

import kotlinx.serialization.Serializable
import java.util.Locale

@Serializable
data class LocalizedString(
    val en: String = "",
    val hi: String = ""
) {
    fun text(currentLang: String? = null): String {
        val lang = currentLang ?: Locale.getDefault().language
        return if (lang.startsWith("hi")) hi.ifEmpty { en } else en.ifEmpty { hi }
    }
}

fun String.toLocalized() = LocalizedString(en = this, hi = this)
