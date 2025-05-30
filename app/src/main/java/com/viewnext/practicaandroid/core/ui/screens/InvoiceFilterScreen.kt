@file:OptIn(ExperimentalMaterial3Api::class)

package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.DatePickerModal
import com.viewnext.practicaandroid.core.ui.IberDialogPopup
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceFilterViewModel
import com.viewnext.practicaandroid.domain.data.InvoiceFilter
import com.viewnext.practicaandroid.domain.data.isDefaultEndDate
import com.viewnext.practicaandroid.domain.data.isDefaultStartDate
import com.viewnext.practicaandroid.domain.data.minDateGreaterThanEndDate
import com.viewnext.practicaandroid.domain.parseDateFromYYYYMMDD
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun InvoiceFilterScreen(navController: NavController?, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    val invoicesBackStackEntry: NavBackStackEntry = remember {
        navController?.getBackStackEntry("invoices")!!
    }
    val viewModel: InvoiceFilterViewModel = viewModel(
        invoicesBackStackEntry,
        factory = InvoiceFilterViewModel.Factory,
        key = "InvoiceFilterViewModel",
    )

    val filterState = viewModel.uiState.collectAsState()
    Log.d("Filter", "InvoiceFilterViewModel: ${viewModel.uiState.collectAsState().value}")

    val newFilter = remember { mutableStateOf(filterState.value) }

    val dateError = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }

    if(showErrorDialog.value){
        IberDialogPopup(
            stringResource(R.string.datePickerErrorTitle),
            stringResource(R.string.datePickerErrorMessage),
            error = true,
            onDismiss = {
                showErrorDialog.value = false
//                dateError.value = false
            },
            buttonText = stringResource(R.string.errorDialogButton)
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 22.dp, end = 22.dp)
            .verticalScroll(scrollState)
    )
    {
        Text(stringResource(R.string.invoice_filter), style = MaterialTheme.typography.titleLarge)

        DateRangeInvoiceFilter(newFilter, dateError)
        FilterDivider()

        RangeSliderAmount(0f..300f, newFilter)
        FilterDivider()

        StatusFilter(newFilter)

        Spacer(Modifier.size(50.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (newFilter.value.minDateGreaterThanEndDate()) {
                            showErrorDialog.value = true
                            dateError.value = true
                        } else {
                            viewModel.setFilter(newFilter.value)
                            navController?.popBackStack("invoices", inclusive = false)
                        }
                    },
                    colors = ButtonColors(
                        containerColor = IberGreen,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White,
                    )
                ) {
                    Text(stringResource(R.string.applyFiltersButton), textAlign = TextAlign.Center)
                }
                TextButton(
                    onClick = { newFilter.value = InvoiceFilter() },
                    colors = ButtonColors(
                        contentColor = Color.Gray,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = Color.LightGray
                    )
                ) {
                    Text(stringResource(R.string.clearFiltersButton), textAlign = TextAlign.Center)
                }
            }

        }
    }


}

@Composable
fun FilterDivider() {
    Spacer(Modifier.size(40.dp))
    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
}

@Composable
fun DateRangeInvoiceFilter(filter: MutableState<InvoiceFilter>, dateError: MutableState<Boolean>) {

    val startDate =
        if (filter.value.isDefaultStartDate()) "" else parseDateFromYYYYMMDD(filter.value.startDate)
    val endDate =
        if (filter.value.isDefaultEndDate()) "" else parseDateFromYYYYMMDD(filter.value.endDate)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Text(
            stringResource(R.string.filterIssueDate),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )
        Row {
            DatePickerInvoice(stringResource(R.string.datePickerFrom), startDate, filter, dateError, Modifier.weight(1f))
            Spacer(Modifier.weight(0.1f))
            DatePickerInvoice(stringResource(R.string.datePickerTo), endDate, filter, dateError, Modifier.weight(1f))
        }

    }
}

@Composable
fun DatePickerInvoice(
    text: String,
    value: String,
    filter: MutableState<InvoiceFilter>,
    dateError: MutableState<Boolean>,
    modifier: Modifier = Modifier,
) {
    var selectedDate by remember { mutableStateOf<String?>(value) }
    var showModal by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text, color = Color.LightGray)
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(stringResource(R.string.datePickerLabel)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (dateError.value) Color.Red else MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = if (dateError.value) Color.Red else MaterialTheme.colorScheme.outline,
                errorBorderColor = Color.Red,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .pointerInput(selectedDate) {
                    awaitEachGesture {
                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                        // in the Initial pass to observe events before the text field consumes them
                        // in the Main pass.
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (upEvent != null) {
                            showModal = true
                        }
                    }
                }
        )

        if (showModal) {
            val fromString = stringResource(R.string.datePickerFrom)
            DatePickerModal(
                onDateSelected = { date ->
                    selectedDate = date?.let { convertMillisToDate(it) } ?: ""
                    if (text.lowercase() == fromString.lowercase()) {
//                        Log.d("DatePickerInvoice", "Desde: $it")
//                        viewModel.setStartDate(selectedDate!!)
                        filter.value = filter.value.copy(startDate = selectedDate!!)
                    } else {
//                        Log.d("DatePickerInvoice", "Hasta: $selectedDate")
//                        viewModel.setEndDate(selectedDate!!)
                        filter.value = filter.value.copy(endDate = selectedDate!!)
                    }
                    dateError.value = false
                },
                onDismiss = { showModal = false }
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RangeSliderAmount(
    //onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    filter: MutableState<InvoiceFilter>,
    steps: Int = 0
) {
    var sliderValue by remember { mutableStateOf(valueRange) }

    Column(Modifier.padding(top = 30.dp)) {
        Text(
            stringResource(R.string.filterAmount),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )

        Text(
            (filter.value.minAmount..filter.value.maxAmount).toFormatString(),
            color = IberGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                String.format("%.0f €", valueRange.start),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                String.format("%.0f €", valueRange.endInclusive),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        RangeSlider(
            value = filter.value.minAmount..filter.value.maxAmount,
            onValueChange = { range ->
                sliderValue = range
                filter.value =
                    filter.value.copy(minAmount = range.start, maxAmount = range.endInclusive)
//                viewModel.setMinAmount(range.start)
//                viewModel.setMaxAmount(range.endInclusive)
            },
            valueRange = valueRange,
            steps = steps,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = IberGreen,
                inactiveTrackColor = Color.LightGray,
                activeTrackColor = IberGreen,
            )
        )
    }
}

@Composable
fun StatusFilter(filter: MutableState<InvoiceFilter>) {
    Column(Modifier.padding(top = 30.dp)) {
        Text(
            stringResource(R.string.filterStatus),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleSmall
        )
        StatusItem(
            stringResource(R.string.filterCheckBoxPaid),
            filter.value.isPaid,
            onChange = { filter.value = filter.value.copy(isPaid = it) }
        )
        StatusItem(
            stringResource(R.string.filterCheckBox),
            filter.value.isCancelled,
            onChange = { filter.value = filter.value.copy(isCancelled = it) })
        StatusItem(
            stringResource(R.string.filterCheckBoxFixedFee),
            filter.value.isFixedFee,
            onChange = { filter.value = filter.value.copy(isFixedFee = it) })
        StatusItem(
            stringResource(R.string.filterCheckBoxPending),
            filter.value.isPending,
            onChange = { filter.value = filter.value.copy(isPending = it) })
        StatusItem(
            stringResource(R.string.filterCheckBoxPlan),
            filter.value.isPaymentPlan,
            onChange = { filter.value = filter.value.copy(isPaymentPlan = it) })
    }
}

@Composable
fun StatusItem(text: String, value: Boolean, onChange: (Boolean) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(value, onCheckedChange = onChange)
        Text(text, modifier = Modifier.clickable { onChange(!value)})
    }
}


@SuppressLint("DefaultLocale")
private fun ClosedFloatingPointRange<Float>.toFormatString(): String {
    return String.format("%.0f € - %.0f €", start, endInclusive)
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
@Preview(showBackground = true)
fun InvoiceFilterScreenPreview() {
    PracticaAndroidTheme {
        InvoiceFilterScreen(null)
    }
}