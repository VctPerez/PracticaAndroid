package com.viewnext.practicaandroid.core.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.IberDialogPopup
import com.viewnext.practicaandroid.core.ui.theme.InfoBlue
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.viewmodel.UserViewModel
import com.viewnext.practicaandroid.domain.data.UserDetailsResponse

@Composable
fun SmartSolarScreen(modifier: Modifier = Modifier){

    val tabs = listOf(stringResource(R.string.smartSolarFixture),
        stringResource(R.string.smartSolarEnergy), stringResource(R.string.smartSolarDetails)
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val viewModel : UserViewModel = viewModel(factory = UserViewModel.Factory)
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.padding(start = 22.dp, end = 30.dp)) {
        Text("Smart Solar",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.size(25.dp))
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
            2 -> DetailsTab(state.userDetails)
        }
    }
}

@Composable
fun InstalationTab(){
    Column(modifier = Modifier.padding(top = 10.dp)){
        Text(
            stringResource(R.string.fixtureMessage),
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
        Text(
            stringResource(R.string.fixtureSelfConsumption),
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
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 60.dp),
            verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.plan_gestiones),
            contentDescription = "Plan de gestiones",
            Modifier.size(240.dp)
        )
        Spacer(Modifier.size(75.dp))
        Text(
            stringResource(R.string.smartSolarEnergyMessage),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
        )
    }
}

@Composable
fun DetailsTab(userDetails: UserDetailsResponse){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp),){
        DetailsTextField(userDetails.cau, {}, stringResource(R.string.smartSolarDetailsCAU))
        DetailsTextField(userDetails.requestStatus, {},
            stringResource(R.string.smartSolarDetailsStatus), info = true)
        DetailsTextField(userDetails.type, {},
            stringResource(R.string.smartSolarSelfConsumptionType))
        DetailsTextField(userDetails.compensation, {},
            stringResource(R.string.smartSolarDetailsCompensation))
        DetailsTextField(userDetails.installationPower, {},
            stringResource(R.string.smartSolarDetailsPower))
    }
}

@Composable
fun DetailsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    info : Boolean = false
) {
    var showRequestStatus by remember { mutableStateOf(false) }
    if(showRequestStatus){
        IberDialogPopup(
            stringResource(R.string.requestStatusTitle),
            stringResource(R.string.requestStatusMessage),
            stringResource(R.string.requestStatusButton), onDismiss = {
                showRequestStatus = false
            })
    }

    TextField(
        value = value,
        enabled = false,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        colors = TextFieldDefaults.colors(
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray,
            disabledLabelColor = Color.Gray,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            disabledTextColor = MaterialTheme.colorScheme.onBackground,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray
        ),
        trailingIcon = {
            if (info) {
                IconButton(
                    onClick = {showRequestStatus = true}
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info",
                        modifier = Modifier.size(33.dp),
                        tint = InfoBlue
                    )
                }
//                Icon(
//                    imageVector = Icons.Outlined.Info,
//                    contentDescription = "Info",
//                    modifier = Modifier.size(33.dp),
//                    tint = InfoBlue
//                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun SmartSolarScreenPreview() {
    PracticaAndroidTheme(dynamicColor = false) {
        SmartSolarScreen()
    }
}
