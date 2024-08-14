package com.mohamedfayaskhan.studentmanager.util

import android.content.Context
import android.widget.Toast
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Assignment

class AssignmentImpl(private val dataViewModel: DataViewModel, private val context: Context) :
    Actions<Assignment> {

    override fun create(data: Assignment) {
        dataViewModel.upsertAssignment(data) {
            Toast.makeText(
                context,
                if (it) "Assignment created successful" else "Unable to create assignment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun update(data: Assignment) {
        dataViewModel.upsertAssignment(data) {
            Toast.makeText(
                context,
                if (it) "Assignment updated successful" else "Unable to update assignment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun read(): List<Assignment> {
        return dataViewModel.assignments
    }

    override fun delete(id: String) {
        dataViewModel.deleteAssignment(id) {
            Toast.makeText(
                context,
                if (it) "Assignment deleted successful" else "Unable to delete assignment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun refresh(studentId: String?) {
        dataViewModel.getAssignment(studentId!!)
    }

    override fun getUniqueId(): String {
        return dataViewModel.getUniqueId(TYPE.ASSIGNMENT)
    }
}