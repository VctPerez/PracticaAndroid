package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceListViewModel
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import androidx.lifecycle.viewmodel.compose.viewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InvoiceListScreen(modifier: Modifier = Modifier) {
    val viewModel : InvoiceListViewModel = viewModel(factory = InvoiceListViewModel.Factory)
    val state by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(start = 22.dp)){
        Text(stringResource(R.string.invoicelist_title), style = MaterialTheme.typography.titleLarge)
        //TODO: Add loading and error screen
        InvoiceList(state.invoices)
    }
}

@Composable
fun InvoiceList(invoices : List<InvoiceEntity>, modifier: Modifier = Modifier){
    LazyColumn(modifier = modifier.padding(top = 40.dp)){
        items(invoices){
            InvoiceItem(it)
        }
    }
}

@Composable
fun InvoiceItem(invoice : InvoiceEntity, modifier: Modifier = Modifier){
    Column(modifier = Modifier.fillMaxWidth()){
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp))
        {
            Column {
                Text(invoice.parseDate(LocalContext.current))
                if(invoice.status.lowercase() != "pagada"){
                    Text(stringResource(R.string.invoice_not_paid),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(Modifier.weight(1f))
            Text(invoice.parseAmount())
            IconButton(onClick = { TODO()}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    modifier = Modifier.size(35.dp).padding(0.dp),
                )
            }
        }
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }

}

@Preview(showBackground = true)
@Composable
fun InvoiceListScreenPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun NotPaidInvoicePreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceItem(
            InvoiceEntity(
            date = "2023/10/01",
            amount = 100.0,
            status = "Pagadan't",
        )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaidInvoicePreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceItem(
            InvoiceEntity(
            date = "2023/10/01",
            amount = 100.0,
            status = "Pagada",
        )
        )
    }
}

private fun getMonth(month: String, context : Context): String {
    return when (month) {
        "01" -> context.getString(R.string.january_abr)
        "02" -> context.getString(R.string.february_abr)
        "03" -> context.getString(R.string.march_abr)
        "04" -> context.getString(R.string.april_abr)
        "05" -> context.getString(R.string.may_abr)
        "06" -> context.getString(R.string.june_abr)
        "07" -> context.getString(R.string.july_abr)
        "08" -> context.getString(R.string.august_abr)
        "09" -> context.getString(R.string.september_abr)
        "10" -> context.getString(R.string.october_abr)
        "11" -> context.getString(R.string.november_abr)
        else -> context.getString(R.string.december_abr)
    }
}

fun InvoiceEntity.parseDate(context : Context): String {
    val dateParams = date.split("/")
    return "${dateParams[0]} ${getMonth(dateParams[1], context)} ${dateParams[2]}"
}

@SuppressLint("DefaultLocale")
fun InvoiceEntity.parseAmount(): String {
    return String.format("%.2f €", amount)
}