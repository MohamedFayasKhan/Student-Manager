package com.mohamedfayaskhan.studentmanager.data

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mohamedfayaskhan.studentmanager.actions.DataFetcher
import com.mohamedfayaskhan.studentmanager.constant.Constant
import com.mohamedfayaskhan.studentmanager.model.Assessment
import com.mohamedfayaskhan.studentmanager.model.Assignment
import com.mohamedfayaskhan.studentmanager.model.Attendance
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.model.Subject

class DataRepository(private val database: FirebaseFirestore) {

    fun upsertStudent(student: Student, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Student.value).document(student.id.toString()).set(student)
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun deleteStudent(id: String, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Student.value).document(id).delete()
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun getStudents(fetcher: DataFetcher<Student>) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Student.value).addSnapshotListener { values, _ ->
            val students = mutableListOf<Student>()
            for (document in values!!) {
                val student = document.toObject(Student::class.java)
                students.add(student)
            }
            fetcher.getListData(students)
        }
    }

    fun upsertAttendance(attendance: Attendance, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Attendance.value).document(attendance.id.toString())
            .set(attendance)
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun deleteAttendance(id: String, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Attendance.value).document(id).delete()
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun getAttendance(fetcher: DataFetcher<Attendance>) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Attendance.value).orderBy("createdTime", Query.Direction.DESCENDING).addSnapshotListener { values, _ ->
            val attendances = mutableListOf<Attendance>()
            for (document in values!!) {
                val attendance = document.toObject(Attendance::class.java)
                attendances.add(attendance)
            }
            fetcher.getListData(attendances)
        }
    }

    fun upsertAssessment(assessment: Assessment, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assessment.value).document(assessment.id.toString())
            .set(assessment)
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun deleteAssessment(id: String, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assessment.value).document(id).delete()
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun getAssessment(studentId: String, fetcher: DataFetcher<Assessment>) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assessment.value).whereEqualTo("studentId", studentId).addSnapshotListener { values, _ ->
            val assessments = mutableListOf<Assessment>()
            for (document in values!!) {
                val assessment = document.toObject(Assessment::class.java)
                assessments.add(assessment)
            }
            fetcher.getListData(assessments)
        }
    }

    fun upsertAssignment(assignment: Assignment, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assignment.value).document(assignment.id.toString())
            .set(assignment)
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun deleteAssignment(id: String, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assignment.value).document(id).delete()
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun getAssignment(studentId: String, fetcher: DataFetcher<Assignment>) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assignment.value).whereEqualTo("studentId", studentId).addSnapshotListener { values, _ ->
            val assignments = mutableListOf<Assignment>()
            for (document in values!!) {
                val assignment = document.toObject(Assignment::class.java)
                assignments.add(assignment)
            }
            fetcher.getListData(assignments)
        }
    }

    fun upsertSubject(subject: Subject, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Subject.value).document(subject.id.toString()).set(subject)
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun deleteSubject(id: String, isComplete: (Boolean) -> Unit) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Subject.value).document(id).delete()
            .addOnSuccessListener {
                isComplete(true)
            }
            .addOnFailureListener {
                isComplete(false)
            }
    }

    fun getSubject(fetcher: DataFetcher<Subject>) {
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Subject.value).addSnapshotListener { values, _ ->
            val subjects = mutableListOf<Subject>()
            for (document in values!!) {
                val subject = document.toObject(Subject::class.java)
                subjects.add(subject)
            }
            fetcher.getListData(subjects)
        }
    }

    fun getStudentUniqueId(): String {
        return database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Student.value).document().id

    }

    fun getAssignmentUniqueId(): String {
        return database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assignment.value).document().id

    }

    fun getAttendanceUniqueId(): String {
        return database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Attendance.value).document().id

    }

    fun getAssessmentUniqueId(): String {
        return database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assessment.value).document().id

    }

    fun getSubjectUniqueId(): String {
        return database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Subject.value).document().id
    }

    fun getTotalAssignments(students: SnapshotStateList<Student>, fetcher: DataFetcher<Assignment>) {
        val studentIds = students.map { it.id }
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assignment.value).whereIn("studentId", studentIds).addSnapshotListener { values, _ ->
            val assignments = mutableListOf<Assignment>()
            for (document in values!!) {
                val assignment = document.toObject(Assignment::class.java)
                assignments.add(assignment)
            }
            fetcher.getListData(assignments)
        }
    }

    fun getTotalAssessment(students: SnapshotStateList<Student>, fetcher: DataFetcher<Assessment>) {
        val studentIds = students.map { it.id }
        database.collection(Constant.Users.value).document(Constant.User.value).collection(Constant.Assessment.value).whereIn("studentId", studentIds).addSnapshotListener { values, _ ->
            val assessments = mutableListOf<Assessment>()
            for (document in values!!) {
                val assessment = document.toObject(Assessment::class.java)
                assessments.add(assessment)
            }
            fetcher.getListData(assessments)
        }
    }
}