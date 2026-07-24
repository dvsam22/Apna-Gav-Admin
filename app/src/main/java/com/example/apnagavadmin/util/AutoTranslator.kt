package com.example.apnagavadmin.util

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

object AutoTranslator {
    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.HINDI)
        .build()
    
    private val translator = Translation.getClient(options)
    private val client = OkHttpClient()
    private var isModelDownloaded = false

    /**
     * Translates or Transliterates text from English to Hindi.
     * If [isPhonetic] is true, it prioritizes Hinglish conversion (teju -> तेजू).
     */
    suspend fun translate(text: String, isPhonetic: Boolean = false): String = withContext(Dispatchers.IO) {
        if (text.isBlank()) return@withContext ""
        
        // Step 1: Try Standard Translation (Meaning-based)
        val translated = try {
            ensureModelDownloaded()
            translator.translate(text).await()
        } catch (e: Exception) {
            ""
        }

        // Step 2: If translation failed or returned same word, OR if phonetic is requested
        // use Transliteration (Hinglish -> Hindi Script)
        if (isPhonetic || translated.equals(text, ignoreCase = true) || !hasHindiScript(translated)) {
            return@withContext transliterate(text)
        }

        return@withContext translated
    }

    private fun hasHindiScript(text: String): Boolean {
        return text.any { Character.UnicodeBlock.of(it) == Character.UnicodeBlock.DEVANAGARI }
    }

    /**
     * Uses free Google Input Tools API for Transliteration (Free & Accurate for Hinglish)
     */
    private suspend fun transliterate(text: String): String {
        return try {
            val url = "https://inputtools.google.com/request?text=$text&itc=hi-t-i0-und&num=1&cp=0&cs=1&ie=utf-8&oe=utf-8&app=test"
            val request = Request.Builder().url(url).build()
            
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: ""
            
            // Parse JSON: ["SUCCESS",[["text",["तेजू"],[],{"goog:suggested_output_q-type":"TRANSLITERATION"}]]]
            val jsonArray = JSONArray(body)
            if (jsonArray.getString(0) == "SUCCESS") {
                val results = jsonArray.getJSONArray(1).getJSONArray(0).getJSONArray(1)
                if (results.length() > 0) {
                    return results.getString(0)
                }
            }
            ""
        } catch (e: Exception) {
            android.util.Log.e("AutoTranslator", "Transliteration failed", e)
            ""
        }
    }

    private suspend fun ensureModelDownloaded() {
        if (isModelDownloaded) return
        val conditions = DownloadConditions.Builder().build()
        translator.downloadModelIfNeeded(conditions).await()
        isModelDownloaded = true
    }
}
