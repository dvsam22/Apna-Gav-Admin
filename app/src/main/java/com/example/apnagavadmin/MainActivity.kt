package com.example.apnagavadmin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.apnagavadmin.navigation.NavRoute
import com.example.apnagavadmin.ui.navigation.ApnaGavNavHost
import com.example.apnagavadmin.ui.theme.ApnaGavAdminTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        setContent {
            ApnaGavAdminTheme {
                val backStack = remember { mutableStateListOf<NavRoute>(NavRoute.VillageList) }
                ApnaGavNavHost(backStack = backStack)
            }
        }
    }
}
