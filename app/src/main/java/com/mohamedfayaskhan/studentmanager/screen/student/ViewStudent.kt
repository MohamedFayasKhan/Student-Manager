package com.mohamedfayaskhan.studentmanager.screen.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Assessment
import com.mohamedfayaskhan.studentmanager.model.Assignment
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.model.Subject
import com.mohamedfayaskhan.studentmanager.navigation.Router
import com.mohamedfayaskhan.studentmanager.screen.AssessmentScreen
import com.mohamedfayaskhan.studentmanager.screen.AssignmentScreen
import com.mohamedfayaskhan.studentmanager.util.AssessmentImpl
import com.mohamedfayaskhan.studentmanager.util.AssignmentImpl
import com.mohamedfayaskhan.studentmanager.util.StudentImpl
import com.mohamedfayaskhan.studentmanager.util.SubjectImpl

@Composable
fun ViewStudent(
    dataViewModel: DataViewModel,
    studentId: String
) {
    val context = LocalContext.current
    val studentImpl =
        StudentImpl(dataViewModel = dataViewModel, context = context) as Actions<Student>
    if (studentImpl.read().isEmpty()) {
        studentImpl.refresh(null)
    }
    val students = studentImpl.read()
    val selectedStudent = students.filter { it.id == studentId }[0]
    val assessmentImpl = AssessmentImpl(dataViewModel, context) as Actions<Assessment>
    assessmentImpl.refresh(studentId)
    val assessments = assessmentImpl.read()
    val assignmentImpl = AssignmentImpl(dataViewModel, context) as Actions<Assignment>
    assignmentImpl.refresh(studentId)
    val assignments = assignmentImpl.read()
    val subjectImpl = SubjectImpl(dataViewModel, context) as Actions<Subject>
    if (subjectImpl.read().isEmpty()) {
        subjectImpl.refresh(null)
    }
    val subjects = subjectImpl.read()
    val tabItems = listOf(
        Router.Assessment,
        Router.Assignment
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Name: ${selectedStudent.name}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Grade: ${selectedStudent.grade}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItems.forEachIndexed { index, router ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(text = router.title)
                    },
                    icon = {
                        Icon(
                            painter = if (index == selectedTabIndex) {
                                painterResource(id = router.selectedIcon)
                            } else {
                                painterResource(id = router.unSelectedIcon)
                            },
                            contentDescription = router.title
                        )
                    }
                )
            }
        }
        when (selectedTabIndex) {
            0 -> AssessmentScreen(assessments, assessmentImpl, studentId, subjects, subjectImpl)
            1 -> AssignmentScreen(assignments, assignmentImpl, studentId, subjects, subjectImpl)
        }
    }
}