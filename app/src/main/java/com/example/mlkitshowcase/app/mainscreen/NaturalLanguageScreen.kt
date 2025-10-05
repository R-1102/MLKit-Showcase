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
fun NaturalLanguageScreen(navController: NavController) {
    Scaffold(
        topBar = { TopBar("🧠 Natural Language APIs") { navController.popBackStack() } }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FeatureCardLarge(
                title = "💬 Smart Reply",
                description = "Suggests context-aware chat replies",
                onClick = { navController.navigate(Screen.SmartReply.route) }
            )

            FeatureCardLarge(
                title = "🌍 Translation",
                description = "Translate text between languages",
                onClick = { navController.navigate(Screen.Translation.route) }
            )

            FeatureCardLarge(
                title = "🌐 Language Identifier",
                description = "Detect the language of any text",
                onClick = { navController.navigate(Screen.LanguageID.route) }
            )
        }
    }
}
