@file:OptIn(ExperimentalMaterial3Api::class)

package com.viewnext.practicaandroid.core.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.DatePickerModal
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InvoiceFilterScreen() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 22.dp, end = 22.dp)){
        Text(stringResource(R.string.invoice_filter), style = MaterialTheme.typography.titleLarge)
        DateRangeInvoiceFilter()
        FilterDivider()
        RangeSliderAmount(0f, 0f..300f)
        FilterDivider()
        StatusFilter()
        Spacer(Modifier.size(50.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { TODO()}, colors = ButtonColors(
                containerColor = IberGreen,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White,
            ), modifier = Modifier.padding(bottom = 20.dp)) {
                Text("Aplicar filtros", textAlign = TextAlign.Center)
            }
        }

    }
}

@Composable
fun FilterDivider(){
    Spacer(Modifier.size(40.dp))
    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
}

@Composable
fun DateRangeInvoiceFilter() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp)) {
        Text("Con fecha de emisión:", fontWeight = FontWeight.Bold ,style = MaterialTheme.typography.titleSmall)
        Row {
            DatePickerInvoice("Desde:", Modifier.weight(1f))
            Spacer(Modifier.weight(0.1f))
            DatePickerInvoice("Hasta:", Modifier.weight(1f))
        }

    }
}

@Composable
fun DatePickerInvoice(text : String, modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(text, color = Color.LightGray)
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = { },
            label = { Text("dia/mes/año") },
            placeholder = { Text("DD/MM/YYYY") },
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
            DatePickerModal(
                onDateSelected = { selectedDate = it },
                onDismiss = { showModal = false }
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RangeSliderAmount(
    value: Float,
    //onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    steps: Int = 0
) {
    var sliderValue by remember { mutableStateOf(valueRange)}

    Column(Modifier.padding(top = 30.dp)){
        Text("Por un importe", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)

        Text(sliderValue.toFormatString(), color = IberGreen, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Row(modifier = Modifier.fillMaxWidth()) {
            Text( String.format("%.0f €", valueRange.start), modifier = Modifier.weight(1f), textAlign = TextAlign.Start)
            Text( String.format("%.0f €", valueRange.endInclusive), modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        }
        RangeSlider(
            value = sliderValue,
            onValueChange = { range -> sliderValue = range },
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
fun StatusFilter(){
    Column(Modifier.padding(top = 30.dp)) {
        Text("Por estado", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
        StatusItem("Pagadas")
        StatusItem("Anuladas")
        StatusItem("Cuota Fija")
        StatusItem("Pendientes de pago")
        StatusItem("Plan de pago")
    }
}

@Composable
fun StatusItem(text : String){
    Row(modifier = Modifier.fillMaxWidth().padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(false, {})
        Text(text)
    }
}


private fun ClosedFloatingPointRange<Float>.toFormatString() : String {
    return String.format("%.0f € - %.0f €", start, endInclusive)
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Composable
@Preview(showBackground = true)
fun InvoiceFilterScreenPreview() {
    PracticaAndroidTheme {
        InvoiceFilterScreen()
    }
}