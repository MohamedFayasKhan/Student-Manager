package com.mohamedfayaskhan.studentmanager.screen.attendance

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.Constant
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Attendance
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.model.StudentPresent
import com.mohamedfayaskhan.studentmanager.util.AttendanceImpl
import com.mohamedfayaskhan.studentmanager.util.StudentImpl
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddAttendance(
    dataViewModel: DataViewModel,
    navController: NavHostController,
    id: String?
) {
    val date = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val attendanceImpl =
        AttendanceImpl(dataViewModel = dataViewModel, context = context) as Actions<Attendance>
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    if (studentImpl.read().isEmpty()) {
        studentImpl.refresh(null)
    }
    val studentPresentList = remember {
        mutableListOf<StudentPresent>()
    }
    val selectedStudents = remember {
        mutableListOf<String>()
    }
    val students = studentImpl.read()
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constant.DateFormat.value, Locale.getDefault())
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            date.value = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    date.value = dateFormat.format(calendar.time)
    if (id != null) {
        val attendance = attendanceImpl.read().filter { it.id == id }[0]
        selectedStudents.clear()
        selectedStudents.addAll(attendance.presents as List<String>)
        date.value = attendance.date.toString()
    }
    LaunchedEffect(Unit) {
        for (student in students) {
            if (selectedStudents.contains(student.id)) {
                studentPresentList.add(StudentPresent(student, true))
            } else {
                studentPresentList.add(StudentPresent(student))
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = if (id == null) "Create Attendance" else "Edit Attendance",
                modifier = Modifier.padding(16.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            OutlinedTextField(
                value = date.value,
                onValueChange = { date.value = it },
                label = {
                    Text(text = "Date")
                },
                placeholder = {
                    Text(text = "Enter Date")
                },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Calendar",
                        modifier = Modifier.clickable {
                            datePickerDialog.show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        items(studentPresentList) { stPr ->
            var isChecked by remember { mutableStateOf(stPr.isPresent) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(checked = isChecked, onCheckedChange = { checked ->
                    isChecked = checked
                    stPr.isPresent = checked
                    if (checked) {
                        selectedStudents.add(stPr.student.id.toString())
                    } else {
                        selectedStudents.remove(stPr.student.id.toString())
                    }
                })
                Text(
                    text = stPr.student.name.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        item {
            Button(
                onClick = {
                    attendanceImpl.create(
                        data = Attendance(
                            id ?: attendanceImpl.getUniqueId(),
                            date.value,
                            selectedStudents,
                            System.currentTimeMillis()
                        )
                    )
                    navController.popBackStack()
                }
            ) {
                Text(text = "Save")
            }
        }
    }
}