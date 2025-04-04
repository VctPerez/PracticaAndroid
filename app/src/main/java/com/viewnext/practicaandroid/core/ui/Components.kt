@file:OptIn(ExperimentalMaterial3Api::class)

package com.viewnext.practicaandroid.core.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title : String, filter : Boolean){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { TODO() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier.size(35.dp).padding(0.dp),
                )
            }
        },
        title = { Text(title, style = MaterialTheme.typography.titleMedium) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        actions = {
            if (filter) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(R.drawable.filtericon_3x),
                        contentDescription = "Filter",
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