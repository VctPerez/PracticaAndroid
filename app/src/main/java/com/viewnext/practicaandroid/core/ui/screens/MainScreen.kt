package com.viewnext.practicaandroid.core.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.infinum.retromock.Retromock
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.IberBlue
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.theme.IberOrange
import com.viewnext.practicaandroid.core.ui.theme.LightIberGreen
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            RetromockActionButton()
        }
    ) {innerPadding ->
        Column(modifier = modifier.fillMaxSize().background(LightIberGreen).padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(350.dp)
            )
            MainScreenButton("Lista de facturas",
                containerColor = IberBlue, modifier = Modifier.weight(0.5f))
            MainScreenButton("Smart Solar",
                containerColor = IberOrange, modifier = Modifier.weight(1f))

        }
    }

}

@Composable
fun RetromockActionButton() =
    ExtendedFloatingActionButton(
        onClick = { /*TODO*/ },
        shape = CircleShape,
        containerColor = IberGreen,
        icon = {
            Icon(
                painter = painterResource(R.drawable.ic_retromock),
                contentDescription = "Info",
                modifier = Modifier.size(50.dp),
                tint = Color.Red,
            )
        },
        text = {
            Text(
                text = "Retromock",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        },
    )


@Composable
fun MainScreenButton(text : String, modifier: Modifier = Modifier, containerColor: Color) {
    ElevatedButton(
        onClick = { /*TODO*/ },
        content = {
            Text(text, style = MaterialTheme.typography.titleMedium)
        },
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = Color.White,
            disabledContainerColor = containerColor,
            disabledContentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 10.dp,
            pressedElevation = 20.dp,
            hoveredElevation = 20.dp,
            focusedElevation = 20.dp
        ),
    )
}

@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    PracticaAndroidTheme {
        MainScreen()
    }
}