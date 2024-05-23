## 1. Type-Safe Compose Navigation

<details>
  <summary><b>Implementation</b></summary>
  
  <b><br>Make sure to annotate destinations and custom parcelables with `@Serializable`<br></b>
  
  ### 1. Define Destinations
  ```kotlin

  // Defines a home destination that doesn't take any arguments
  @Serializable
  object Home
  
  // Defines a profile destination that takes an ID
  @Serializable
  data class Profile(val id: String)

  ```

  ### 2. Define NavHost
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

  ### 3. Pass complex data
  
  1. Annotate your `data class` with `@Parcelize` and implement `Parcelable` interface
  2. Define Custom `NavType` for that `data class` - [Generic mapper for CustomNavType](https://github.com/shahzadansari/Playground/blob/1c1a24b8bf6adc924852c9387ff6b27e0bf2c259/app/src/main/java/com/example/playground/tutorial01_typesafe_compose_navigation/ComposeNavigationExt.kt#L13)
  
  ```kotlin
  
  @Serializable
  @Parcelize
  data class User(val name: String, val age: Int) : Parcelable
  
  ```

  #### 3.1 Update Destination to take Custom Parcelable as args

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

