package com.viewnext.practicaandroid.core

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.viewnext.practicaandroid.core.ui.CustomTopBar
import com.viewnext.practicaandroid.core.ui.navigation.AppNavHost
import com.viewnext.practicaandroid.core.ui.screens.InvoiceFilterScreen
import com.viewnext.practicaandroid.core.ui.screens.InvoiceListScreen
import com.viewnext.practicaandroid.core.ui.screens.SmartSolarScreen
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceListViewModel
import com.viewnext.practicaandroid.domain.repository.OfflineInvoiceRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticaAndroidTheme(dynamicColor = false) {
                ApplicationComposable()
            }
        }
    }
}

@Composable
fun ApplicationComposable(navController : NavHostController = rememberNavController()){
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val showTopBar = currentDestination?.route != "main"
    Scaffold(
        topBar = {
            if(showTopBar){
                CustomTopBar(
                    title = "Atrás",
                    filter = currentDestination?.route == "invoices",
                    onBackButtonClick = {
                        navController.popBackStack()
                    },
                    navigateToFilter = {
                        navController.navigate("invoices_filter")
                    }
                )
            }
        }
    ){innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
fun TestMainScreen(){
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {CustomTopBar("Practica", false)})
    {innerPadding ->

        InvoiceListScreen(Modifier.padding(innerPadding))
    }
}

@Composable
fun TestFilterScreen(name: String, modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {CustomTopBar("Practica", false)})
    {innerPadding ->
        InvoiceFilterScreen(Modifier.padding(innerPadding))
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        TestMainScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun FilterPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        TestFilterScreen("Practica")
    }
}