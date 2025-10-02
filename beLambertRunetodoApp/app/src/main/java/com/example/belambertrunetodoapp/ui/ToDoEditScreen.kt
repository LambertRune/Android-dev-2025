package com.example.belambertrunetodoapp.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.belambertrunetodoapp.models.ToDo
import com.example.belambertrunetodoapp.models.User
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoEditScreen(
    toDo: ToDo?,
    users: List<User>,
    onSave: (ToDo) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isEditMode = toDo != null
    val screenTitle = if (isEditMode) "Edit ToDo" else "Add ToDo"

    // State for the form fields
    var title by remember { mutableStateOf(toDo?.title ?: "") }
    var description by remember { mutableStateOf(toDo?.description ?: "") }
    var timeEstimated by remember { mutableStateOf(toDo?.timeEstimated?.toString() ?: "") }
    var assignedTo by remember { mutableStateOf(toDo?.assignedToUser) }
    var analysisDone by remember { mutableStateOf(toDo?.analysisDone ?: false) }
    var developmentDone by remember { mutableStateOf(toDo?.developmentDone ?: false) }
    var reviewAndTestingDone by remember { mutableStateOf(toDo?.reviewAndTestingDone ?: false) }
    var acceptanceDone by remember { mutableStateOf(toDo?.acceptanceDone ?: false) }

    var isUserDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text(screenTitle) }) },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth().height(120.dp))
            OutlinedTextField(
                value = timeEstimated,
                onValueChange = { timeEstimated = it },
                label = { Text("Time Estimated (hours)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(expanded = isUserDropdownExpanded, onExpandedChange = { isUserDropdownExpanded = !isUserDropdownExpanded }) {
                OutlinedTextField(
                    value = assignedTo?.userName ?: "Select User (Optional)",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Assign To") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isUserDropdownExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = isUserDropdownExpanded, onDismissRequest = { isUserDropdownExpanded = false }) {
                    DropdownMenuItem(text = { Text("None") }, onClick = { assignedTo = null; isUserDropdownExpanded = false })
                    users.forEach { user ->
                        DropdownMenuItem(text = { Text("${user.firstName} ${user.lastName}") }, onClick = { assignedTo = user; isUserDropdownExpanded = false })
                    }
                }
            }

            Text("Progress:", style = MaterialTheme.typography.titleMedium)
            Column(modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium).padding(16.dp)) {
                EditableSwitchRow("Analysis Done", analysisDone) { analysisDone = it }
                EditableSwitchRow("Development Done", developmentDone) { developmentDone = it }
                EditableSwitchRow("Review & Testing Done", reviewAndTestingDone) { reviewAndTestingDone = it }
                EditableSwitchRow("Acceptance Done", acceptanceDone) { acceptanceDone = it }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.padding(end = 16.dp)) { Text("Cancel") }
                Button(onClick = {
                    val timeEst = timeEstimated.toIntOrNull() ?: 0
                    val finalToDo = if (isEditMode) {
                        // Edit Mode: Copy the existing ToDo with updated values
                        toDo!!.copy(
                            title = title,
                            description = description,
                            timeEstimated = timeEst,
                            assignedToUser = assignedTo,
                            analysisDone = analysisDone,
                            developmentDone = developmentDone,
                            reviewAndTestingDone = reviewAndTestingDone,
                            acceptanceDone = acceptanceDone,
                            finishedOnDate = if (acceptanceDone && toDo.finishedOnDate == null) Date() else if (!acceptanceDone) null else toDo.finishedOnDate
                        )
                    } else {
                        // Add Mode: Create a new ToDo with all values from the form
                        ToDo(
                            number = (100..999).random(), // Dummy number
                            title = title,
                            description = description,
                            createdByUser = users.first(), // Placeholder for logged-in user
                            createdOnDate = Date(),
                            assignedToUser = assignedTo,
                            finishedOnDate = if (acceptanceDone) Date() else null,
                            timeEstimated = timeEst,
                            analysisDone = analysisDone,
                            developmentDone = developmentDone,
                            reviewAndTestingDone = reviewAndTestingDone,
                            acceptanceDone = acceptanceDone
                        )
                    }
                    onSave(finalToDo)
                }) { Text("Save") }
            }
        }
    }
}

@Composable
fun EditableSwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
