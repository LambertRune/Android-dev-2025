package com.example.belambertrunetodoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource // Added import
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
// Removed unused background import and sp import if it was still there
// import androidx.compose.ui.graphics.Color (Removed if only used for placeholder)
// import androidx.compose.foundation.background (Removed if only used for placeholder)
import com.example.belambertrunetodoapp.R // Added import for R.drawable
import com.example.belambertrunetodoapp.data.MockupProvider
import com.example.belambertrunetodoapp.models.ToDo
import com.example.belambertrunetodoapp.ui.theme.BeLambertRunetodoAppTheme

@Composable
fun ToDoDetailScreen(toDo: ToDo, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Top Row: Image, Number, Status
        Row(
            verticalAlignment = Alignment.Top, // Align items to the top of the row
            modifier = Modifier.fillMaxWidth()
        ) {
            // Image (Logo)
            Image(
                painter = painterResource(id = R.drawable.schermafbeelding),
                contentDescription = "ToDo Logo",
                modifier = Modifier.size(width = 70.dp, height = 70.dp) // Adjusted size for logo
            )
            // Removed Box placeholder
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                Text(
                    text = "#${toDo.number}",
                    style = MaterialTheme.typography.headlineLarge, // Larger text for number
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = toDo.statusDescription,
                    style = MaterialTheme.typography.titleMedium, // Adjusted style
                    fontStyle = FontStyle.Italic // Italicized status
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Red Divider
        HorizontalDivider(color = MaterialTheme.colorScheme.error, thickness = 1.dp) // Using theme color for red

        Spacer(modifier = Modifier.height(16.dp))

        // Title and Description
        Text(
            text = toDo.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = toDo.description,
            style = MaterialTheme.typography.bodyLarge, // Slightly larger body text
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Assigned User
        toDo.assignedToUser?.let {
            Text(
                text = "Assigned to ${it.firstName} ${it.lastName}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Time Estimated and Time Remaining
        Text("Time estimated ${toDo.timeEstimated} hours", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(4.dp))
        Text("Time remaining ${toDo.timeRemaining} hours", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Boolean Fields with Switches (Outlined Section)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline, shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        ) {
            SwitchRow("Analysis done?", toDo.analysisDone)
            SwitchRow("Development done?", toDo.developmentDone)
            SwitchRow("Review & testing done?", toDo.reviewAndTestingDone) // Updated label
            SwitchRow("Acceptance done?", toDo.acceptanceDone)
        }
    }
}

@Composable
fun SwitchRow(label: String, checked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Increased padding for better spacing
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = null // Read-only
        )
    }
}

// Preview data of ToDo
class ToDoPreviewParameterProvider : PreviewParameterProvider<ToDo> {
    private val toDos = MockupProvider.getToDos()
    override val values = toDos.asSequence()
}

@Preview(showBackground = true, showSystemUi = true, name = "ToDo Detail Screen Preview")
@Composable
fun ToDoDetailScreenPreview(@PreviewParameter(ToDoPreviewParameterProvider::class, limit = 3) toDo: ToDo) {
    BeLambertRunetodoAppTheme {
        ToDoDetailScreen(toDo)
    }
}
