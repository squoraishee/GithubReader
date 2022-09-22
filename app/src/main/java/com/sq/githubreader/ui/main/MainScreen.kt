package com.sq.githubreader.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sq.githubreader.R
import com.sq.githubreader.navigation.Screen
import com.sq.githubreader.network.model.Organization

/**
 * Operation:
 *
 * On the main screen you will be able to perform an organization search
 * which will update a list of organizations as you type in the search bar
 *
 * Clicking on an organization from the list will open a details screen where you can get the top
 * repos for that particular organization.  These organizations were fetched when the
 * app loaded and are filtered alphabetically
 *
 * If the organization isn't in the list, you can also do a search for
 * that organization from whatever you've typed in the search bar,
 * which will make a call to the repos api.
 *
 * If the organization isn't found, then an error dialog will appear
 */
@Composable
fun MainScreen(modifier: Modifier,
               mainScreenViewModel: MainScreenViewModel = hiltViewModel(),
               navController: NavController
) {
    val queryText by mainScreenViewModel.queryText.collectAsState()
    val selectableItems by mainScreenViewModel.selectableItems.collectAsState()

    Column {
        Header()
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = queryText,
            onValueChange = { query ->
                mainScreenViewModel.userTyped(query)
            },
            label = {Text(text = "Enter Organization")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions (
                onSearch = {
                    navController.navigate(Screen.DetailsScreen.route.replace("{org}",queryText))
                }
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        OrgList(queryText = queryText, selectableItems = selectableItems, navController = navController)
    }
}

@Composable()
fun OrgList(queryText: String, selectableItems: MutableList<Organization>, navController: NavController) {
    Text(if(queryText == "") {
        "Enter a query to see matching repos"
    } else {
        "${selectableItems.size} results for '${queryText}'"
    }, fontWeight = FontWeight.Bold)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(selectableItems) { org ->
            OrgItem(org = org, navController = navController)
        }
    }
}


@Composable
fun Header(modifier: Modifier = Modifier) {
    Column() {
        Text("Github Lister App",
            modifier = modifier.align(alignment = Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
        Text(text = stringResource(R.string.exists),
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(20.dp))
    }
}

@Composable
fun OrgItem(org: Organization, navController: NavController) {
    Box(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(2.dp, Color.Black, RoundedCornerShape(5.dp)))
    {
        Text(org.login, Modifier
            .clickable {
                navController.navigate(Screen.DetailsScreen.route.replace("{org}", org.login))
            }
            .align(alignment = Alignment.CenterStart)
            .padding(5.dp)
            .fillMaxWidth()
        )
    }

}