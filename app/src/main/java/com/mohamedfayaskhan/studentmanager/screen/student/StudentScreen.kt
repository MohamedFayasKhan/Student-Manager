package com.mohamedfayaskhan.studentmanager.screen.student

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.navigation.Router
import com.mohamedfayaskhan.studentmanager.util.StudentImpl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StudentScreen(
    dataViewModel: DataViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    if (studentImpl.read().isEmpty()) {
        studentImpl.refresh(null)
    }
    val students = studentImpl.read()
    val isDialogShown = remember {
        mutableStateOf(false)
    }
    val selectedStudent = remember {
        mutableStateOf(Student())
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(students) { student ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            navController.navigate(Router.ViewStudent.route + "/${student.id}")
                        },
                        onLongClick = {
                            isDialogShown.value = true
                            selectedStudent.value = student
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = student.name.toString(), modifier = Modifier.padding(16.dp))
                Text(text = student.grade.toString(), modifier = Modifier.padding(16.dp))
            }
            Divider()
        }
    }

    if (isDialogShown.value) {
        ShowEditDialogStudent(navController = navController, selectedStudent, dataViewModel) {
            isDialogShown.value = false
        }
    }
}

@Composable
private fun ShowEditDialogStudent(
    navController: NavHostController,
    selectedStudent: MutableState<Student>,
    dataViewModel: DataViewModel,
    onDismiss: () -> Unit
) {
    val isDialogShown = remember {
        mutableStateOf(false)
    }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Edit Student",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            navController.navigate(Router.AddStudent.route + "/${selectedStudent.value.id}")
                        }
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Delete Student",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            isDialogShown.value = true
                        })
                Spacer(modifier = Modifier.size(10.dp))
            }
        }
    }

    if (isDialogShown.value) {
        ShowDeleteDialogStudent(selectedStudent, dataViewModel) {
            isDialogShown.value = false
            onDismiss()
        }
    }
}

@Composable
private fun ShowDeleteDialogStudent(
    selectedStudent: MutableState<Student>,
    dataViewModel: DataViewModel,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Are you want to delete the Student ${selectedStudent.value.name}?",
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "No", modifier = Modifier
                        .weight(1f)
                        .clickable { onDismiss() })
                    Text(text = "Yes", modifier = Modifier
                        .weight(1f)
                        .clickable {
                            dataViewModel.deleteStudent(selectedStudent.value.id.toString()) {
                                onDismiss()
                            }
                        })
                }
            }
        }
    }
}
