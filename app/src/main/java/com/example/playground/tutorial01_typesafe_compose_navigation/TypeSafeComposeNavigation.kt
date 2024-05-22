package com.example.playground.tutorial01_typesafe_compose_navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.playground.tutorial01_typesafe_compose_navigation.NavigationCompose.ScreenA
import com.example.playground.tutorial01_typesafe_compose_navigation.NavigationCompose.ScreenB
import com.example.playground.ui.theme.PlaygroundTheme
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Composable
fun TypeSafeComposeNavigationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.ScreenA) {
        composable<Route.ScreenA> {
            ScreenA(
                onNavigateForward = { amount, user ->
                    val route = Route.ScreenB(amount = amount, user = user)
                    navController.navigate(route)
                }
            )
        }

        composable<Route.ScreenB>(
            typeMap = mapOf(typeOf<User>() to CustomNavType(User::class.java, User.serializer()))
        ) { backStackEntry: NavBackStackEntry ->
            val args = backStackEntry.toRoute<Route.ScreenB>()
            ScreenB(
                amount = args.amount,
                user = args.user,
                onNavigateBack = navController::navigateUp
            )
        }
    }
}

@Serializable
sealed interface Route {

    @Serializable
    data object ScreenA : Route

    @Serializable
    data class ScreenB(val amount: Int, val user: User) : Route
}

@Serializable
@Parcelize
data class User(val name: String, val age: Int) : Parcelable {
    companion object
}


@Preview(showBackground = true)
@Composable
private fun TypeSafeComposeNavigationScreenPreview() {
    PlaygroundTheme {
        TypeSafeComposeNavigationScreen()
    }
}