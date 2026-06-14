package core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import core.security.SessionManager
import core.security.SessionState
import feature_auth.presentation.screen.LoginScreen
import feature_auth.presentation.viewmodel.LoginViewModel
import feature_feed.presentation.navigation.FeedNavigation
import feature_feed.presentation.screen.FeedScreen
import feature_feed.presentation.viewmodel.FeedViewModel
import feature_post_detail.presentation.navigation.PostDetailNavigation
import feature_post_detail.presentation.screen.PostDetailScreen
import feature_post_detail.presentation.viewmodel.PostDetailViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val sessionManager: SessionManager = koinInject()
    val sessionState by sessionManager.sessionState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { sessionManager.initialize() }

    LaunchedEffect(sessionState) {
        when (sessionState) {
            SessionState.Loading         -> Unit
            SessionState.Authenticated   -> navController.navigate(AppRoute.Feed) {
                popUpTo(0) { inclusive = true }
            }
            SessionState.Unauthenticated -> navController.navigate(AppRoute.Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = AppRoute.Loading) {

        composable<AppRoute.Loading> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        composable<AppRoute.Feed> {
            val feedViewModel: FeedViewModel = koinViewModel()
            val state by feedViewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                feedViewModel.navigation.collect { destination ->
                    when (destination) {
                        is FeedNavigation.ToPostDetail ->
                            navController.navigate(AppRoute.PostDetail(destination.postId))
                    }
                }
            }

            FeedScreen(state = state, onIntent = feedViewModel::dispatch)
        }

        composable<AppRoute.PostDetail> { backStackEntry ->
            val args: AppRoute.PostDetail = backStackEntry.toRoute()

            val postDetailViewModel: PostDetailViewModel = koinViewModel { parametersOf(args.postId) }
            val state by postDetailViewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                postDetailViewModel.navigation.collect { destination ->
                    when (destination) {
                        PostDetailNavigation.Back -> navController.popBackStack()
                    }
                }
            }

            PostDetailScreen(state = state, onIntent = postDetailViewModel::dispatch)
        }

        composable<AppRoute.Login> {
            val loginViewModel: LoginViewModel = koinViewModel()
            val state by loginViewModel.state.collectAsStateWithLifecycle()
            LoginScreen(state = state, onIntent = loginViewModel::dispatch)
        }
    }
}