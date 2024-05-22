package com.example.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.playground.tutorial01_typesafe_compose_navigation.TypeSafeComposeNavigationScreen
import com.example.playground.ui.theme.PlaygroundTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = TutorialRoute.Catalog) {
                        composable<TutorialRoute.Catalog> {
                            CatalogScreen(
                                modifier = Modifier.padding(innerPadding),
                                onTutorialSelected = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                        composable<TutorialRoute.TypeSafeComposeNavigation> {
                            TypeSafeComposeNavigationScreen()
                        }
                    }
                }
            }
        }
    }
}

@Serializable
sealed interface TutorialRoute {

    @Serializable
    data object Catalog : TutorialRoute

    @Serializable
    data object TypeSafeComposeNavigation : TutorialRoute
}

data class Tutorial(val title: String, val route: TutorialRoute)

val tutorials by lazy {
    listOf(
        Tutorial(title = "1. Type-Safe Compose Navigation", route = TutorialRoute.TypeSafeComposeNavigation)
    )
}

@Composable
fun CatalogScreen(modifier: Modifier = Modifier, onTutorialSelected: (route: TutorialRoute) -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tutorials.forEach { (title: String, route: TutorialRoute) ->
            Button(
                onClick = {
                    onTutorialSelected(route)
                },
                content = {
                    Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatalogPreview() {
    PlaygroundTheme {
        CatalogScreen(onTutorialSelected = {})
    }
}