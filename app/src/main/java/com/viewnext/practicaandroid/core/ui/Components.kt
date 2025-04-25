@file:OptIn(ExperimentalMaterial3Api::class)

package com.viewnext.practicaandroid.core.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.InvoiceFilterViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title : String,
    onInvoiceList : Boolean = false,
    onFilter : Boolean = false,
    onBackButtonClick : () -> Unit = {},
    navigateToFilter : () -> Unit = {}
) {
    TopAppBar(
        navigationIcon = { if(!onFilter) {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp).padding(0.dp),
                )
            }
        }
        },
        title = { if(!onFilter){Text(title, style = MaterialTheme.typography.titleMedium)}},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            if (onInvoiceList) {
                IconButton(onClick = navigateToFilter) {
                    Image(
                        painter = painterResource(R.drawable.filtericon_3x),
                        contentDescription = "Filter",
                    )
                }
            }else if(onFilter){
                IconButton(onClick = onBackButtonClick){
                    Image(
                        painter = painterResource(R.drawable.close_icon),
                        contentDescription = "Back",
                    )
                }
            }
        }
    )
}

@Composable
@Preview
fun CustomTopBarPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        CustomTopBar("Facturas", true)
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CustomTopBarPreviewDark(){
    PracticaAndroidTheme(dynamicColor = false) {
        CustomTopBar("Facturas",true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun IberDialogPopup(title : String, message : String, buttonText : String,
                   onDismiss: () -> Unit = {}){
    Dialog(
        onDismissRequest = onDismiss

    ) {
        Card(modifier = Modifier.fillMaxWidth().padding(10.dp),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween)
            {
                Text(text=title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
//                    fontSize = 30.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = buttonText)
                }
            }
        }

    }
}

@Composable
@Preview
fun IberDialogPopupPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        IberDialogPopup("Title", "Message", "OK")
    }
}

@Composable
@Preview(showSystemUi = true)
fun DatePickerModalPreview(){
    PracticaAndroidTheme(dynamicColor = false) {
        DatePickerModal(onDateSelected = {}, onDismiss = {})
    }
}