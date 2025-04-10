@file:OptIn(ExperimentalMaterial3Api::class)

package com.viewnext.practicaandroid.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title : String,
    onInvoiceList : Boolean,
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
                IconButton(onClick = onBackButtonClick) {
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
        CustomTopBar("Facturas", true)
    }
}

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
        }
    ) {
        DatePicker(state = datePickerState)
    }
}