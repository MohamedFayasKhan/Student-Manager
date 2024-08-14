package com.mohamedfayaskhan.studentmanager.navigation

import androidx.annotation.DrawableRes
import com.mohamedfayaskhan.studentmanager.R

sealed class Router(
    val title: String,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
    val route: String
) {
    data object Home: Router(
        title = "Home",
        selectedIcon = R.drawable.home_filled,
        unSelectedIcon = R.drawable.home_outline,
        route = "home"
    )
    data object Student: Router(
        title = "Student",
        selectedIcon = R.drawable.student_filled,
        unSelectedIcon = R.drawable.student_outline,
        route = "student"
    )
    data object AddStudent: Router(
        title = "Add Student",
        selectedIcon = R.drawable.student_filled,
        unSelectedIcon = R.drawable.student_outline,
        route = "addStudent"
    )
    data object ViewStudent: Router(
        title = "View Student",
        selectedIcon = R.drawable.student_filled,
        unSelectedIcon = R.drawable.student_outline,
        route = "viewStudent"
    )
    data object Attendance: Router(
        title = "Attendance",
        selectedIcon = R.drawable.attendance_filled,
        unSelectedIcon = R.drawable.attendance_outline,
        route = "attendance"
    )
    data object AddAttendance: Router(
        title = "Add Attendance",
        selectedIcon = R.drawable.attendance_filled,
        unSelectedIcon = R.drawable.attendance_outline,
        route = "addAttendance"
    )
    data object ViewAttendance: Router(
        title = "View Attendance",
        selectedIcon = R.drawable.attendance_filled,
        unSelectedIcon = R.drawable.attendance_outline,
        route = "viewAttendance"
    )
    data object Assessment: Router(
        title = "Assessment",
        selectedIcon = R.drawable.assessment_filled,
        unSelectedIcon = R.drawable.assessment_outline,
        route = "assessment"
    )
    data object Assignment: Router(
        title = "Assignment",
        selectedIcon = R.drawable.assignment_filled,
        unSelectedIcon = R.drawable.assignment_outline,
        route = "assignment"
    )
}
