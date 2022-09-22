package com.sq.githubreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sq.githubreader.navigation.NavHostComponent
import com.sq.githubreader.ui.theme.GithubReaderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubReaderTheme() {
                navController = rememberNavController()
                NavHostComponent(navController = navController)
            }
        }
    }
}
