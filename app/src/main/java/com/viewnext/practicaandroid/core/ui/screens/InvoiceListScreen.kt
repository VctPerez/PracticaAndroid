package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.viewnext.practicaandroid.core.ui.IberDialogPopup
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceFilterViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InvoiceListScreen(modifier: Modifier = Modifier) {

    val invoiceFilterViewModel : InvoiceFilterViewModel = viewModel(
        factory = InvoiceFilterViewModel.Factory,
        key = "InvoiceFilterViewModel"
    )
    val filter = invoiceFilterViewModel.uiState.collectAsState().value

    val invoiceListViewModel : InvoiceListViewModel = viewModel(
        factory = InvoiceListViewModel.Factory
    )
    val state by invoiceListViewModel.uiState.collectAsState()

    LaunchedEffect(filter) {
        invoiceListViewModel.refreshInvoices(filter)
    }

//    Log.d("filtro:", invoiceFilterViewModel.uiState.collectAsState().value.filter.toString())

    Column(modifier = modifier
        .fillMaxSize()
        .padding(start = 22.dp)){
        Text(stringResource(R.string.invoicelist_title), style = MaterialTheme.typography.titleLarge)

        if(state.isLoading){
            LoadingInvoicesScreen()
        } else if(state.error != null){
            Text("error + ${state.error}")
        } else {
            InvoiceList(state.invoices)
        }
    }
}

@Composable
fun LoadingInvoicesScreen(modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center)
    {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = IberGreen,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceList(invoices : List<InvoiceEntity>, modifier: Modifier = Modifier){
    if(invoices.isEmpty()){
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){

            Text(stringResource(R.string.invoicesEmptyList),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 20.dp))
        }

    }
    LazyColumn(modifier = modifier.padding(top = 40.dp)){
        items(invoices){
            InvoiceItem(it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceItem(invoice : InvoiceEntity, modifier: Modifier = Modifier){
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()){

        if(showDialog){
            IberDialogPopup(
                stringResource(R.string.infoDialogTitle), stringResource(R.string.infoDialogMessage),
                "Cerrar", onDismiss = {
                    showDialog = false
                })
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp))
        {
            Column {
                Text(invoice.parseDate())
                if(invoice.status.lowercase() != "pagada"){
                    Text(stringResource(R.string.invoice_not_paid),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge)
                }
            }
            Spacer(Modifier.weight(1f))
            Text(invoice.parseAmount())
            IconButton(onClick = { showDialog = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(0.dp),
                )
            }
        }
        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun InvoiceListScreenPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceListScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NotPaidInvoicePreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceItem(
            InvoiceEntity(
            date = "01/10/2023",
            amount = 100.0,
            status = "Pagadan't",
        )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PaidInvoicePreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceItem(
            InvoiceEntity(
            date = "01/10/2023",
            amount = 100.0,
            status = "Pagada",
        )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun InvoiceEntity.parseDate(): String {
    val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())

    val date = LocalDate.parse(date, inputFormatter)
    return date.format(outputFormatter)
}

@SuppressLint("DefaultLocale")
fun InvoiceEntity.parseAmount(): String {
    return String.format("%.2f €", amount)
}