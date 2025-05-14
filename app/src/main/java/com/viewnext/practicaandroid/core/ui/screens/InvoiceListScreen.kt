package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceListViewModel
import com.viewnext.practicaandroid.core.db.entity.InvoiceEntity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viewnext.practicaandroid.core.ui.IberDialogPopup
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceFilterViewModel
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorCount
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.LineProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InvoiceListScreen(modifier: Modifier = Modifier) {

    val invoiceFilterViewModel: InvoiceFilterViewModel = viewModel(
        factory = InvoiceFilterViewModel.Factory,
        key = "InvoiceFilterViewModel"
    )
    val filter = invoiceFilterViewModel.uiState.collectAsState().value

    val invoiceListViewModel: InvoiceListViewModel = viewModel(
        factory = InvoiceListViewModel.Factory
    )
    val state by invoiceListViewModel.uiState.collectAsState()

    val dates = state.invoices.map { it.date.substring(6..9) }.sorted().toSet()
    val paginationDateIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(filter) {
        invoiceListViewModel.refreshInvoices(filter)
    }

//    Log.d("filtro:", invoiceFilterViewModel.uiState.collectAsState().value.filter.toString())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 22.dp)
    ) {
        Text(
            stringResource(R.string.invoicelist_title),
            style = MaterialTheme.typography.titleLarge
        )

        if (state.isLoading) {
            LoadingInvoicesScreen()
        } else if (state.error != null) {
            Text("error + ${state.error}")
        } else {
            val invoicesPaginated = state.invoices.filter {
                it.date.contains(dates.elementAt(paginationDateIndex.intValue))
            }
            ChartsSection(
                invoicesPaginated,
                dates,
                paginationDateIndex.intValue,
                {
                    if (paginationDateIndex.intValue > 0)
                        paginationDateIndex.intValue -= 1
                },
                {
                    if (paginationDateIndex.intValue < dates.size - 1)
                        paginationDateIndex.intValue += 1
                }
            )
            InvoiceList(invoicesPaginated)
        }
    }
}

@Composable
fun LoadingInvoicesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
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
fun InvoiceList(
    invoices: List<InvoiceEntity>,
    modifier: Modifier = Modifier
) {
    if (invoices.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Text(
                stringResource(R.string.invoicesEmptyList),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 20.dp)
            )
        }

    }
    LazyColumn(modifier = modifier.padding(top = 40.dp)) {
        items(invoices) {
            InvoiceItem(it)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceItem(invoice: InvoiceEntity, modifier: Modifier = Modifier) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        if (showDialog) {
            IberDialogPopup(
                stringResource(R.string.infoDialogTitle),
                stringResource(R.string.infoDialogMessage),
                "Cerrar",
                onDismiss = {
                    showDialog = false
                })
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
        )
        {
            Column {
                Text(invoice.parseDate())
                if (invoice.status.lowercase() != "pagada") {
                    Text(
                        stringResource(R.string.invoice_not_paid),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
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
@Composable
fun ChartsSection(
    invoices: List<InvoiceEntity>,
    dates: Set<String>,
    currentDateIndex: Int,
    decreaseDateIndex: () -> Unit,
    increaseDateIndex: () -> Unit,
    modifier: Modifier = Modifier
) {

    var showKwhChart by remember { mutableStateOf(false) }
    if (invoices.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, end = 22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    "€",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 25.dp, end = 20.dp)
                )
                Switch(
                    checked = showKwhChart,
                    onCheckedChange = { showKwhChart = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = IberGreen,
                        checkedTrackColor = IberGreen,
                        uncheckedTrackColor = Color.White,
                        uncheckedBorderColor = IberGreen
                    )
                )
                Text(
                    "Kwh",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 20.dp, end = 5.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                IconButton(
                    onClick = decreaseDateIndex,
                    enabled = currentDateIndex > 0,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                    )
                }
                Text(
                    text = dates.elementAt(currentDateIndex),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(5.dp)
                )
                IconButton(
                    onClick = increaseDateIndex,
                    enabled = currentDateIndex < dates.size - 1,
                    modifier = Modifier.padding(5.dp)
                )
                {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                    )
                }
            }

            if (showKwhChart) {
                //Log.d("ChartInvoices", invoices.toString())
                InvoiceKwhChart(invoices)
            } else {
                Log.d("ChartInvoices", invoices.toString())
                InvoiceAmountChart(invoices)
            }
//
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceAmountChart(invoices: List<InvoiceEntity>, modifier: Modifier = Modifier) {

    val invoicesMap = remember(invoices) { getInvoicesAmountPerMonth(invoices) }
    Log.d("InvoicesMap", invoicesMap.toString())

    LineChart(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(250.dp),
        data = remember(invoices) {
            listOf(
                Line(
                    label = "Euros",
                    values = invoicesMap.values.toList(),
                    color = SolidColor(IberGreen),
                    firstGradientFillColor = IberGreen.copy(alpha = .8f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                    gradientAnimationDelay = 1000,
                    drawStyle = DrawStyle.Stroke(width = 2.dp)
                )
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            padding = 10.dp,
            position = IndicatorPosition.Horizontal.End,
            contentBuilder = {
                "%.2f €".format(it)
            },
            count = IndicatorCount.CountBased(
                count = 3
            )
        ),
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            labels = invoicesMap.keys.toList()
        ),
        labelHelperProperties = LabelHelperProperties(
            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onBackground)
        ),
        dotsProperties = DotProperties(
            enabled = true,
            radius = 2.5.dp,
            strokeWidth = 2.dp,
            color = SolidColor(IberGreen),
            strokeColor = SolidColor(IberGreen),
            animationEnabled = true,
            animationSpec = tween(2000, easing = EaseInOutCubic),
        ),
        gridProperties = GridProperties(
            yAxisProperties = GridProperties.AxisProperties(
                enabled = false
            ),
            xAxisProperties = GridProperties.AxisProperties(
                lineCount = 3
            ),
        ),
        dividerProperties = DividerProperties(
            yAxisProperties = LineProperties(
                enabled = false
            )
        )

    )

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InvoiceKwhChart(
    invoices: List<InvoiceEntity>,
    modifier: Modifier = Modifier,
) {
    val invoicesMap = remember(invoices){ getInvoicesKwhPerMonth(invoices) }
    Log.d("InvoicesMap", invoicesMap.toString())

    ColumnChart(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .height(250.dp),
        data = remember(invoices) {
            invoicesMap.keys.map {
                Bars(
                    label = it,
                    values = listOf(
                        Bars.Data(
                            label = "Kwh",
                            value = invoicesMap[it] ?: 0.0,
                            color = SolidColor(IberGreen),
                        )
                    )
                )
            }
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 500L
        }),
        indicatorProperties = HorizontalIndicatorProperties(
            enabled = true,
            textStyle = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onBackground
            ),
            padding = 10.dp,
            position = IndicatorPosition.Horizontal.End,
            contentBuilder = {
                "%.0f Kwh".format(it)
            },
            count = IndicatorCount.CountBased(
                count = 3
            )
        ),
        labelHelperProperties = LabelHelperProperties(
            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onBackground)
        ),
        labelProperties = LabelProperties(
            enabled = true,
            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onBackground)
        ),
        gridProperties = GridProperties(
            yAxisProperties = GridProperties.AxisProperties(
                enabled = false
            ),
            xAxisProperties = GridProperties.AxisProperties(
                lineCount = 3
            ),
        ),
        dividerProperties = DividerProperties(
            yAxisProperties = LineProperties(
                enabled = false
            )
        ),
        barProperties = BarProperties(
            cornerRadius = Bars.Data.Radius.Rectangle(topRight = 6.dp, topLeft = 6.dp),
            spacing = 3.dp,
            thickness = 20.dp
        ),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun InvoiceListScreenPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        InvoiceListScreen()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NotPaidInvoicePreview() {
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
fun PaidInvoicePreview() {
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

    val dateFormatted = StringBuilder(date.format(outputFormatter))
    dateFormatted[3] = dateFormatted[3].uppercaseChar()


    return dateFormatted.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun getInvoicesAmountPerMonth(invoices: List<InvoiceEntity>): Map<String, Double> {
    val invoicesPerMonth = mutableMapOf<String, Double>()

    for (invoice in invoices) {
        val month = invoice.parseDate().substring(3..5) + " " + invoice.parseDate().substring(9, 11)
        val amount = invoice.amount

        if (invoicesPerMonth.containsKey(month)) {
            invoicesPerMonth[month] = invoicesPerMonth[month]!! + amount
        } else {
            invoicesPerMonth[month] = amount
        }
    }

    return invoicesPerMonth
}

@RequiresApi(Build.VERSION_CODES.O)
fun getInvoicesKwhPerMonth(invoices: List<InvoiceEntity>): Map<String, Double> {
    val invoicesPerMonth = mutableMapOf<String, Double>()

    for (invoice in invoices) {
        val month = invoice.parseDate().substring(3..5) + " " + invoice.parseDate().substring(9, 11)
        val kwh = invoice.kwh

        if (invoicesPerMonth.containsKey(month)) {
            invoicesPerMonth[month] = invoicesPerMonth[month]!! + kwh
        } else {
            invoicesPerMonth[month] = kwh
        }
    }

    return invoicesPerMonth
}

@SuppressLint("DefaultLocale")
fun InvoiceEntity.parseAmount(): String {
    return String.format("%.2f €", amount)
}