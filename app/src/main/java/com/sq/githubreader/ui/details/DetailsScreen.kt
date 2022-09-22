package com.sq.githubreader.ui.details


import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sq.githubreader.navigation.Screen
import com.sq.githubreader.network.model.Repo

/**
 * The Details screen will be launched from the main screen and populated
 * with repo info.  A User will click on ay of the items in the repo
 * and it will launch a webview
 */
@Composable
fun DetailsScreen(
    modifier: Modifier,
    navController: NavController,
    detailsScreenViewModel: DetailsScreenViewModel = hiltViewModel(),
    orgName: String = "nytimes"
) {
    val status by detailsScreenViewModel.result.collectAsState()
    detailsScreenViewModel.fetchRepos(orgName)
    Box(modifier = Modifier.fillMaxSize()) {
        if(status.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
        } else {
            if(status.isError || status.items.isEmpty()) {
                Text("Error Loading repos for '${orgName}'", modifier = Modifier.align(alignment = Alignment.Center) )
            } else {
                Column() {
                    Text(
                        "Most Popular Repos for '$orgName'",
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(status.items) { repo ->
                            DetailItem(repo = repo, orgName = orgName, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItem(repo: Repo, orgName: String, navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp).border(2.dp, Color.Black, RoundedCornerShape(5.dp))) {
        Text(text = repo.fullName ?: "",
            Modifier
                .clickable {
                    navController.navigate(
                        Screen.WebViewScreen.route
                            .replace("{repo}", repo.name)
                            .replace("{org}", orgName)
                    )
                }
                .padding(5.dp)
                .align(alignment = Alignment.CenterStart))
        Row(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(5.dp)) {
            Image(painter = painterResource(id = android.R.drawable.star_on), contentDescription = "Star")
            Text(text = repo.stargazersCount.toString())
        }
    }
}
