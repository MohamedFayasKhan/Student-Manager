package com.mohamedfayaskhan.studentmanager.screen.attendance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Attendance
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.navigation.Router
import com.mohamedfayaskhan.studentmanager.util.AttendanceImpl
import com.mohamedfayaskhan.studentmanager.util.StudentImpl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttendanceScreen(
    dataViewModel: DataViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val attendanceImpl =
        AttendanceImpl(dataViewModel = dataViewModel, context = context) as Actions<Attendance>
    if (attendanceImpl.read().isEmpty()) {
        attendanceImpl.refresh(null)
    }
    val attendances = attendanceImpl.read()
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    if (studentImpl.read().isEmpty()) {
        studentImpl.refresh(null)
    }
    val students = studentImpl.read()
    val isDialogShown = remember {
        mutableStateOf(false)
    }
    val selectedAttendance = remember {
        mutableStateOf(Attendance())
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Text(
                text = "Attendance",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(attendances) { attendance ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .combinedClickable(
                        onClick = {
                            navController.navigate(Router.ViewAttendance.route + "/${attendance.id}")
                        },
                        onLongClick = {
                            isDialogShown.value = true
                            selectedAttendance.value = attendance
                        }
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = attendance.date.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "${attendance.presents?.size}/${students.size}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Divider()
        }
    }
    if (isDialogShown.value) {
        ShowEditDialogAttendance(navController = navController, selectedAttendance, dataViewModel) {
            isDialogShown.value = false
        }
    }
}

@Composable
private fun ShowEditDialogAttendance(
    navController: NavHostController,
    selectedAttendance: MutableState<Attendance>,
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
                    text = "Edit Attendance",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clickable {
                            navController.navigate(Router.AddAttendance.route + "/${selectedAttendance.value.id}")
                        }
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = "Delete Attendance",
                    style = MaterialTheme.typography.bodyLarge,
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
        ShowDeleteDialogAttendance(selectedAttendance, dataViewModel) {
            isDialogShown.value = false
            onDismiss()
        }
    }
}

@Composable
private fun ShowDeleteDialogAttendance(
    selectedAttendance: MutableState<Attendance>,
    dataViewModel: DataViewModel,
    onDismiss1: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss1) {
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
                    text = "Are you want to delete the Attendance on ${selectedAttendance.value.date}?",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "No",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismiss1() }
                    )
                    Text(
                        text = "Yes",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                dataViewModel.deleteAttendance(selectedAttendance.value.id.toString()) {
                                    onDismiss1()
                                }
                            }
                    )
                }
            }
        }
    }
}
