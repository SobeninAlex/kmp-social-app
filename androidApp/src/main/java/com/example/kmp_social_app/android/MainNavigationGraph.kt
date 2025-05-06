package com.example.kmp_social_app.android

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kmp_social_app.android.common.navigation.AuthGraph
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.navigation.MainGraph
import com.example.kmp_social_app.android.presentation.account.profile.ProfileScreen
import com.example.kmp_social_app.android.presentation.auth.login.LoginScreen
import com.example.kmp_social_app.android.presentation.auth.signup.SignUpScreen
import com.example.kmp_social_app.android.presentation.home.HomeScreen
import com.example.kmp_social_app.android.presentation.post_detail.PostDetailScreen
import com.example.kmp_social_app.android.utils.ObserveAsEvent
import com.example.kmp_social_app.android.utils.SnackbarController
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainNavigationGraph(
    token: String?
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val startDestination: Any = if (token.isNullOrEmpty()) AuthGraph else MainGraph

    ObserveAsEvent(
        flow = SnackbarController.event,
        key1 = snackbarHostState
    ) { event ->
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.snackbarAction?.buttonName,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.snackbarAction?.action?.invoke()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                        ProfileScreen(userId = args.userId)
                    }
                }
            }
        }
    }
}