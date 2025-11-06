package com.example.baitapnangcao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.baitapnangcao.ui.onboarding.OnboardingScreen
import com.example.baitapnangcao.ui.theme.BaiTapNangCaoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaiTapNangCaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // << SỬA Ở ĐÂY: DÙNG colorScheme
                    color = MaterialTheme.colorScheme.background
                ) {
                    OnboardingScreen()
                }
            }
        }
    }
}