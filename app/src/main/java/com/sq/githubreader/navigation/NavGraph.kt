package com.sq.githubreader.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sq.githubreader.ui.about.AboutScreen
import com.sq.githubreader.ui.details.DetailsScreen
import com.sq.githubreader.ui.main.MainScreen
import com.sq.githubreader.ui.repowebview.RepoWebViewScreen
import com.sq.githubreader.ui.splash.SplashScreen

sealed class Screen(val route: String) {
    object SplashScreen: Screen(route = "splash_screen")
    object MainScreen: Screen(route = "main_screen")
    object DetailsScreen: Screen(route = "details_screen/{org}")
    object WebViewScreen: Screen(route = "webview_screen/{org}/{repo}")
    object AboutScreen: Screen(route = "about_screen")
}

@Composable
fun NavHostComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(modifier = Modifier, navController = navController)
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(modifier = Modifier, navController = navController)
        }
        composable(route = Screen.DetailsScreen.route,
            arguments = listOf(navArgument("org") {
                type = NavType.StringType
            })
        ) {
            DetailsScreen(
                modifier = Modifier,
                navController = navController,
                orgName = it.arguments?.getString("org").toString()
            )
        }
        composable(route = Screen.WebViewScreen.route,
            arguments = listOf(navArgument("org") {
                type = NavType.StringType
            },navArgument("repo") {
                type = NavType.StringType
            })
        ) {
            RepoWebViewScreen(
                org = it.arguments?.getString("org").toString(),
                repo = it.arguments?.getString("repo").toString(),
                navController = navController
            )
        }
        composable(route = Screen.AboutScreen.route) {
            AboutScreen(modifier = Modifier, navController = navController)
        }
    }
}
