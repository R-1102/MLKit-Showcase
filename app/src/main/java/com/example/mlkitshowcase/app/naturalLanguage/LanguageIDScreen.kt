package com.example.mlkitshowcase.app.naturalLanguage

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.google.mlkit.nl.languageid.LanguageIdentification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageIDScreen(navController: NavController? = null) {
    var input by remember { mutableStateOf("") }
    var detected by remember { mutableStateOf("") }
    var isDetecting by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    val languageIdentifier = LanguageIdentification.getClient()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Language Identifier",
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
                .padding(24.dp)

            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🌍 Header Icon
            Icon(
                imageVector = Icons.Filled.Translate,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))

            // 📝 Input Field
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    detected = ""
                    isError = false
                },
                label = { Text("Enter text to detect language") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 3,
                textStyle = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(16.dp))

            // 🔍 Detect Button
            Button(
                onClick = {
                    if (input.isNotBlank()) {
                        isDetecting = true
                        isError = false
                        detected = ""

                        languageIdentifier.identifyLanguage(input)
                            .addOnSuccessListener { code ->
                                isDetecting = false
                                detected = if (code == "und") "Unknown" else code
                            }
                            .addOnFailureListener { e ->
                                isDetecting = false
                                isError = true
                                detected = "Error: ${e.message}"
                            }
                    } else {
                        isError = true
                        detected = "Please enter text first"
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                if (isDetecting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Detecting...")
                } else {
                    Text("Detect Language")
                }
            }

            Spacer(Modifier.height(24.dp))

            // 🗣️ Result Section
            if (detected.isNotEmpty()) {
                val textColor = when {
                    isError -> MaterialTheme.colorScheme.error
                    else -> MaterialTheme.colorScheme.onSurface
                }

                val icon = when {
                    isError -> Icons.Default.Error
                    else -> Icons.Default.Language
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(icon, contentDescription = null, tint = textColor)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (isError) "Detection failed" else "Detected Language",
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
                    Text(
                        detected,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = textColor
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
