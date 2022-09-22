package com.sq.githubreader.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sq.githubreader.R
import com.sq.githubreader.navigation.Screen
import kotlin.random.Random

/**
 * When the splash screen loads, we get an initial list of organizations
 *
 * For the purpose of this demo, I've decided to cache them in memory, but
 * we could easily cache them in a Room Database if necessary for a more robust
 * application
 */

@Composable
fun SplashScreen(modifier: Modifier,
                 splashScreenViewModel: SplashScreenViewModel = hiltViewModel(),
                 navController: NavController
) {

    /* Prevent recompositions on automatic navigation */
    val hasAlreadyNavigated = remember { mutableStateOf(false) }

    val status by splashScreenViewModel.result.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(painter = painterResource(id = R.drawable.octocat), contentDescription = "")
            if (status.isLoading) {
                CircularProgressIndicator()
            } else {
                if (status.isError) {
                    Text("Error Fetching Repos", modifier = Modifier
                        .clickable {
                            navController.navigate(Screen.MainScreen.route) {
                                popUpTo(0)
                            }
                        })
                } else {
                    if (!hasAlreadyNavigated.value) {
                        hasAlreadyNavigated.value = true
                        navController.navigate(Screen.MainScreen.route) {
                            popUpTo(0)
                        }
                    }
                }
            }
        }
    }
}