package dev.lchang.appuesan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.lchang.appuesan.presentation.favorites.FavoritesScreen
import dev.lchang.appuesan.presentation.favorites.FavoritesViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lchang.appuesan.presentation.apifootball.ApiFootballScreen
import dev.lchang.appuesan.presentation.auth.LoginScreen
import dev.lchang.appuesan.presentation.auth.RegisterScreen
import dev.lchang.appuesan.presentation.home.HomeScreen
import dev.lchang.appuesan.presentation.permissions.GalleryPermissionsScreen
import dev.lchang.appuesan.presentation.realtime.FirestoreRealtimeScreen
import dev.lchang.appuesan.presentation.supabase.SupabaseStorageScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    val favoritesModel: FavoritesViewModel = viewModel()
    val favorites by favoritesModel.favorites.collectAsState()
    val loaded by favoritesModel.loaded.collectAsState()
    val busy by favoritesModel.busy.collectAsState()
    val error by favoritesModel.error.collectAsState()

    NavHost(navController = navController,
            startDestination = "login")
    {
        composable("register") { RegisterScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("home") {
            DrawerScaffold(navController) {
                HomeScreen(favorites, loaded, busy, error,
                    favoritesModel::save, favoritesModel::remove)
            }
        }
        composable("favorites") {
            DrawerScaffold(navController) {
                FavoritesScreen(favorites, loaded, busy, error, favoritesModel::remove)
            }
        }
        composable("permissions") {
            DrawerScaffold(navController) {
                GalleryPermissionsScreen()
            }
        }
        composable("realtime") {
            DrawerScaffold(navController) {
                FirestoreRealtimeScreen()
            }
        }
        composable("supabase") {
            DrawerScaffold(navController) {
                SupabaseStorageScreen()
            }
        }
        composable("apifootball") {
            DrawerScaffold(navController) {
                ApiFootballScreen()
            }
        }
    }
}