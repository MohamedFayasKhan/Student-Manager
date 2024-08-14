package com.mohamedfayaskhan.studentmanager.screen.student

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.util.StudentImpl

@Composable
fun AddStudent(
    dataViewModel: DataViewModel,
    navController: NavHostController,
    id: String?
) {
    val context = LocalContext.current
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    val name = remember {
        mutableStateOf("")
    }
    val grade = remember {
        mutableStateOf("")
    }
    if (id != null) {
        val student = studentImpl.read().filter { it.id == id }[0]
        name.value = student.name.toString()
        grade.value = student.grade.toString()
    }
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = if (id == null) "Create Student" else "Edit Student",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = {
                Text(text = "Name")
            },
            placeholder = {
                Text(text = "Enter Name")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = grade.value,
            onValueChange = { grade.value = it },
            label = {
                Text(text = "Grade")
            },
            placeholder = {
                Text(text = "Enter Grade")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                if (name.value.isNotEmpty() && grade.value.isNotEmpty()) {
                    studentImpl.create(
                        data = Student(
                            id ?: studentImpl.getUniqueId(),
                            name.value,
                            grade.value,
                            System.currentTimeMillis()
                        )
                    )
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Save")
        }
    }
}