package com.viewnext.practicaandroid.core.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.viewnext.practicaandroid.core.ui.screens.InvoiceFilterScreen
import com.viewnext.practicaandroid.core.ui.screens.InvoiceListScreen
import com.viewnext.practicaandroid.core.ui.screens.MainScreen
import com.viewnext.practicaandroid.core.ui.screens.SmartSolarScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = "main",
    ){
        composable("main"){
            MainScreen(modifier = modifier,
                navigateToInvoiceList = {
                    navController.navigate("invoices")
                },
                navigateToSmartSolar = {
                    navController.navigate("smart_solar")
                }
            )
        }
        composable("invoices",){
            InvoiceListScreen(modifier = modifier)
        }
        composable("smart_solar"){
            SmartSolarScreen(modifier = modifier)
        }
        composable("invoices_filter") {
            InvoiceFilterScreen(navController, modifier = modifier)
        }
    }
}