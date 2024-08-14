package com.mohamedfayaskhan.studentmanager.constant

sealed class Constant(val value: String) {

    data object Student : Constant("Student")
    data object Attendance : Constant("Attendance")
    data object Assessment : Constant("Assessment")
    data object Assignment : Constant("Assignment")
    data object Subject : Constant("Subject")
    data object DateFormat : Constant("MMM dd, yyyy")
    data object User : Constant("Shammeer")
    data object Users : Constant("Users")
    data object AddSubjectId: Constant("#ADDSUBJECT")
    data object AddSubjectValue: Constant("Add Subject")
}

enum class TYPE {
    STUDENT, ATTENDANCE, ASSESSMENT, ASSIGNMENT, SUBJECT
}
