package com.example.belambertrunetodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.belambertrunetodoapp.ui.ToDoApp // Updated import
import com.example.belambertrunetodoapp.ui.theme.BeLambertRunetodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BeLambertRunetodoAppTheme {
                // Set the ToDoApp composable as the content
                // This now contains both the list and detail screens
                ToDoApp()
            }
        }
    }
}
