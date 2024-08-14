package com.mohamedfayaskhan.studentmanager.screen.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Attendance
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.util.AttendanceImpl
import com.mohamedfayaskhan.studentmanager.util.StudentImpl

@Composable
fun ViewAttendance(
    dataViewModel: DataViewModel,
    id: String
) {
    val context = LocalContext.current
    val attendanceImpl =
        AttendanceImpl(dataViewModel = dataViewModel, context = context) as Actions<Attendance>
    if (attendanceImpl.read().isEmpty()) {
        attendanceImpl.refresh(null)
    }
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    if (studentImpl.read().isEmpty()) {
        studentImpl.refresh(null)
    }
    val students = studentImpl.read()
    val attendances = attendanceImpl.read()
    val selectedAttendance = attendances.filter { it.id == id }[0]
    LazyColumn {
        item {
            Text(text = "Date : ${selectedAttendance.date}", modifier = Modifier.padding(16.dp))
        }
        items(students) { student ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = student.name.toString())
                Text(
                    text = if (selectedAttendance.presents?.contains(student.id.toString()) == true) "Present" else "Absent"
                )
            }
        }
    }
}