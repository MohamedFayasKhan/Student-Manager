package com.mohamedfayaskhan.studentmanager.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedfayaskhan.studentmanager.actions.DataFetcher
import com.mohamedfayaskhan.studentmanager.constant.TYPE
import com.mohamedfayaskhan.studentmanager.model.Assessment
import com.mohamedfayaskhan.studentmanager.model.Assignment
import com.mohamedfayaskhan.studentmanager.model.Attendance
import com.mohamedfayaskhan.studentmanager.model.Student
import com.mohamedfayaskhan.studentmanager.model.Subject
import com.mohamedfayaskhan.studentmanager.model.SubjectMarks
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class DataViewModel: ViewModel() {

    private val repository: DataRepository
    init {
        val firestore = Database().getFirebaseFireStore()
        repository = DataRepository(firestore)
    }

    private var _students = mutableStateListOf<Student>()
    val students: SnapshotStateList<Student> = _students
    private var _attendances = mutableStateListOf<Attendance>()
    val attendances: SnapshotStateList<Attendance> = _attendances
    private var _assessments = mutableStateListOf<Assessment>()
    val assessments: SnapshotStateList<Assessment> = _assessments
    private var _assignments = mutableStateListOf<Assignment>()
    val assignments: SnapshotStateList<Assignment> = _assignments
    private var _subjects = mutableStateListOf<Subject>()
    val subjects: SnapshotStateList<Subject> = _subjects

    private var _totalAssessments = mutableStateListOf<Assessment>()
//    val totalAssessments: SnapshotStateList<Assessment> = _totalAssessments
    private var _totalAssignments = mutableStateListOf<Assignment>()
//    val totalAssignments: SnapshotStateList<Assignment> = _totalAssignments
    private var _stats = mutableStateListOf<Triple<Student, List<SubjectMarks>, String>>()
    val stats: SnapshotStateList<Triple<Student, List<SubjectMarks>, String>> = _stats

    fun upsertStudent(student: Student, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.upsertStudent(student) {
                isComplete(it)
            }
        }
    }

    fun deleteStudent(id: String, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.deleteStudent(id) {
                isComplete(it)
            }
        }
    }

    fun getStudents() {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Student> {
                override fun getListData(list: List<Student>) {
                    _students.clear()
                    _students.addAll(list.distinctBy { it.id })
                    getSubject()
                    getAttendance()
                    if (_students.isNotEmpty()) {
                        viewModelScope.launch {
                            delay(2000)
                            getTotalAssessment(_students)
                            getTotalAssignment(_students)
                            getStats()
                        }
                    }
                }

                override fun getSingleData(data: Student) {
                }
            }
            repository.getStudents(fetcher)
        }
    }

    fun upsertAttendance(attendance: Attendance, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.upsertAttendance(attendance) {
                isComplete(it)
            }
        }
    }

    fun deleteAttendance(id: String, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.deleteAttendance(id) {
                isComplete(it)
            }
        }
    }

    fun getAttendance() {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Attendance> {
                override fun getListData(list: List<Attendance>) {
                    _attendances.clear()
                    _attendances.addAll(list.distinctBy { it.id })
                }

                override fun getSingleData(data: Attendance) {
                    TODO("Not yet implemented")
                }

            }
            repository.getAttendance(fetcher)
        }
    }

    fun upsertAssessment(assessment: Assessment, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.upsertAssessment(assessment) {
                isComplete(it)
            }
        }
    }

    fun deleteAssessment(id: String, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.deleteAssessment(id) {
                isComplete(it)
            }
        }
    }

    fun getAssessment(studentId: String) {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Assessment> {
                override fun getListData(list: List<Assessment>) {
                    _assessments.clear()
                    _assessments.addAll(list.distinctBy { it.id })
                }

                override fun getSingleData(data: Assessment) {

                }

            }
            repository.getAssessment(studentId, fetcher)
        }
    }

    fun upsertAssignment(assignment: Assignment, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.upsertAssignment(assignment) {
                isComplete(it)
            }
        }
    }

    fun deleteAssignment(id: String, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.deleteAssignment(id) {
                isComplete(it)
            }
        }
    }

    fun getAssignment(studentId: String) {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Assignment> {
                override fun getListData(list: List<Assignment>) {
                    _assignments.clear()
                    _assignments.addAll(list.distinctBy { it.id })
                }

                override fun getSingleData(data: Assignment) {

                }

            }
            repository.getAssignment(studentId, fetcher)
        }
    }

    fun upsertSubject(subject: Subject, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.upsertSubject(subject) {
                isComplete(it)
            }
        }
    }

    fun deleteSubject(id: String, isComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            repository.deleteSubject(id) {
                isComplete(it)
            }
        }
    }

    fun getSubject() {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Subject> {
                override fun getListData(list: List<Subject>) {
                    _subjects.clear()
                    _subjects.addAll(list.distinctBy { it.id })
                }

                override fun getSingleData(data: Subject) {
                }
            }
            repository.getSubject(fetcher)
        }
    }

    fun getUniqueId(type: TYPE): String {
        return when (type) {
            TYPE.STUDENT -> repository.getStudentUniqueId()
            TYPE.ATTENDANCE -> repository.getAttendanceUniqueId()
            TYPE.ASSESSMENT -> repository.getAssessmentUniqueId()
            TYPE.ASSIGNMENT -> repository.getAssignmentUniqueId()
            TYPE.SUBJECT -> repository.getSubjectUniqueId()
        }
    }

    fun getTotalAssignment(students: SnapshotStateList<Student>) {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Assignment> {
                override fun getListData(list: List<Assignment>) {
                    _totalAssignments.clear()
                    _totalAssignments.addAll(list.distinctBy { it.id })
                }
                override fun getSingleData(data: Assignment) {
                }
            }
            repository.getTotalAssignments(students, fetcher)
        }
    }

    fun getTotalAssessment(students: SnapshotStateList<Student>) {
        viewModelScope.launch {
            val fetcher = object : DataFetcher<Assessment> {
                override fun getListData(list: List<Assessment>) {
                    _totalAssessments.clear()
                    _totalAssessments.addAll(list.distinctBy { it.id })
                }
                override fun getSingleData(data: Assessment) {
                }
            }
            repository.getTotalAssessment(students, fetcher)
        }
    }

    fun getStats() {
        _stats.clear()
        _students.forEach {student ->
            val list = mutableListOf<SubjectMarks>()
            var presentCount = 0
            _attendances.forEach { attendance ->
                if (attendance.presents?.contains(student.id) == true) {
                    presentCount += 1
                }
            }
            _subjects.forEach {subject ->
                val assessments = _totalAssessments.filter { it.studentId == student.id }
                var scored = 0.0
                var total = 0.0
                assessments.forEach {assessment ->
                    scored += assessment.scoredMark ?: 0.0
                    total += assessment.totalMark ?: 0.0
                }
                list.add(SubjectMarks(subject.name.toString(), String.format(Locale.getDefault(), "%.2f", ((scored / total) * 100))))
            }
            _stats.add(Triple(student, list, "$presentCount / ${_attendances.size}"))
        }
    }
}