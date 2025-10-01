package com.example.belambertrunetodoapp.data

import com.example.belambertrunetodoapp.models.ToDo
import com.example.belambertrunetodoapp.models.User
import java.util.Date

object MockupProvider {

    fun getUsers(): List<User> {
        return listOf(
            User(1, "dhostens", "Dirk", "Hostens", "pass1", true),
            User(2, "jdoe", "John", "Doe", "pass2", true),
            User(3, "asmith", "Alice", "Smith", "pass3", false)
        )
    }

    fun getToDos(): List<ToDo> {
        val users = getUsers()
        val userDirk = users[0]
        val userJohn = users[1]
        val userAlice = users[2]

        return listOf(
            ToDo(
                number = 1,
                title = "Setup Project",
                description = "Initialize the Android Studio project with basic structure.",
                createdByUser = userDirk,
                createdOnDate = Date(System.currentTimeMillis() - 100000000), // Some time ago
                assignedToUser = null,
                finishedOnDate = null,
                timeEstimated = 120, // 2 hours
                analysisDone = true,
                developmentDone = false,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ), // Status: NEW
            ToDo(
                number = 2,
                title = "Create Models",
                description = "Define User and ToDo data classes.",
                createdByUser = userDirk,
                createdOnDate = Date(System.currentTimeMillis() - 90000000),
                assignedToUser = userJohn,
                finishedOnDate = null,
                timeEstimated = 180, // 3 hours
                analysisDone = true,
                developmentDone = true,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ), // Status: ASSIGNED
            ToDo(
                number = 3,
                title = "Implement UI Layout",
                description = "Design the main screen layout using Jetpack Compose.",
                createdByUser = userJohn,
                createdOnDate = Date(System.currentTimeMillis() - 80000000),
                assignedToUser = userJohn,
                finishedOnDate = Date(System.currentTimeMillis() - 10000000), // Finished recently
                timeEstimated = 300, // 5 hours
                analysisDone = true,
                developmentDone = true,
                reviewAndTestingDone = true,
                acceptanceDone = true
            ), // Status: FINISHED
            ToDo(
                number = 4,
                title = "Add Sample Data",
                description = "Create a mockup data provider for testing.",
                createdByUser = userAlice,
                createdOnDate = Date(System.currentTimeMillis() - 70000000),
                assignedToUser = userDirk,
                finishedOnDate = null,
                timeEstimated = 90, // 1.5 hours
                analysisDone = true,
                developmentDone = false,
                reviewAndTestingDone = false,
                acceptanceDone = false
            ), // Status: ASSIGNED
            ToDo(
                number = 5,
                title = "Write Unit Tests",
                description = "Develop unit tests for the model classes.",
                createdByUser = userDirk,
                createdOnDate = Date(System.currentTimeMillis() - 60000000),
                assignedToUser = userAlice,
                finishedOnDate = Date(System.currentTimeMillis() - 5000000),
                timeEstimated = 240, // 4 hours
                analysisDone = true,
                developmentDone = true,
                reviewAndTestingDone = true,
                acceptanceDone = true
            ) // Status: FINISHED
        )
    }
}
