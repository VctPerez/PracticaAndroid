package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import android.content.res.Resources
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.CustomTopBar
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.domain.data.InvoiceEntity
import com.viewnext.practicaandroid.domain.repository.OfflineInvoiceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InvoiceListScreen(){
    Scaffold(
        topBar = { CustomTopBar("Consumo", true) }
    ){innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(start = 22.dp)){
            Text(stringResource(R.string.invoicelist_title), style = MaterialTheme.typography.titleLarge ,
                modifier = Modifier.padding(innerPadding))
            InvoiceList(invoices,modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun InvoiceList(invoices : List<InvoiceEntity>, modifier: Modifier = Modifier){
    LazyColumn {
        items(invoices){
            InvoiceItem(it)
        }
    }
}

@Composable
fun InvoiceItem(invoice : InvoiceEntity, modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Column {
            Text(invoice.parseDate())
            if(invoice.status != "Pagada"){
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
}

@Preview(showSystemUi = true)
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
        InvoiceItem(InvoiceEntity(
            date = "2023/10/01",
            amount = 100.0,
            status = "Pagadan't",
        ))
    }
}

@Preview(showBackground = true)
@Composable
fun PaidInvoicePreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceItem(InvoiceEntity(
            date = "2023/10/01",
            amount = 100.0,
            status = "Pagada",
        ))
    }
}

private fun getMonth(month: String): String {
    return when (month) {
        "01" -> Resources.getSystem().getString(R.string.january_abr)
        "02" -> Resources.getSystem().getString(R.string.february_abr)
        "03" -> Resources.getSystem().getString(R.string.march_abr)
        "04" -> Resources.getSystem().getString(R.string.april_abr)
        "05" -> Resources.getSystem().getString(R.string.may_abr)
        "06" -> Resources.getSystem().getString(R.string.june_abr)
        "07" -> Resources.getSystem().getString(R.string.july_abr)
        "08" -> Resources.getSystem().getString(R.string.august_abr)
        "09" -> Resources.getSystem().getString(R.string.september_abr)
        "10" -> Resources.getSystem().getString(R.string.october_abr)
        "11" -> Resources.getSystem().getString(R.string.november_abr)
        else -> Resources.getSystem().getString(R.string.december_abr)
    }
}

fun InvoiceEntity.parseDate(): String {
    val dateParams = date.split("/")
    return "${dateParams[0]} ${getMonth(dateParams[1])} ${dateParams[2]}"
}

@SuppressLint("DefaultLocale")
fun InvoiceEntity.parseAmount(): String {
    return String.format("%.2f €", amount)
}