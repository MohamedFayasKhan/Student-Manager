package com.mohamedfayaskhan.studentmanager.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedfayaskhan.studentmanager.constant.Constant
import com.mohamedfayaskhan.studentmanager.data.DataViewModel

@Composable
fun HomeScreen(dataViewModel: DataViewModel) {
    dataViewModel.getStudents()
    val stats = dataViewModel.stats
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Welcome ${Constant.User}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Stats",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        items(stats) { studentWithMarks ->
            val (student, subjectMarksList) = studentWithMarks
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Name: ${student.name}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Grade: ${student.grade}",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                subjectMarksList.forEach { subjectMarks ->
                    Text(
                        text = "${subjectMarks.subject}: ${subjectMarks.marks}",
                        fontSize = 16.sp
                    )
                }
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}