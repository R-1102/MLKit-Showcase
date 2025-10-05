package com.example.mlkitshowcase.app.naturalLanguage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mlkitshowcase.R
import com.example.mlkitshowcase.navigation.TopBar
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SmartReplyChatScreen(navController: NavController? = null) {
    var userMessage by remember { mutableStateOf("") }
    var suggestions by remember { mutableStateOf(listOf<String>()) }
    var chatMessages by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    Scaffold(
        topBar = { TopBar("💬 Smart Reply") { navController?.popBackStack() } }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
                // 🔹 Header (Name + Avatar)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(12.dp)
                ) {
                Image(
                painter = painterResource(R.drawable.pic),
                contentDescription = "Bot Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "Smart Bot",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "online",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // 🔹 Chat History
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    reverseLayout = true
                ) {
                    items(chatMessages.reversed()) { (text, isUser) ->
                        Row(
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (!isUser) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Bot Avatar",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFE0E0E0))
                                        .padding(4.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(
                                        if (isUser) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant
                                    )
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = text,
                                    color = if (isUser) Color.White else Color.Black,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            if (isUser) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "User Avatar",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }

                // 🔹 Input + Receive Button
                Column(Modifier.padding(12.dp)) {
                    OutlinedTextField(
                        value = userMessage,
                        onValueChange = { userMessage = it },
                        label = { Text("Type received message...") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (userMessage.isNotEmpty()) {
                                chatMessages = chatMessages + (userMessage to false)
                                generateSmartReplies(userMessage) { suggestions = it }
                                userMessage = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        shape = RoundedCornerShape(50)
                    ) {
                        Text("Receive")
                    }

                    // 🔹 Smart Reply Suggestions
                    if (suggestions.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("💡 Smart Reply Suggestions:", fontWeight = FontWeight.Bold)

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                        ) {
                            suggestions.forEach { reply ->
                                AssistChip(
                                    onClick = {
                                        chatMessages = chatMessages + (reply to true)
                                        suggestions = emptyList()
                                    },
                                    label = { Text(reply) },
                                    shape = RoundedCornerShape(50),
                                    colors = AssistChipDefaults.assistChipColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        labelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

}
fun generateSmartReplies(text: String, onResult: (List<String>) -> Unit) {
    val smartReply = SmartReply.getClient()


    val conversation = listOf(
        TextMessage.createForRemoteUser(text, System.currentTimeMillis(), "user123")
    )

    smartReply.suggestReplies(conversation)
        .addOnSuccessListener { result ->
            if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                val replies = result.suggestions.map { it.text }
                onResult(replies)
            } else {
                onResult(listOf("No suggestions available"))
            }
        }
        .addOnFailureListener {
            onResult(listOf("Error: ${it.message}"))
        }
}