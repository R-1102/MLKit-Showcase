package com.example.mlkitshowcase.app.naturalLanguage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TranslationScreen(navController: NavController? = null) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var isTranslating by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val options = TranslatorOptions.Builder()
        .setSourceLanguage(TranslateLanguage.ENGLISH)
        .setTargetLanguage(TranslateLanguage.SPANISH)
        .build()

    val translator = Translation.getClient(options)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Translation (EN → ES)",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🌐 Language Row
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AssistChip(
                    onClick = {},
                    label = { Text("English") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Language,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                AssistChip(
                    onClick = {},
                    label = { Text("Spanish") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Translate,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }

            Spacer(Modifier.height(24.dp))

            // 📝 Input field
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Enter text in English") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            // 🔄 Translate Button
            Button(
                onClick = {
                    if (input.isBlank()) {
                        output = "Please enter some text first."
                        isError = true
                        return@Button
                    }

                    isTranslating = true
                    isError = false
                    output = ""

                    translator.downloadModelIfNeeded()
                        .addOnSuccessListener {
                            translator.translate(input)
                                .addOnSuccessListener {
                                    isTranslating = false
                                    output = it
                                }
                                .addOnFailureListener { e ->
                                    isTranslating = false
                                    isError = true
                                    output = "Translation failed: ${e.message}"
                                }
                        }
                        .addOnFailureListener { e ->
                            isTranslating = false
                            isError = true
                            output = "Model download failed: ${e.message}"
                        }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isTranslating,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (isTranslating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Translating...")
                } else {
                    Text("Translate")
                }
            }

            Spacer(Modifier.height(24.dp))

            // 💬 Output Box
            if (output.isNotEmpty()) {
                val textColor = if (isError)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface

                val icon = if (isError)
                    Icons.Default.Error
                else
                    Icons.Default.ChatBubble

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = null, tint = textColor)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (isError) "Error" else "Translation Result",
                        color = textColor,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Surface(
                    tonalElevation = 2.dp,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SelectionContainer {
                        Text(
                            output,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}
