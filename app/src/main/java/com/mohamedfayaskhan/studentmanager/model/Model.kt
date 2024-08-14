package com.mohamedfayaskhan.studentmanager.model

data class Student(
    val id: String?,
    val name: String?,
    val grade: String?,
    val createdTime: Long?
) {
    constructor(): this(null, null, null, null)
}

data class Attendance(
    val id: String?,
    val date: String?,
    val presents: List<String>?,
    val createdTime: Long?
) {
    constructor(): this(null, null, null, null)
}

data class Assignment(
    val id: String?,
    val date: String?,
    val dueDate: String?,
    val title: String?,
    val description: String?,
    val subject: Subject?,
    val status: String?,
    val remarks: String?,
    val studentId: String?,
    val createdTime: Long?
) {
    constructor(): this(null, null,null, null,null, null,null, null, null, null)
}

data class Assessment(
    val id: String?,
    val date: String?,
    val title: String?,
    val description: String?,
    val subject: Subject?,
    val scoredMark: Double?,
    val totalMark: Double?,
    val studentId: String?,
    val createdTime: Long?
){
    constructor(): this(null, null,null, null,null, null,null, null, null)
}

data class Subject(
    val id: String?,
    var name: String?
) {
    constructor(): this(null, null)
}

data class StudentPresent(
    val student: Student,
    var isPresent: Boolean = false
)

data class SubjectMarks(
    val subject: String,
    val marks: String
)