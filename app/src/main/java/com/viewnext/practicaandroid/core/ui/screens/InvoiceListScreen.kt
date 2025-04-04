package com.viewnext.practicaandroid.core.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.CustomTopBar
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme

@Composable
fun InvoiceListScreen(){
    Scaffold(
        topBar = { CustomTopBar("Consumo", true) }
    ){innerPadding ->
        Text(stringResource(R.string.invoicelist_title), style = MaterialTheme.typography.titleLarge ,
            modifier = Modifier.padding(innerPadding).padding(start = 22.dp))
        InvoiceList(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun InvoiceList(modifier: Modifier = Modifier){
//    Text(
//        text = "Invoice List",
//        modifier = modifier
//    )
}

@Preview(showSystemUi = true)
@Composable
fun InvoiceListScreenPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceListScreen()
    }
}