package com.mohamedfayaskhan.studentmanager.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.screen.attendance.AddAttendance
import com.mohamedfayaskhan.studentmanager.screen.student.AddStudent
import com.mohamedfayaskhan.studentmanager.screen.attendance.AttendanceScreen
import com.mohamedfayaskhan.studentmanager.screen.HomeScreen
import com.mohamedfayaskhan.studentmanager.screen.student.StudentScreen
import com.mohamedfayaskhan.studentmanager.screen.attendance.ViewAttendance
import com.mohamedfayaskhan.studentmanager.screen.student.ViewStudent


@Composable
fun BottomBar(navController: NavHostController) {
    val navItems = listOf(
        Router.Home,
        Router.Student,
        Router.Attendance
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        navItems.forEach {navItem ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == navItem.route
            }
            if (selected != null) {
                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = if (selected) {
                                painterResource(id = navItem.selectedIcon)
                            } else {
                                painterResource(id = navItem.unSelectedIcon)
                            },
                            contentDescription = navItem.title,
                        )
                    },
                    alwaysShowLabel = true,
                    label = {
                        Text(
                            text = navItem.title
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationGraph(
    navController: NavHostController,
    dataViewModel: DataViewModel
) {
    NavHost(navController = navController, startDestination = Router.Home.route) {
        composable(route = Router.Home.route) {
            HomeScreen(dataViewModel)
        }
        composable(route = Router.Student.route) {
            StudentScreen(dataViewModel = dataViewModel, navController = navController)
        }
        composable(route = Router.Attendance.route) {
            AttendanceScreen(dataViewModel = dataViewModel, navController = navController)
        }
        composable(route = Router.AddStudent.route) {
            AddStudent(
                dataViewModel = dataViewModel,
                navController = navController,
                id = null
            )
        }
        composable(route = Router.AddAttendance.route) {
            AddAttendance(
                dataViewModel = dataViewModel,
                navController = navController,
                id = null
            )
        }
        composable(
            route = Router.ViewAttendance.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                },
            )
        ) {entry ->
            ViewAttendance(dataViewModel = dataViewModel, entry.arguments?.getString("id").toString())
        }
        composable(
            route = Router.ViewStudent.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {entry ->
            ViewStudent(dataViewModel = dataViewModel, studentId = entry.arguments?.getString("id").toString())
        }
        composable(
            route = Router.AddStudent.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {entry ->
            AddStudent(dataViewModel = dataViewModel, navController = navController, id = entry.arguments?.getString("id").toString())
        }
        composable(
            route = Router.AddAttendance.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {entry ->
            AddAttendance(dataViewModel = dataViewModel, navController = navController, id = entry.arguments?.getString("id").toString())
        }
    }
}