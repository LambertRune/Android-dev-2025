package com.example.myapplication.ui.technical

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.myapplication.R

@Composable
fun toDoDetailScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "Detail",
            color = colorResource(id = R.color.purple_500)
        )
        HorizontalDivider(thickness = DividerDefaults.Thickness, color = Color.Green)
    }
}