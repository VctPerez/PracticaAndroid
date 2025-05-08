package com.viewnext.practicaandroid.core.ui.screens


import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.viewnext.practicaandroid.R
import com.viewnext.practicaandroid.core.ui.CoilImage
import com.viewnext.practicaandroid.core.ui.theme.IberBlue
import com.viewnext.practicaandroid.core.ui.theme.IberGreen
import com.viewnext.practicaandroid.core.ui.theme.IberOrange
import com.viewnext.practicaandroid.core.ui.theme.LightIberGreen
import com.viewnext.practicaandroid.core.ui.theme.NewsGray
import com.viewnext.practicaandroid.core.ui.theme.Pink90
import com.viewnext.practicaandroid.core.ui.theme.PracticaAndroidTheme
import com.viewnext.practicaandroid.core.ui.theme.RetromockLighRed
import com.viewnext.practicaandroid.core.ui.viewmodel.NewsViewModel
import com.viewnext.practicaandroid.dataretrofit.DefaultAppContainer
import com.viewnext.practicaandroid.domain.data.NewsArticle

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToInvoiceList: () -> Unit = {},
    navigateToSmartSolar: () -> Unit = {}
) {

    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        floatingActionButton = {
            RetromockActionButton()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize().background(MaterialTheme.colorScheme.surface)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(350.dp)
                    .height(100.dp)
            )

            Row(Modifier
                .fillMaxWidth()
                .padding(12.dp)) {
                MainScreenButton(
                    text = stringResource(R.string.invoicelist_title),
                    icon = Icons.Rounded.ShoppingCart,
                    containerColor = IberOrange,
                    modifier = Modifier.weight(1f),
                    onClick = navigateToInvoiceList
                )
                Spacer(modifier = Modifier.width(12.dp))
                MainScreenButton(
                    text = "Smart Solar",
                    icon = Icons.Default.Home,
                    containerColor = IberBlue,
                    modifier = Modifier.weight(1f),
                    onClick = navigateToSmartSolar
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 12.dp))
            Text(
                stringResource(R.string.newsTitle), style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            NewsContent()
        }
    }
}

@Composable
fun NewsContent(){
    val newsViewModel : NewsViewModel = viewModel(factory = NewsViewModel.Factory)
    val state = newsViewModel.uiState.collectAsState()

    if (state.value.isLoading){
        // Show loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = IberGreen,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    } else if (state.value.error != null) {
        // Show error message
        Text("Error: ${state.value.error}")
    } else {
        // Show news list
        NewsList(state.value.news)
    }
}

@Composable
fun RetromockActionButton() {
    val context = LocalContext.current
    val mockStatus = remember{ mutableStateOf(DefaultAppContainer.isMocking())}

    ExtendedFloatingActionButton(
        onClick = {
            DefaultAppContainer.toggleMocking()
            mockStatus.value = !mockStatus.value
            Toast.makeText(
                context,
                "Retromock ${
                    if (DefaultAppContainer.isMocking()) context.getString(R.string.retromockEnabled) 
                    else context.getString(R.string.retormockDisabled)
                }",
                Toast.LENGTH_SHORT
            ).show()
        },
        shape = CircleShape,
        containerColor = if(mockStatus.value) IberGreen else RetromockLighRed,
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
}

@Composable
fun MainScreenButton(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .aspectRatio(1f) // Cuadrado
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text, color = Color.White,
                fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium
            )

        }
    }
}

@Composable
fun NewsList(
    newsList: List<NewsArticle>,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 8.dp, end = 8.dp, bottom = 5.dp, top = 5.dp)
        ) {
            items(newsList) {
                NewsCard(it)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background, // o el fondo de tu app
                            Color.Transparent
                        )
                    )
                )
        )

        // Fade abajo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background // o el fondo de tu app
                        )
                    )
                )
        )
    }
}

@Composable
fun NewsCard(newsArticle: NewsArticle){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NewsGray),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CoilImage(
                imageUrl = newsArticle.urlToImage!!,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .clip(RoundedCornerShape(16.dp))
//                    .border(BorderStroke(2.dp, IberGreen), RoundedCornerShape(16.dp))
            )
            Column(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()) {
                Text(text = newsArticle.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    modifier = Modifier
                        .wrapContentWidth()
                        .basicMarquee())
                Spacer(Modifier.size(5.dp))
                Text(text = newsArticle.description?: newsArticle.title,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    modifier = Modifier.wrapContentWidth())
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun MainScreenPreview() {
    PracticaAndroidTheme {
        MainScreen()
    }
}

@Preview
@Composable
fun MainScreenButtonPreview() {
    PracticaAndroidTheme {
        Row(Modifier
            .fillMaxWidth()
            .padding(6.dp)) {
            MainScreenButton(
                text = "Facturas",
                icon = Icons.Default.Home,
                containerColor = IberGreen,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            MainScreenButton(
                text = "Facturas",
                icon = Icons.Default.Home,
                containerColor = IberGreen,
                modifier = Modifier.weight(1f)
            )
        }

    }
}