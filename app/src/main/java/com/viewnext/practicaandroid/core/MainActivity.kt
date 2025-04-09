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
import com.viewnext.practicaandroid.core.ui.CustomTopBar
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
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {CustomTopBar("Practica", false)})
    {innerPadding ->
        //SmartSolarScreen(Modifier.padding(innerPadding))
        InvoiceListScreen(Modifier.padding(innerPadding))
    }
}

@Composable
fun FilterScreen(name: String, modifier: Modifier = Modifier) {
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
        MainScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun FilterPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        FilterScreen("Practica")
    }
}