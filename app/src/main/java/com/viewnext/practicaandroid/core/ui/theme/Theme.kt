package com.viewnext.practicaandroid.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = IberGreen,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = Color(0xFF414855),
    surfaceContainer = Color(0xFF414855),
    surfaceContainerHigh = Color(0xFF414855),
    onSurface = Color.Black,
    background = Color.White,
    onBackground = IberGreen,
    error = Color.Red

    )

private val LightColorScheme = lightColorScheme(
    primary = IberGreen,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = Color.White,
    surfaceContainer = Color.White,
    surfaceContainerHigh = Color.White,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    error = Color.Red

    /*Other default colors to override
    onPrimary = Color.White,
    onSecondary = IberGreen,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),

     */
)

@Composable
fun PracticaAndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}