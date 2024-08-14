package com.mohamedfayaskhan.studentmanager.screen

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.Constant
import com.mohamedfayaskhan.studentmanager.model.Assessment
import com.mohamedfayaskhan.studentmanager.model.Subject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AssessmentScreen(
    assessments: List<Assessment>,
    assessmentImpl: Actions<Assessment>,
    studentId: String,
    subjects: List<Subject>,
    subjectImpl: Actions<Subject>
) {
    val isDialogShown = remember {
        mutableStateOf(false)
    }
    val isEdit = remember {
        mutableStateOf(false)
    }
    val isDelete = remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf(Assessment())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(assessments) { assessment ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .heightIn(min = 200.dp, max = 300.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = assessment.title.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(8.dp)
                        )
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        isEdit.value = true
                                        selected = assessment
                                    }
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        isDelete.value = true
                                        selected = assessment
                                    }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        ) {
                            Text(
                                text = assessment.description.toString(),
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.75f)
                                    .padding(8.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.25f)
                                    .padding(8.dp)
                            ) {
                                Text(//String.format(Locale.getDefault(), "%.2f", party.balance.toDouble())
                                    text = String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        assessment.scoredMark
                                    ),
                                    fontSize = 16.sp
                                )
                                Divider(modifier = Modifier.width(50.dp), thickness = 2.dp)
                                Text(
                                    text = String.format(
                                        Locale.getDefault(),
                                        "%.2f",
                                        assessment.totalMark
                                    ),
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Text(
                            text = assessment.date.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        )
                        Text(
                            text = assessment.subject?.name.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(30.dp),
            onClick = {
                isDialogShown.value = true
            }
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }
    if (isDialogShown.value) {
        AssessmentCreateDialog(assessmentImpl, studentId, subjects, subjectImpl, isEdit, selected) {
            isDialogShown.value = false
        }
    }
    if (isEdit.value) {
        AssessmentCreateDialog(assessmentImpl, studentId, subjects, subjectImpl, isEdit, selected) {
            isEdit.value = false
        }
    }
    if (isDelete.value) {
        ShowDeleteDialogAssessment(assessment = selected, assessmentImpl) {
            isDelete.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentCreateDialog(
    assessmentImpl: Actions<Assessment>,
    studentId: String,
    subjects: List<Subject>,
    subjectImpl: Actions<Subject>,
    isEdit: MutableState<Boolean>,
    selected: Assessment,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf("")
    }
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var subject by remember {
        mutableStateOf(Subject())
    }
    var scoredMark by remember {
        mutableStateOf("")
    }
    var totalMark by remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var newSubject by remember {
        mutableStateOf("")
    }
    var isNewSubjectVisible by remember {
        mutableStateOf(false)
    }
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat(Constant.DateFormat.value, Locale.getDefault())
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            date = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    if (isEdit.value) {
        date = selected.date.toString()
        title = selected.title.toString()
        description = selected.description.toString()
        scoredMark = selected.scoredMark.toString()
        totalMark = selected.totalMark.toString()
        subject = selected.subject!!
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
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = {
                        Text(text = "Date")
                    },
                    readOnly = true,
                    singleLine = true,
                    placeholder = {
                        Text(text = "Enter Date")
                    },
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
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(text = "Title")
                    },
                    singleLine = true,
                    placeholder = {
                        Text(text = "Enter title")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(text = "Description")
                    },
                    maxLines = 5,
                    placeholder = {
                        Text(text = "Enter description")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                ExposedDropdownMenuBox(expanded = false, onExpandedChange = { expanded = it }) {
                    OutlinedTextField(
                        value = subject.name.toString(),
                        onValueChange = {
                            subject.name = it
                        },
                        readOnly = true,
                        singleLine = true,
                        label = {
                            Text(text = "Subject")
                        },
                        placeholder = {
                            Text(text = "Enter subject")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        mergeAddSubject(subjects).forEach { subject1 ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(text = subject1.name.toString()) },
                                onClick = {
                                    subject = subject1
                                    isNewSubjectVisible = subject1.id == Constant.AddSubjectId.value
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                if (isNewSubjectVisible) {
                    OutlinedTextField(
                        value = newSubject,
                        onValueChange = { newSubject = it },
                        singleLine = true,
                        label = {
                            Text(text = "New Subject")
                        },
                        placeholder = {
                            Text(text = "Enter new Subject")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }
                OutlinedTextField(
                    value = scoredMark,
                    onValueChange = { scoredMark = it },
                    singleLine = true,
                    label = {
                        Text(text = "Scored Mark")
                    },
                    placeholder = {
                        Text(text = "Enter scored mark")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = totalMark,
                    onValueChange = { totalMark = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    label = {
                        Text(text = "Total Mark")
                    },
                    placeholder = {
                        Text(text = "Enter total mark")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    if (newSubject.isNotEmpty()) {
                        if (date.isNotEmpty() && title.isNotEmpty() && totalMark.isNotEmpty()) {
                            val id = subjectImpl.getUniqueId()
                            subjectImpl.create(Subject(id, newSubject))
                            assessmentImpl.create(
                                data = Assessment(
                                    if (isEdit.value) selected.id else assessmentImpl.getUniqueId(),
                                    date,
                                    title,
                                    description,
                                    Subject(id, newSubject),
                                    if (scoredMark.isEmpty()) null else scoredMark.toDouble(),
                                    if (totalMark.isEmpty()) null else totalMark.toDouble(),
                                    studentId,
                                    System.currentTimeMillis()
                                )
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(context, "Date, Title, NewSubject and Total mark required", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        if (date.isNotEmpty() && title.isNotEmpty() && totalMark.isNotEmpty() && subject.name != null && subject.id != null) {
                            assessmentImpl.create(
                                data = Assessment(
                                    if (isEdit.value) selected.id else assessmentImpl.getUniqueId(),
                                    date,
                                    title,
                                    description,
                                    subject,
                                    if (scoredMark.isEmpty()) null else scoredMark.toDouble(),
                                    if (totalMark.isEmpty()) null else totalMark.toDouble(),
                                    studentId,
                                    System.currentTimeMillis()
                                )
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(context, "Date, Title, Subject and Total mark required", Toast.LENGTH_LONG).show()
                        }
                    }
//                    assessmentImpl.create(studentId = studentId, data = Assessment(assessmentImpl.getUniqueId(), date, title, description, subject, if (scoredMark.isEmpty()) null else scoredMark.toDouble(), if (totalMark.isEmpty()) null else totalMark.toDouble(), System.currentTimeMillis()))
                    isEdit.value = false
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
private fun ShowDeleteDialogAssessment(
    assessment: Assessment,
    assessmentImpl: Actions<Assessment>,
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
                    text = "Are you want to delete the assessment ${assessment.title}?",
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
                            assessmentImpl.delete(assessment.id!!)
                            onDismiss()
                        })
                }
            }
        }
    }
}

fun mergeAddSubject(subjectsDb: List<Subject>): List<Subject> {
    val subjects = mutableListOf<Subject>()
    subjects.add(Subject(Constant.AddSubjectId.value, Constant.AddSubjectValue.value))
    if (subjectsDb.isNotEmpty()) {
        subjects.addAll(subjectsDb)
    }
    return subjects
}