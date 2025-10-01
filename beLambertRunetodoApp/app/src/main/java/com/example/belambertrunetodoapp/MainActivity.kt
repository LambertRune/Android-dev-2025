package com.example.belambertrunetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.belambertrunetodoapp.data.MockupProvider // Added import
import com.example.belambertrunetodoapp.ui.ToDoDetailScreen // Added import
import com.example.belambertrunetodoapp.ui.theme.BeLambertRunetodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeLambertRunetodoAppTheme {
                // Get a sample ToDo item
                val sampleToDo = MockupProvider.getToDos().firstOrNull()
                // Display the ToDoDetailScreen if a sample ToDo is available
                if (sampleToDo != null) {
                    ToDoDetailScreen(toDo = sampleToDo, modifier = Modifier.fillMaxSize())
                } else {
                    // Fallback if no ToDo items are available (optional)
                    // You could display a message or an empty state here
                }
            }
        }
    }
}
// Removed Greeting composable and its preview
