/*
// Vị trí: app/src/main/java/com/example/baitapnangcao/ui/profile/ProfileScreen.kt
package com.example.baitapnangcao.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter

// Composable chính cho màn hình Profile
@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    // 1. Định nghĩa quyền cần xin
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // 2. Trạng thái để lưu URI của ảnh được chọn
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 3. Trạng thái để kiểm tra quyền
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                permissionToRequest
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    // 4. Launcher MỚI để MỞ THƯ VIỆN ẢNH
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri // Lưu ảnh người dùng đã chọn
        }
    )

    // 5. Launcher MỚI để XIN QUYỀN ĐỌC ẢNH
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermission = isGranted
            if (isGranted) {
                // Nếu người dùng vừa cấp quyền, mở ngay thư viện ảnh
                imagePickerLauncher.launch("image/*")
            }
            // (Bạn có thể thêm logic xử lý khi người dùng từ chối)
        }
    )

    // Giao diện (UI) của màn hình
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hình ảnh profile
        Image(
            // Nếu đã chọn ảnh (imageUri) thì dùng nó, nếu chưa thì dùng ảnh mặc định
            painter = rememberAsyncImagePainter(
                model = imageUri ?: android.R.drawable.ic_menu_myplaces
            ),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape) // Bo tròn ảnh
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop // Đảm bảo ảnh fill đều
        )

        Spacer(Modifier.height(24.dp))

        // Nút bấm
        Button(onClick = {
            if (hasPermission) {
                // Nếu đã có quyền, mở thẳng thư viện ảnh
                imagePickerLauncher.launch("image/*")
            } else {
                // Nếu chưa có quyền, gọi launcher xin quyền
                permissionLauncher.launch(permissionToRequest)
            }
        }) {
            Text("Thay đổi ảnh Profile")
        }

        Spacer(Modifier.height(16.dp))

        // Text hiển thị trạng thái quyền
        Text(
            text = if (hasPermission) "Trạng thái: Đã cấp quyền đọc ảnh." else "Trạng thái: Chưa cấp quyền đọc ảnh.",
            color = if (hasPermission) Color.Green else Color.Red
        )
    }
}

 */