package com.britetodo.notesforandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.britetodo.notesforandroid.ui.screens.editor.EditorScreen
import com.britetodo.notesforandroid.ui.screens.home.HomeScreen
import com.britetodo.notesforandroid.ui.screens.notebooks.NotebookDetailScreen
import com.britetodo.notesforandroid.ui.screens.onboarding.OnboardingScreen
import com.britetodo.notesforandroid.ui.screens.settings.SettingsScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object NotebookDetail : Screen("notebook/{notebookId}") {
        fun createRoute(notebookId: String) = "notebook/$notebookId"
    }
    object Editor : Screen("editor/{notebookId}/{pageId}") {
        fun createRoute(notebookId: String, pageId: String) = "editor/$notebookId/$pageId"
    }
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onOpenNotebook = { notebookId ->
                    navController.navigate(Screen.NotebookDetail.createRoute(notebookId))
                },
                onSettings = {
                    navController.navigate(Screen.Settings.route)
                },
            )
        }

        composable(
            route = Screen.NotebookDetail.route,
            arguments = listOf(navArgument("notebookId") { type = NavType.StringType }),
        ) { backStack ->
            val notebookId = backStack.arguments?.getString("notebookId") ?: return@composable
            NotebookDetailScreen(
                notebookId = notebookId,
                onOpenPage = { pageId ->
                    navController.navigate(Screen.Editor.createRoute(notebookId, pageId))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Screen.Editor.route,
            arguments = listOf(
                navArgument("notebookId") { type = NavType.StringType },
                navArgument("pageId") { type = NavType.StringType },
            ),
        ) { backStack ->
            val notebookId = backStack.arguments?.getString("notebookId") ?: return@composable
            val pageId = backStack.arguments?.getString("pageId") ?: return@composable
            EditorScreen(
                notebookId = notebookId,
                pageId = pageId,
                onBack = { navController.popBackStack() },
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
