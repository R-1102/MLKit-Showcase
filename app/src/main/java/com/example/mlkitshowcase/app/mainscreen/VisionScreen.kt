package com.example.mlkitshowcase.app.mainscreen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mlkitshowcase.navigation.Screen
import com.example.mlkitshowcase.navigation.TopBar


@Composable
fun VisionScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar("👁️ Vision APIs") { navController.popBackStack() } }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FeatureCardLarge(
                title = "📸 Text Recognition",
                description = "Extract text from images",
                onClick = { navController.navigate(Screen.OCR.route) }
            )

            FeatureCardLarge(
                title = "🔳 Barcode Scanner",
                description = "Scan QR codes and barcodes",
                onClick = { navController.navigate(Screen.Barcode.route) }
            )

            FeatureCardLarge(
                title = "🙂 Face Detection",
                description = "Detect faces in photos",
                onClick = { navController.navigate(Screen.Face.route) }
            )

            FeatureCardLarge(
                title = "📦 Object Detection",
                description = "Identify objects in images",
                onClick = { navController.navigate(Screen.Object.route) }
            )
        }
    }
}
