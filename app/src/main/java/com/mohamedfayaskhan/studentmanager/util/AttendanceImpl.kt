package com.mohamedfayaskhan.studentmanager.util

import android.content.Context
import android.widget.Toast
import com.mohamedfayaskhan.studentmanager.actions.Actions
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.model.Attendance

class AttendanceImpl(private val dataViewModel: DataViewModel, private val context: Context) :
    Actions<Attendance> {

    override fun create(data: Attendance) {
        dataViewModel.upsertAttendance(data) {
            Toast.makeText(
                context,
                if (it) "Attendance created successful" else "Unable to create attendance",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun update(data: Attendance) {
        dataViewModel.upsertAttendance(data) {
            Toast.makeText(
                context,
                if (it) "Attendance updated successful" else "Unable to update attendance",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun read(): List<Attendance> {
        return dataViewModel.attendances
    }

    override fun delete(id: String) {
        dataViewModel.deleteAttendance(id) {
            Toast.makeText(
                context,
                if (it) "Attendance deleted successful" else "Unable to delete attendance",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun refresh(studentId: String?) {
        dataViewModel.getAttendance()
    }

    override fun getUniqueId(): String {
        return dataViewModel.getUniqueId(TYPE.ATTENDANCE)
    }

}