package com.mohamedfayaskhan.studentmanager.util

import android.content.Context
import android.widget.Toast
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Student

class StudentImpl(private val dataViewModel: DataViewModel, private val context: Context) :
    Actions<Student> {

    override fun create(data: Student) {
        dataViewModel.upsertStudent(data) {
            Toast.makeText(
                context,
                if (it) "Student created successful" else "Unable to create student",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun update(data: Student) {
        dataViewModel.upsertStudent(data) {
            Toast.makeText(
                context,
                if (it) "Student updated successful" else "Unable to update student",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun read(): List<Student> {
        return dataViewModel.students
    }

    override fun delete(id: String) {
        dataViewModel.deleteStudent(id) {
            Toast.makeText(
                context,
                if (it) "Student deleted successful" else "Unable to delete student",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun refresh(studentId: String?) {
        dataViewModel.getStudents()
    }

    override fun getUniqueId(): String {
        return dataViewModel.getUniqueId(TYPE.STUDENT)
    }
}