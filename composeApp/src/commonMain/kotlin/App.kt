import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.dsl.module

import com.jdw.todo.components.HomePage

val primaryLight = Color(0xFF904A45)
val primaryDark = Color(0xFFFFB3AD)
@Composable
@Preview
fun App() {

    val lightColors = lightColorScheme(
        primary = primaryLight,
        onPrimary = primaryDark,
        primaryContainer = primaryLight,
        onPrimaryContainer = primaryDark
    )
    val darkColors = darkColorScheme(
        primary = primaryLight,
        onPrimary = primaryDark,
        primaryContainer = primaryLight,
        onPrimaryContainer = primaryDark
    )
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )

    MaterialTheme(colorScheme = colors) {
        Navigator(HomePage()) {
            SlideTransition(it)
        }


    }
}
