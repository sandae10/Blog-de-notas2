package com.example.secondapp.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.secondapp.R
import com.example.secondapp.data.TaskList
import com.example.secondapp.viewmodel.ListDataManager

@Composable
fun TaskDetailScreenContent(
    modifier : Modifier = Modifier,
    taskTodos : List<String>
) {
    if (taskTodos.isEmpty()) {
        EmptyView(message = stringResource(id = R.string.text_no_todos_yet))
    } else {
        LazyColumn(
            modifier = modifier,
            content = {
                items(taskTodos) {
                    ListItemView(
                        value = it,
                        onClick = {
                            
                        }
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskName :  String?,
    onBackPressed: () -> Unit
) {
    val viewModel: ListDataManager = viewModel()
    var taskTodos by remember {
        mutableStateOf(viewModel.readLists().firstOrNull() { it.name == taskName}
            ?.tasks ?: emptyList())
    }
    Scaffold(
        topBar = {
            ListMakerTopAppBar(
                title = taskName ?: stringResource(id = R.string.label_task_list),
                showBackButton = true,
                onBackPressed = onBackPressedg
            )
        },
        content = {
            TaskDetailScreenContent(
                modifier = Modifier.padding(it),
                taskTodos = taskTodos,
            )
        },
        floatingActionButton = {
            ListMakerFloatingActionButton(
                title = stringResource(id = R.string.task_to_add),
                inputHint = stringResource(id = R.string.task_hint),
                onFabClick = { todoName ->
                    viewModel.saveList(TaskList(
                        taskName ?: "",
                        taskTodos + listOf(todoName)
                    ))
                    taskTodos = viewModel.readLists().firstOrNull() {
                        it.name == taskName
                    }?.tasks ?: emptyList()
                }
            )
        }
    )
}