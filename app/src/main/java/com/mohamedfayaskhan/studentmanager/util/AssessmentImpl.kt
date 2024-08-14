package com.mohamedfayaskhan.studentmanager.util

import android.content.Context
import android.widget.Toast
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Assessment

class AssessmentImpl(private val dataViewModel: DataViewModel, private val context: Context) :
    Actions<Assessment> {

    override fun create(data: Assessment) {
        dataViewModel.upsertAssessment(data) {
            Toast.makeText(
                context,
                if (it) "Assessment created successful" else "Unable to create assessment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun update(data: Assessment) {
        dataViewModel.upsertAssessment(data) {
            Toast.makeText(
                context,
                if (it) "Assessment updated successful" else "Unable to update assessment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun read(): List<Assessment> {
        return dataViewModel.assessments
    }

    override fun delete(id: String) {
        dataViewModel.deleteAssessment(id) {
            Toast.makeText(
                context,
                if (it) "Assessment deleted successful" else "Unable to delete assessment",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun refresh(studentId: String?) {
        dataViewModel.getAssessment(studentId!!)
    }

    override fun getUniqueId(): String{
        return dataViewModel.getUniqueId(TYPE.ASSESSMENT)
    }

}