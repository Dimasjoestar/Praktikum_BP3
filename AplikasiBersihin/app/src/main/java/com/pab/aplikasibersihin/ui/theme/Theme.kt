package com.pab.aplikasibersihin.ui.theme

import android.app.Activity

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.pab.aplikasibersihin.utils.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryTeal,
    secondary = AccentMint,
    tertiary = OnPrimaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceDarkVariant,
    onPrimary = SurfaceLight,
    onSecondary = SurfaceLight,
    onBackground = TextDarkOnDark,
    onSurface = TextDarkOnDark,
    onSurfaceVariant = TextLightOnDark,
    outline = Color(0xFF5A7A7A),
    outlineVariant = Color(0xFF3A5050)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryTeal,
    secondary = AccentMint,
    tertiary = DarkTeal,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = Color(0xFFE8EDED),
    onPrimary = OnPrimaryLight,
    onSecondary = OnPrimaryLight,
    onBackground = TextDark,
    onSurface = TextDark,
    onSurfaceVariant = Color(0xFF78909C),
    outline = Color(0xFFB0BEC5),
    outlineVariant = Color(0xFFCFD8DC)
)

// Custom colors not covered by MaterialTheme
data class BersihinCustomColors(
    val lightTealBg: Color,
    val subtitleOnHeader: Color
)

val LocalBersihinColors = compositionLocalOf {
    BersihinCustomColors(
        lightTealBg = LightTealBgLight,
        subtitleOnHeader = Color.White.copy(alpha = 0.8f)
    )
}

@Composable
fun AplikasiBersihinTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val customColors = if (darkTheme) {
        BersihinCustomColors(
            lightTealBg = LightTealBgDark,
            subtitleOnHeader = Color.White.copy(alpha = 0.8f)
        )
    } else {
        BersihinCustomColors(
            lightTealBg = LightTealBgLight,
            subtitleOnHeader = Color.White.copy(alpha = 0.8f)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as? Activity)?.window
        if (window != null) {
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(LocalBersihinColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}