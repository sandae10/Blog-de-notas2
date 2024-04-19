package com.example.secondapp.navigation

sealed class Screens(val route : String) {
    object TaskListScreen : Screens("taskList")
    object TaskDetailScreen : Screens("taskDetail")
}