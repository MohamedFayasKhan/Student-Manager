package com.mohamedfayaskhan.studentmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mohamedfayaskhan.studentmanager.data.DataViewModel
import com.mohamedfayaskhan.studentmanager.navigation.BottomBar
import com.mohamedfayaskhan.studentmanager.navigation.BottomNavigationGraph
import com.mohamedfayaskhan.studentmanager.navigation.Router
import com.mohamedfayaskhan.studentmanager.ui.theme.StudentManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backstackState = navController.currentBackStackEntryAsState().value
            val dataViewModel = viewModel<DataViewModel>()
            val isBottomBarVisible = remember(key1 = backstackState) {
                backstackState?.destination?.route == Router.Home.route ||
                        backstackState?.destination?.route == Router.Student.route ||
                        backstackState?.destination?.route == Router.Attendance.route
            }
            val isFABVisible = remember(key1 = backstackState) {
                backstackState?.destination?.route == Router.Student.route ||
                        backstackState?.destination?.route == Router.Attendance.route
            }

            StudentManagerTheme {
                Scaffold(
                    bottomBar = {
                        if (isBottomBarVisible) {
                            BottomBar(navController = navController)
                        }
                    },
                    floatingActionButton = {
                        if (isFABVisible) {
                            FloatingActionButton(
                                onClick = {
                                    if (backstackState?.destination?.route == Router.Student.route) {
                                        navController.navigate(Router.AddStudent.route)
                                    } else if (backstackState?.destination?.route == Router.Attendance.route) {
                                        navController.navigate(Router.AddAttendance.route)
                                    }
                                }
                            ) {
                                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        BottomNavigationGraph(navController = navController, dataViewModel = dataViewModel)
                    }
                }
            }
        }
    }
}