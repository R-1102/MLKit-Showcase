package com.example.mlkitshowcase.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mlkitshowcase.app.mainscreen.HomeScreen
import com.example.mlkitshowcase.app.naturalLanguage.LanguageIDScreen
import com.example.mlkitshowcase.app.mainscreen.NaturalLanguageScreen
import com.example.mlkitshowcase.app.naturalLanguage.SmartReplyChatScreen
import com.example.mlkitshowcase.app.naturalLanguage.TranslationScreen
import com.example.mlkitshowcase.app.vision.BarcodeScreen
import com.example.mlkitshowcase.app.vision.FaceScreen
import com.example.mlkitshowcase.app.vision.OCRScreen
import com.example.mlkitshowcase.app.vision.ObjectScreen
import com.example.mlkitshowcase.app.mainscreen.VisionScreen

@Composable
fun MLKitNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {

        // 🏠 Main Entry
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }

        // 👁️ Vision Category
        composable(Screen.Vision.route) {
            VisionScreen(navController)
        }

        // 🧠 Language Category
        composable(Screen.Language.route) {
            NaturalLanguageScreen(navController)
        }

        // 👁️ Vision Features
        composable(Screen.OCR.route) {
            OCRScreen(navController)
        }
        composable(Screen.Barcode.route) {
            BarcodeScreen(navController)
        }
        composable(Screen.Face.route) {
            FaceScreen(navController)
        }
        composable(Screen.Object.route) {
            ObjectScreen(navController)
        }

        // 🧠 Language Features
        composable(Screen.SmartReply.route) {
            SmartReplyChatScreen(navController)
        }
        composable(Screen.Translation.route) {
            TranslationScreen(navController)
        }
        composable(Screen.LanguageID.route) {
            LanguageIDScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onBackClick: (() -> Unit)? = null) {
   TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}
