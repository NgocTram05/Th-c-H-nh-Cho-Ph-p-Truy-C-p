package com.example.baitapnangcao.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
// --- THAY ĐỔI QUAN TRỌNG ---
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
// --- --- ---
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitapnangcao.ui.theme.*

@Composable
fun OnboardingScreen() {
    var currentScreen by remember { mutableStateOf(0) }
    var showDialogs by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        LoginEntryBaseScreen()

        if (showDialogs) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Dialog 1: Location
                AnimatedVisibility(visible = currentScreen == 0, enter = fadeIn(), exit = fadeOut()) {
                    PermissionDialog(
                        icon = rememberVectorPainter(Icons.Default.LocationOn),
                        iconBackgroundColor = MintBg, iconTint = IconTintMint,
                        title = "Location", description = "Allow maps to access your location while you use the app?",
                        primaryButtonText = "Allow",
                        onPrimaryButtonClick = { currentScreen = 1 },
                        onSkipButtonClick = { currentScreen = 1 }
                    )
                }
                // Dialog 2: Notification
                AnimatedVisibility(visible = currentScreen == 1, enter = fadeIn(), exit = fadeOut()) {
                    PermissionDialog(
                        icon = rememberVectorPainter(Icons.Default.Notifications),
                        iconBackgroundColor = CreamBg, iconTint = IconTintYellow,
                        title = "Notification", description = "Please enable notifications to receive updates and reminders",
                        primaryButtonText = "Turn on",
                        onPrimaryButtonClick = { currentScreen = 2 },
                        onSkipButtonClick = { currentScreen = 2 }
                    )
                }
                // Dialog 3: Camera
                AnimatedVisibility(visible = currentScreen == 2, enter = fadeIn(), exit = fadeOut()) {
                    PermissionDialog(
                        icon = rememberVectorPainter(Icons.Default.CameraAlt),
                        iconBackgroundColor = MintBg, iconTint = IconTintMint,
                        title = "Camera", description = "We need access to your camera to scan QR codes",
                        primaryButtonText = "Turn on",
                        onPrimaryButtonClick = { showDialogs = false },
                        onSkipButtonClick = { showDialogs = false }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginEntryBaseScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(DarkerGrey)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "16:54", color = Color.White, fontSize = 16.sp)
            Row {
                Text(text = "বাংলা", color = Color.LightGray, fontSize = 14.sp)
                Spacer(Modifier.width(8.dp))
                Text(text = "ENG", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
                .padding(top = 56.dp)
                .background(Color(0xFFFDD835).copy(alpha = 0.3f))
        ) {  }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
        ) {
            // Nút Login
            Button(
                onClick = {  },

                colors = ButtonDefaults.buttonColors(containerColor = MidGrey),
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Login", color = Color.White, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { /* Xử lý tiếp tục không tài khoản */ },
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text("Continue without account", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun PermissionDialog(
    icon: Painter, iconBackgroundColor: Color, iconTint: Color,
    title: String, description: String, primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit, onSkipButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .wrapContentHeight(),
        shape = RoundedCornerShape(24.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(iconBackgroundColor)
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon, contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    colorFilter = ColorFilter.tint(iconTint)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Tiêu đề
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
            // Mô tả
            Text(
                text = description, fontSize = 16.sp, color = Color.DarkGray,
                textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = onPrimaryButtonClick,

                colors = ButtonDefaults.buttonColors(containerColor = Orange500),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(primaryButtonText, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onSkipButtonClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, Orange100)
            ) {
                Text("Skip for now", color = MidGrey, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    BaiTapNangCaoTheme {
        OnboardingScreen()
    }
}