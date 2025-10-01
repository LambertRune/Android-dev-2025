package com.example.belambertrunetodoapp.models

import java.util.Date

data class ToDo(
    val number: Int,
    val title: String,
    val description: String,
    val createdByUser: User,
    val createdOnDate: Date,
    val assignedToUser: User?,
    val finishedOnDate: Date?,
    val timeEstimated: Int, // in minutes or hours? Assuming minutes for now
    val analysisDone: Boolean,
    val developmentDone: Boolean,
    val reviewAndTestingDone: Boolean,
    val acceptanceDone: Boolean
) {
    val status: Status
        get() {
            return if (assignedToUser != null && finishedOnDate != null) {
                Status.FINISHED
            } else if (assignedToUser != null) {
                Status.ASSIGNED
            } else {
                Status.NEW
            }
        }

    val statusDescription: String
        get() {
            return when (status) {
                Status.NEW -> "New"
                Status.ASSIGNED -> "Assigned"
                Status.FINISHED -> "Finished"
            }
        }

    val timeRemaining: Int // Assuming this is a percentage of timeEstimated
        get() {
            return when {
                acceptanceDone -> 0
                reviewAndTestingDone -> (timeEstimated * 0.10).toInt()
                developmentDone -> (timeEstimated * 0.30).toInt()
                analysisDone -> (timeEstimated * 0.85).toInt()
                else -> timeEstimated
            }
        }
}