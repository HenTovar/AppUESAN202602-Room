package dev.lchang.appuesan.presentation.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.lchang.appuesan.data.local.FavoriteCountry

@Composable
fun FavoritesScreen(
    favorites: List<FavoriteCountry>,
    loaded: Boolean,
    busy: Boolean,
    error: String?,
    onRemove: (String) -> Unit
) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis países favoritos", style = MaterialTheme.typography.headlineSmall)
        Text("Guardados en este dispositivo con Room y SQLite.")
        if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
        if (!loaded && error == null) Text("Cargando favoritos…")
        if (loaded && favorites.isEmpty()) {
            Text("Todavía no tenés favoritos. Agregá un país desde Home.")
        }
        LazyColumn {
            items(favorites, key = { it.name }) { country ->
                Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(country.name, style = MaterialTheme.typography.titleMedium)
                        Text("Ranking Fifa: ${country.ranking}")
                        TextButton(onClick = { onRemove(country.name) }, enabled = !busy) {
                            Text("Quitar de favoritos")
                        }
                    }
                }
            }
        }
    }
}
