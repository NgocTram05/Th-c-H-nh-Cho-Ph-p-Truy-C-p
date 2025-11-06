
// Vị trí: app/src/main/java/com/example/baitapnangcao/ui/onboarding/OnboardingScreen.kt
package com.example.baitapnangcao.ui.onboarding

// << LOGIC MỚI: Import thêm các thư viện cần thiết
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
// ---
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.baitapnangcao.ui.theme.*

@Composable
fun OnboardingScreen() {
    var currentScreen by remember { mutableStateOf(0) } // 0: Location, 1: Notification, 2: Camera
    var showDialogs by remember { mutableStateOf(true) }

    // << --- LOGIC MỚI: ĐỊNH NGHĨA CÁC LAUNCHER XIN QUYỀN --- >>

    // Launcher cho Quyền Vị trí (Location)
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            // Bất kể granted hay không, chuyển sang màn hình tiếp theo
            currentScreen = 1
        }
    )

    // Launcher cho Quyền Thông báo (Notification) - API 33+
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            // Bất kể granted hay không, chuyển sang màn hình tiếp theo
            currentScreen = 2
        }
    )

    // Launcher cho Quyền Camera
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            // Bất kể granted hay không, kết thúc flow
            showDialogs = false
        }
    )
    // << --- HẾT PHẦN LOGIC MỚI --- >>


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {


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
                        onPrimaryButtonClick = {
                            // << LOGIC MỚI: Gọi launcher xin quyền Location
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        },
                        onSkipButtonClick = { currentScreen = 1 } // Bỏ qua, sang màn hình 2
                    )
                }
                // Dialog 2: Notification
                AnimatedVisibility(visible = currentScreen == 1, enter = fadeIn(), exit = fadeOut()) {
                    PermissionDialog(
                        icon = rememberVectorPainter(Icons.Default.Notifications),
                        iconBackgroundColor = CreamBg, iconTint = IconTintYellow,
                        title = "Notification", description = "Please enable notifications to receive updates and reminders",
                        primaryButtonText = "Turn on",
                        onPrimaryButtonClick = {
                            // << LOGIC MỚI: Chỉ xin quyền Notification trên API 33 (Android 13) trở lên
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                            } else {
                                // Với API < 33, không cần xin quyền này, tự động next
                                currentScreen = 2
                            }
                        },
                        onSkipButtonClick = { currentScreen = 2 } // Bỏ qua, sang màn hình 3
                    )
                }
                // Dialog 3: Camera
                AnimatedVisibility(visible = currentScreen == 2, enter = fadeIn(), exit = fadeOut()) {
                    PermissionDialog(
                        icon = rememberVectorPainter(Icons.Default.CameraAlt),
                        iconBackgroundColor = MintBg, iconTint = IconTintMint,
                        title = "Camera", description = "We need access to your camera to scan QR codes",
                        primaryButtonText = "Turn on",
                        onPrimaryButtonClick = {
                            // << LOGIC MỚI: Gọi launcher xin quyền Camera
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        },
                        onSkipButtonClick = { showDialogs = false } // Bỏ qua, kết thúc
                    )
                }
            }
        }
    }
}

// ... (Các Composable LoginEntryBaseScreen và PermissionDialog giữ nguyên như cũ) ...
// (Bạn chỉ cần đảm bảo 2 Composable này có tồn tại trong file)

@Composable
fun LoginEntryBaseScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
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
        ) { /* Đặt Image của bạn ở đây */ }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp, start = 32.dp, end = 32.dp)
        ) {
            Button(
                onClick = { /* Xử lý đăng nhập */ },
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
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(12.dp))
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
