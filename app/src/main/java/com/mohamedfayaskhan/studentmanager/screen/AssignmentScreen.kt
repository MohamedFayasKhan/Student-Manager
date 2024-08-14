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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.Constant
import com.mohamedfayaskhan.studentmanager.model.Assignment
import com.mohamedfayaskhan.studentmanager.model.Subject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AssignmentScreen(
    assignments: List<Assignment>,
    assignmentImpl: Actions<Assignment>,
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
        mutableStateOf(Assignment())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(assignments) {assignment ->
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
                            text = assignment.title.toString(),
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
                                        selected = assignment
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
                                        selected = assignment
                                    }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(100.dp)
                                .align(Alignment.Center)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Description: ${assignment.description}",
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                                Text(
                                    text = "Remarks: ${assignment.remarks}",
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        .padding(8.dp)
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "${assignment.status}",
                                    fontSize = 16.sp
                                )
                                Divider(thickness = 2.dp)
                                Text(
                                    text = "${assignment.dueDate}",
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Text(
                            text = assignment.date.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        )
                        Text(
                            text = assignment.subject?.name.toString(),
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
            onClick = { isDialogShown.value = true }
        ) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = null)
        }
    }
    if (isDialogShown.value) {
        AssignmentCreateDialog(assignmentImpl , studentId, subjects, subjectImpl, isEdit, selected){
            isDialogShown.value = false
        }
    }
    if (isEdit.value) {
        AssignmentCreateDialog(assignmentImpl , studentId, subjects, subjectImpl, isEdit, selected){
            isEdit.value = false
        }
    }
    if (isDelete.value) {
        ShowDeleteDialogAssignment(assignment = selected, assignmentImpl) {
            isDelete.value = false
        }
    }
}

@Composable
private fun ShowDeleteDialogAssignment(
    assignment: Assignment,
    assignmentImpl: Actions<Assignment>,
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
                    text = "Are you want to delete the assignment ${assignment.title}?",
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
                            assignmentImpl.delete(assignment.id!!)
                            onDismiss()
                        })
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentCreateDialog(
    assignmentImpl: Actions<Assignment>,
    studentId: String,
    subjects: List<Subject>,
    subjectImpl: Actions<Subject>,
    edit: MutableState<Boolean>,
    selected: Assignment,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var date by remember {
        mutableStateOf("")
    }
    var dueDate by remember {
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
    var status by remember {
        mutableStateOf("")
    }
    var remarks by remember {
        mutableStateOf("")
    }
    var expandedSubject by remember {
        mutableStateOf(false)
    }
    var expandedStatus by remember {
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
    val dueDatePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            dueDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    if (edit.value) {
        date = selected.date.toString()
        dueDate = selected.dueDate.toString()
        title = selected.title.toString()
        description = selected.description.toString()
        subject = selected.subject!!
        status = selected.status.toString()
        remarks = selected.remarks.toString()
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
                    onValueChange = {date = it},
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
                    value = dueDate,
                    onValueChange = {dueDate = it},
                    label = {
                        Text(text = "Due Date")
                    },
                    readOnly = true,
                    singleLine = true,
                    placeholder = {
                        Text(text = "Enter Due Date")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Calendar",
                            modifier = Modifier.clickable {
                                dueDatePickerDialog.show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {title = it},
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
                    onValueChange = {description = it},
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
                ExposedDropdownMenuBox(expanded = expandedSubject, onExpandedChange = {expandedSubject= it}) {
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
                    DropdownMenu(expanded = expandedSubject, onDismissRequest = { expandedSubject = false }) {
                        mergeAddSubject(subjects).forEach {subject1 ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(text = subject1.name.toString()) },
                                onClick = {
                                    subject = subject1
                                    isNewSubjectVisible = subject1.id == Constant.AddSubjectId.value
                                    expandedSubject = false
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
                        onValueChange = {newSubject = it},
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
                ExposedDropdownMenuBox(expanded = expandedStatus, onExpandedChange = {expandedStatus= it}) {
                    OutlinedTextField(
                        value = status,
                        onValueChange = {
                            status = it
                        },
                        readOnly = true,
                        singleLine = true,
                        label = {
                            Text(text = "Status")
                        },
                        placeholder = {
                            Text(text = "Enter status")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    DropdownMenu(expanded = expandedStatus, onDismissRequest = { expandedStatus = false }) {
                        listOf("Not Started", "In Progress", "On Hold", "Completed", "Dropped", "Retry").forEach { status1 ->
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = { Text(text = status1) },
                                onClick = {
                                    status = status1
                                    expandedStatus = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedTextField(
                    value = remarks,
                    onValueChange = {remarks = it},
                    label = {
                        Text(text = "Remarks")
                    },
                    maxLines = 5,
                    placeholder = {
                        Text(text = "Enter remarks")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    if (newSubject.isNotEmpty()) {
                        if (date.isNotEmpty() && title.isNotEmpty() && dueDate.isNotEmpty() && status.isNotEmpty()) {
                            val id = subjectImpl.getUniqueId()
                            subjectImpl.create(Subject(id, newSubject))
                            assignmentImpl.create(
                                Assignment(
                                    if (edit.value) selected.id else assignmentImpl.getUniqueId(),
                                    date,
                                    dueDate,
                                    title,
                                    description,
                                    Subject(id, newSubject),
                                    status,
                                    remarks,
                                    studentId,
                                    System.currentTimeMillis()
                                )
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(context, "Date, Title, Due date and Status required", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        if (date.isNotEmpty() && title.isNotEmpty() && dueDate.isNotEmpty() && status.isNotEmpty() && subject.name != null && subject.id != null) {
                            assignmentImpl.create(
                                Assignment(
                                    if (edit.value) selected.id else assignmentImpl.getUniqueId(),
                                    date,
                                    dueDate,
                                    title,
                                    description,
                                    subject,
                                    status,
                                    remarks,
                                    studentId,
                                    System.currentTimeMillis()
                                )
                            )
                            onDismiss()
                        } else {
                            Toast.makeText(context, "Date, Title, Due date, Subject and Status required", Toast.LENGTH_LONG).show()
                        }
                    }
                    edit.value = false
                }) {
                    Text(text = "Save")
                }
            }
        }
    }
}
