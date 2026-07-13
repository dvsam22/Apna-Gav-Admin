package com.example.apnagavadmin.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class PreferenceManager(private val context: Context) {
    companion object {
        private val IS_DUMMY_DATA_GENERATED = booleanPreferencesKey("is_dummy_data_generated")
    }

    val isDummyDataGenerated: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_DUMMY_DATA_GENERATED] ?: false
    }

    suspend fun setDummyDataGenerated(isGenerated: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DUMMY_DATA_GENERATED] = isGenerated
        }
    }
}
