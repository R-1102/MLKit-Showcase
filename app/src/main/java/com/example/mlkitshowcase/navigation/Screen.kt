package com.example.mlkitshowcase.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Vision : Screen("vision")
    object Language : Screen("language")
    // Vision features
    object OCR : Screen("ocr")
    object Barcode : Screen("barcode")
    object Face : Screen("face")
    object Object : Screen("object")
    // Language features
    object SmartReply : Screen("smart_reply")
    object Translation : Screen("translation")
    object LanguageID : Screen("language_id")
}