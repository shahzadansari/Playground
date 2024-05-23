## 1. Type-Safe Compose Navigation

<details>
  <summary><b>Implementation</b></summary>
  
  <b><br>Make sure to annotate destinations and custom parcelables with `@Serializable`<br></b>
  
  ### 1. Dependencies

  #### `libs.versions.toml`
  ```toml
  [versions]
  kotlinxSerializationJson = "1.6.3"
  kotlinxSerialization = "1.9.0"
  navigationCompose = "2.8.0-alpha08"
    
  [libraries]
  androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
  kotlinx-serialization-json = { module = "org.jetbrains.kotlinx:kotlinx-serialization-json", version.ref = "kotlinxSerializationJson" }
    
  [plugins]
  jetbrains-kotlin-serialization = { id ="org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlinxSerialization"}

  ```

  #### `build.gradle.kts` (Project)
  ```kotlin
  plugins {
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
  }
  ```

  #### `build.gradle.kts` (Module)
  ```kotlin
  plugins {
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-parcelize")
  }
    
  depencencies {
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
  }
  ```
  
  ### 2. Define Destinations
  ```kotlin

  // Defines a home destination that doesn't take any arguments
  @Serializable
  object Home
  
  // Defines a profile destination that takes an ID
  @Serializable
  data class Profile(val id: String)

  ```

  ### 3. Define NavHost
  ```kotlin

  NavHost(navController = navController, startDestination = Home) {
    composable<Home> {
        HomeScreen()
    }

    composable<Profile> { backStackEntry: NavBackStackEntry ->
        val args = backStackEntry.toRoute<Profile>()
        ProfileScreen(id = args.id)
    }
}
 
  ```

  ### 4. Pass complex data
  
  1. Annotate your `data class` with `@Parcelize` and implement `Parcelable` interface
  2. Define Custom `NavType` for that `data class` - [Generic mapper for CustomNavType](https://github.com/shahzadansari/Playground/blob/1c1a24b8bf6adc924852c9387ff6b27e0bf2c259/app/src/main/java/com/example/playground/tutorial01_typesafe_compose_navigation/ComposeNavigationExt.kt#L13)
  
  ```kotlin
  
  @Serializable
  @Parcelize
  data class User(val name: String, val age: Int) : Parcelable
  
  ```

  #### 4.1 Update Destination to take Custom Parcelable as args

  ```kotlin
  composable<Profile>(
      typeMap = mapOf(typeOf<User>() to CustomNavType(User::class.java, User.serializer()))
  ) { backStackEntry: NavBackStackEntry ->
      val args = backStackEntry.toRoute<Profile>()
      ProfileScreen(user = args.user)
  }
  ```
  
</details>


### Demo

https://github.com/shahzadansari/Playground/assets/43310446/76be49db-2682-4d37-9fcd-6609b4b90423

