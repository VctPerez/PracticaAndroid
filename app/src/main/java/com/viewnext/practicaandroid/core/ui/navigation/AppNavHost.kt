package com.viewnext.practicaandroid.core.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideIntoContainer(
                animationSpec = tween(300, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    300, easing = LinearEasing
                )
            ) + slideOutOfContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        }
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