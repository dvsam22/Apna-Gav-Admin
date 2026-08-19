package com.example.apnagavadmin

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import com.example.apnagavadmin.data.repository.NewsBannerRepository
import com.example.apnagavadmin.navigation.NavRoute
import com.example.apnagavadmin.ui.navigation.ApnaGavNavHost
import com.example.apnagavadmin.ui.theme.ApnaGavAdminTheme
import com.example.apnagavadmin.util.DummyDataGenerator
import com.example.apnagavadmin.util.PreferenceManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val preferenceManager = PreferenceManager(this)

        // Initialize Notification Service Account
        try {
            NewsBannerRepository.serviceAccountProvider = {
                resources.openRawResource(R.raw.service_account)
            }
        } catch (e: Exception) {
            android.util.Log.e("MainActivity", "service-account.json not found in raw")
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )

        // Generate Ausani real data on launch
        lifecycleScope.launch {
            // Force reset to upload Ausani village data
            preferenceManager.setDummyDataGenerated(false) 

            if (!preferenceManager.isDummyDataGenerated.first()) {
                DummyDataGenerator().generateAllData()
                preferenceManager.setDummyDataGenerated(true)
            }
        }

        setContent {
            ApnaGavAdminTheme {
                val context = androidx.compose.ui.platform.LocalContext.current
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    com.example.apnagavadmin.util.GlobalEventBus.events.collect { event ->
                        when (event) {
                            is com.example.apnagavadmin.util.UiEvent.ShowToast -> {
                                android.widget.Toast.makeText(context, event.message, android.widget.Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                val backStack = remember { mutableStateListOf<NavRoute>(NavRoute.VillageList) }
                ApnaGavNavHost(backStack = backStack)
            }
        }
    }
}
