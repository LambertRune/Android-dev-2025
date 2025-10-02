package com.example.belambertrunetodoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.belambertrunetodoapp.R
import com.example.belambertrunetodoapp.data.MockupProvider
import com.example.belambertrunetodoapp.models.ToDo
import com.example.belambertrunetodoapp.ui.theme.BeLambertRunetodoAppTheme

// --- Best Practice: Navigation Routes as a Sealed Class ---
sealed class Screen(val route: String) {
    object ToDoList : Screen("todolist")
    object ToDoDetail : Screen("tododetail/{todoNumber}") {
        fun createRoute(toDoNumber: Int) = "tododetail/$toDoNumber"
    }
    object ToDoEdit : Screen("todoedit/{todoNumber}") {
        fun createRoute(toDoNumber: Int) = "todoedit/$toDoNumber"
    }
}

// --- Navigation Setup ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoApp() {
    val navController = rememberNavController()
    var toDos by remember { mutableStateOf(MockupProvider.getToDos()) }
    val users = MockupProvider.getUsers()

    NavHost(navController = navController, startDestination = Screen.ToDoList.route) {
        composable(Screen.ToDoList.route) {
            ToDoListScreen(
                toDos = toDos,
                onToDoClick = { toDoNumber -> navController.navigate(Screen.ToDoDetail.createRoute(toDoNumber)) },
                onAddClick = { navController.navigate(Screen.ToDoEdit.createRoute(-1)) }
            )
        }
        composable(
            route = Screen.ToDoDetail.route,
            arguments = listOf(navArgument("todoNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoNumber = backStackEntry.arguments?.getInt("todoNumber")
            val toDo = toDos.find { it.number == todoNumber }
            if (toDo != null) {
                ToDoDetailScreen(
                    toDo = toDo,
                    onEditClick = { navController.navigate(Screen.ToDoEdit.createRoute(toDo.number)) },
                    onBack = { navController.popBackStack() }
                )
            } else { Text("ToDo not found!") }
        }
        composable(
            route = Screen.ToDoEdit.route,
            arguments = listOf(navArgument("todoNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoNumber = backStackEntry.arguments?.getInt("todoNumber")
            val toDo = if (todoNumber == -1) null else toDos.find { it.number == todoNumber }
            ToDoEditScreen(
                toDo = toDo,
                users = users,
                onSave = { updatedToDo ->
                    if (toDo == null) { // Add new
                        toDos = toDos + updatedToDo
                    } else { // Edit existing
                        toDos = toDos.map { if (it.number == updatedToDo.number) updatedToDo else it }
                    }
                    navController.popBackStack(Screen.ToDoList.route, inclusive = false)
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}

// --- List Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(toDos: List<ToDo>, onToDoClick: (Int) -> Unit, onAddClick: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My ToDo's") }) },
        floatingActionButton = { FloatingActionButton(onClick = onAddClick) { Icon(Icons.Default.Add, contentDescription = "Add ToDo") } },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        val sortedToDos = toDos.sortedBy { it.createdOnDate }
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(sortedToDos) { toDo ->
                ToDoListItem(toDo = toDo, modifier = Modifier.clickable { onToDoClick(toDo.number) })
                HorizontalDivider()
            }
        }
    }
}

// --- List Item ---
@Composable
fun ToDoListItem(toDo: ToDo, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(toDo.title, style = MaterialTheme.typography.titleMedium)
            Text("Status: ${toDo.statusDescription}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.width(16.dp))
        Text("#${toDo.number}", style = MaterialTheme.typography.titleMedium)
    }
}

// --- Detail Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDetailScreen(toDo: ToDo, onEditClick: () -> Unit, onBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ToDo Details") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") } },
                actions = { IconButton(onClick = onEditClick) { Icon(Icons.Default.Edit, "Edit ToDo") } }
            )
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(paddingValues).padding(16.dp).fillMaxSize()) {
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                Image(painterResource(R.drawable.schermafbeelding), "ToDo Logo", Modifier.size(70.dp))
                Spacer(Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.End, modifier = Modifier.weight(1f)) {
                    Text("#${toDo.number}", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                    Text(toDo.statusDescription, style = MaterialTheme.typography.titleMedium, fontStyle = FontStyle.Italic)
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.error, thickness = 1.dp)
            Spacer(Modifier.height(16.dp))
            Text(toDo.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(toDo.description, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 8.dp))
            Spacer(Modifier.height(8.dp))
            toDo.assignedToUser?.let {
                Text("Assigned to ${it.firstName} ${it.lastName}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(4.dp))
            }
            Text("Time estimated ${toDo.timeEstimated} hours", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(4.dp))
            Text("Time remaining ${toDo.timeRemaining} hours", style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(24.dp))
            Column(Modifier.border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.medium).padding(16.dp)) {
                SwitchRow("Analysis done?", toDo.analysisDone)
                SwitchRow("Development done?", toDo.developmentDone)
                SwitchRow("Review & testing done?", toDo.reviewAndTestingDone)
                SwitchRow("Acceptance done?", toDo.acceptanceDone)
            }
        }
    }
}

// --- Helper Composables & Previews ---
@Composable
fun SwitchRow(label: String, checked: Boolean) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = null)
    }
}

class ToDoPreviewParameterProvider : PreviewParameterProvider<ToDo> {
    override val values = MockupProvider.getToDos().asSequence()
}

@Preview(showBackground = true, name = "List Screen Preview")
@Composable
fun ToDoListScreenPreview() {
    BeLambertRunetodoAppTheme {
        ToDoListScreen(toDos = MockupProvider.getToDos(), onToDoClick = {}, onAddClick = {})
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Detail Screen Preview")
@Composable
fun ToDoDetailScreenPreview(@PreviewParameter(ToDoPreviewParameterProvider::class) toDo: ToDo) {
    BeLambertRunetodoAppTheme {
        ToDoDetailScreen(toDo, onEditClick = {}, onBack = {})
    }
}
