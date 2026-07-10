package com.example.apnagavadmin

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.apnagavadmin.data.repository.NewsBannerRepository
import com.example.apnagavadmin.navigation.NavRoute
import com.example.apnagavadmin.ui.navigation.ApnaGavNavHost
import com.example.apnagavadmin.ui.theme.ApnaGavAdminTheme
import com.example.apnagavadmin.util.DummyDataGenerator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
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

        // Generate dummy data (Remove or comment this after first run)
        DummyDataGenerator().generateAllData()

        setContent {
            ApnaGavAdminTheme {
                val backStack = remember { mutableStateListOf<NavRoute>(NavRoute.VillageList) }
                ApnaGavNavHost(backStack = backStack)
            }
        }
    }
}
