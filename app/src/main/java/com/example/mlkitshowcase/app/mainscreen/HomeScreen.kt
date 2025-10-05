package com.example.mlkitshowcase.app.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mlkitshowcase.navigation.Screen
import com.example.mlkitshowcase.navigation.TopBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = { TopBar(title = "🤖 ML Kit Showcase") }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Choose a Category",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            FeatureCardLarge(
                title = "👁️ Vision APIs",
                description = "Text Recognition, Barcode Scanning, and more",
                onClick = { navController.navigate(Screen.Vision.route) }
            )

            FeatureCardLarge(
                title = "🧠 Natural Language APIs",
                description = "Smart Reply, Translation, Language ID, etc.",
                onClick = { navController.navigate(Screen.Language.route) }
            )
        }
    }
}
@Composable
fun FeatureCardLarge(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
        }
    }
}


