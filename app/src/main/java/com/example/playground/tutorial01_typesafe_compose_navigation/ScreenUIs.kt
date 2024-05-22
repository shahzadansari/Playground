package com.example.playground.tutorial01_typesafe_compose_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playground.Empty
import com.example.playground.mock.mock
import com.example.playground.tutorial01_typesafe_compose_navigation.NavigationCompose.ScreenA
import com.example.playground.tutorial01_typesafe_compose_navigation.NavigationCompose.ScreenB
import com.example.playground.ui.theme.PlaygroundTheme

/**
 * Composables ScreenA() and ScreenB() and kept inside `object` NavigationCompose to reduce their visibility in other files. They can still be accessed via NavigationCompose.ScreenA() and NavigationCompose.ScreenB() respectively.
 * */
object NavigationCompose {

    @Composable
    fun ScreenA(
        onNavigateForward: (amount: Int, user: User) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var amount by rememberSaveable { mutableIntStateOf(0) }
        var user by rememberSaveable { mutableStateOf(User(name = String.Empty, age = 0)) }

        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Screen A", fontSize = 30.sp)

            TextField(
                label = {
                    Text("Enter amount to send")
                },
                value = amount.toString().takeIf { amount != 0 }.orEmpty(),
                onValueChange = { newValue ->
                    amount = newValue.toIntOrNull() ?: 0
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextField(
                label = {
                    Text("Enter name")
                },
                value = user.name,
                onValueChange = { newValue ->
                    user = user.copy(name = newValue)
                }
            )

            TextField(
                label = {
                    Text("Enter age")
                },
                value = user.age.toString().takeIf { user.age != 0 }.orEmpty(),
                onValueChange = { newValue ->
                    user = user.copy(age = newValue.toIntOrNull() ?: 0)
                }
            )

            Button(
                onClick = {
                    val userDetails = user ?: User.mock // Ideally, user shouldn't be able to process if `User` is mandatory
                    onNavigateForward(amount, userDetails)
                },
                content = {
                    Text("Send to Screen B")
                }
            )
        }
    }

    @Composable
    fun ScreenB(amount: Int, user: User, onNavigateBack: () -> Unit, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Screen B", fontSize = 30.sp)

            Text("Amount Received: $$amount")
            Text("User: $user")

            Button(
                onClick = onNavigateBack,
                content = {
                    Text("Go Back")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenAPreview() {
    PlaygroundTheme {
        ScreenA(onNavigateForward = { _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenBPreview() {
    PlaygroundTheme {
        ScreenB(amount = 100, user = User.mock, onNavigateBack = {})
    }
}