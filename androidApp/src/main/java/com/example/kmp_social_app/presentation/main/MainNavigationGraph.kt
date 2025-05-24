package com.example.kmp_social_app.presentation.main

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kmp_social_app.navigation.AuthGraph
import com.example.kmp_social_app.navigation.LocalNavController
import com.example.kmp_social_app.navigation.MainGraph
import com.example.kmp_social_app.navigation.asType
import ru.sobeninalex.account.presentation.edit.EditProfileScreen
import ru.sobeninalex.account.presentation.follows.FollowsScreen
import ru.sobeninalex.account.presentation.profile.ProfileScreen
import ru.sobeninalex.authorization.presentation.login.LoginScreen
import ru.sobeninalex.authorization.presentation.signup.SignUpScreen
import ru.sobeninalex.home.presentation.HomeScreen
import com.example.kmp_social_app.presentation.post_detail.PostDetailScreen
import com.example.kmp_social_app.common.utils.event.ObserveAsEvent
import ru.sobeninalex.utils.event.SnackbarEvent
import ru.sobeninalex.utils.event.UnauthorizedEvent
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationGraph(
    uiState: MainUiState.Success
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val startDestination: Any = if (uiState.currentUser.token.isEmpty()) AuthGraph else MainGraph

    ObserveAsEvent(
        flow = SnackbarEvent.event,
        key1 = snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.buttonName,
                duration = SnackbarDuration.Long,
                withDismissAction = true
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    ObserveAsEvent(
        flow = UnauthorizedEvent.event
    ) {
        navController.navigate(AuthGraph) {
            popUpTo(MainGraph) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            ) {
                Snackbar(
                    snackbarData = it,
                    shape = MaterialTheme.shapes.medium,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    dismissActionContentColor = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                navigation<AuthGraph>(
                    startDestination = AuthGraph.LoginRoute,
                ) {
                    composable<AuthGraph.SignUpRoute> {
                        SignUpScreen()
                    }

                    composable<AuthGraph.LoginRoute> {
                        LoginScreen()
                    }
                }

                navigation<MainGraph>(
                    startDestination = MainGraph.HomeRoute,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(250)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            tween(250)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(250)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(250)
                        )
                    }
                ) {
                    composable<MainGraph.HomeRoute> {
                        HomeScreen()
                    }

                    composable<MainGraph.PostDetailRoute> {
                        val args = it.toRoute<MainGraph.PostDetailRoute>()
                        PostDetailScreen(postId = args.postId)
                    }

                    composable<MainGraph.ProfileRoute> {
                        val args = it.toRoute<MainGraph.ProfileRoute>()
                        ru.sobeninalex.account.presentation.profile.ProfileScreen(userId = args.userId)
                    }

                    composable<MainGraph.EditProfileRoute>(
                        typeMap = mapOf(typeOf<ru.sobeninalex.utils.navigation.args.EditProfileArgs>() to NavType.asType<ru.sobeninalex.utils.navigation.args.EditProfileArgs>())
                    ) {
                        val route = it.toRoute<MainGraph.EditProfileRoute>()
                        ru.sobeninalex.account.presentation.edit.EditProfileScreen(route.args)
                    }

                    composable<MainGraph.FollowsRoute>(
                        typeMap = mapOf(typeOf<ru.sobeninalex.utils.navigation.args.FollowsArgs>() to NavType.asType<ru.sobeninalex.utils.navigation.args.FollowsArgs>())
                    ) {
                        val route = it.toRoute<MainGraph.FollowsRoute>()
                        ru.sobeninalex.account.presentation.follows.FollowsScreen(route.args)
                    }
                }
            }
        }
    }
}