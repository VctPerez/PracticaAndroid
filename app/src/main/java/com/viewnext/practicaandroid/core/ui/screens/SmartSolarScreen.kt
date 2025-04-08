package com.viewnext.practicaandroid.core.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme

@Composable
fun SmartSolarScreen(modifier: Modifier = Modifier){

    val tabs = listOf("Mi instalacion", "Energía", "Detalles")
    var selectedTabIndex by remember { mutableIntStateOf(1) }

    Column(modifier = modifier.padding(start = 22.dp, end = 30.dp)) {
        ScrollableTabRow(selectedTabIndex = selectedTabIndex,
            modifier = Modifier.wrapContentWidth(align = Alignment.Start),
            edgePadding = 0.dp, contentColor = MaterialTheme.colorScheme.onBackground) {
            tabs.forEachIndexed{ index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> InstalationTab()
            1 -> EnergyTab()
            2 -> DetailsTab()
        }
    }
}

@Composable
fun InstalationTab(){
    Column(modifier = Modifier.padding(top = 10.dp)){
        Text("Aquí tienes los datos de tu instalación fotovoltaica en tiempo real.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.size(30.dp))
        SelfConsumptionText("92%")

        Image(
            painter = painterResource(R.drawable.grafico1),
            contentDescription = "Grafico de autoconsumo",
            modifier = Modifier.size(350.dp)
        )
    }
}

@Composable
fun SelfConsumptionText(value : String){
    Row {
        Text("Autoconsumo:",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(end = 7.dp)
        )
        Text(
            value,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun EnergyTab(){
    Column(modifier = Modifier.fillMaxWidth().padding(top = 60.dp),
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.plan_gestiones),
            contentDescription = "Plan de gestiones",
            Modifier.size(240.dp)
        )
        Spacer(Modifier.size(75.dp))
        Text("Estamos trabajando en mejorar la App. Tus paneles solares siguen produciendo, " +
                "en breve podrás seguir viendo tu producción solar. Sentimos las molestias.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        )
    }
}

@Composable
fun DetailsTab(){
    Column {
        TODO()
    }
}

@Composable
@Preview(showBackground = true)
fun SmartSolarScreenPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        SmartSolarScreen()
    }
}
