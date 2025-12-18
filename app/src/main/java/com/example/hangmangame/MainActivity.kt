package com.example.hangmangame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangmangame.ui.theme.HangmanGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HangmanGameTheme {
                HangmanUI()
            }
        }
    }
}

@Composable
fun HangmanUI() {
    var word by remember { mutableStateOf("APPLE") } // Random word (fixed for simplicity)
    var guessedLetters by remember { mutableStateOf(mutableListOf<Char>()) }
    var attemptsLeft by remember { mutableStateOf(6) }
    var message by remember { mutableStateOf("") }

    fun guessLetter(letter: Char) {
        if (!guessedLetters.contains(letter)) {
            guessedLetters.add(letter)
            if (!word.contains(letter)) {
                attemptsLeft--
            }
            if (attemptsLeft <= 0) message = "You lost! Word: $word"
            if (word.all { guessedLetters.contains(it) }) message = "You won!"
        }
    }

    fun resetGame() {
        word = "APPLE" // For simplicity, same word each time
        guessedLetters.clear()
        attemptsLeft = 6
        message = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display word
        Text(
            text = word.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" "),
            fontSize = 32.sp
        )

        // Display attempts left
        Text(
            text = "Attempts left: $attemptsLeft",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Message (win/lose)
        if (message.isNotEmpty()) {
            Text(
                text = message,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Letter buttons (A-Z)
        val letters = ('A'..'Z').toList()
        Column(modifier = Modifier.padding(top = 16.dp)) {
            letters.chunked(7).forEach { row ->
                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                    row.forEach { letter ->
                        Button(
                            onClick = { guessLetter(letter) },
                            modifier = Modifier.padding(end = 4.dp)
                        ) {
                            Text(letter.toString())
                        }
                    }
                }
            }
        }

        // Restart button
        Button(
            onClick = { resetGame() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Restart")
        }
    }
}
