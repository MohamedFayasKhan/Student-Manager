package com.mohamedfayaskhan.studentmanager.util

import android.content.Context
import android.widget.Toast
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Subject

class SubjectImpl(private val dataViewModel: DataViewModel, private val context: Context): Actions<Subject> {
    override fun create(data: Subject) {
        dataViewModel.upsertSubject(data) {
            Toast.makeText(
                context,
                if (it) "Subject created successful" else "Unable to create subject",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun update(data: Subject) {
        dataViewModel.upsertSubject(data) {
            Toast.makeText(
                context,
                if (it) "Subject updated successful" else "Unable to update subject",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun read(): List<Subject> {
        return dataViewModel.subjects
    }

    override fun delete(id: String) {
        dataViewModel.deleteSubject(id) {
            Toast.makeText(
                context,
                if (it) "Subject deleted successful" else "Unable to delete subject",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun refresh(studentId: String?) {
        dataViewModel.getSubject()
    }

    override fun getUniqueId(): String {
        return dataViewModel.getUniqueId(TYPE.SUBJECT)
    }
}