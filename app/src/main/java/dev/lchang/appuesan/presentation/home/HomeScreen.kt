package dev.lchang.appuesan.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import dev.lchang.appuesan.data.local.FavoriteCountry
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import dev.lchang.appuesan.data.model.CountryModel


val mockCountries = listOf(
    CountryModel("Perú", 50,"https://flagcdn.com/w320/pe.png"),
    CountryModel("Brasil", 11,"https://flagcdn.com/w320/br.png"),
    CountryModel("España", 1,"https://flagcdn.com/w320/es.png"),
    CountryModel("Portugal", 7,"https://flagcdn.com/w320/pt.png"),
    CountryModel("Argentina", 4,"https://flagcdn.com/w320/ar.png"),
    CountryModel("Japón", 30,"https://flagcdn.com/w320/jp.png"),
    CountryModel("Francia", 5,"https://flagcdn.com/w320/fr.png"),
)
@Composable
fun HomeScreen(
    favorites: List<FavoriteCountry>,
    loaded: Boolean,
    busy: Boolean,
    error: String?,
    onSave: (CountryModel) -> Unit,
    onRemove: (String) -> Unit
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ranking Fifa 2026")
        if (error != null) Text(error, color = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn{
            items(mockCountries){ country ->
                Card(
                    modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 16.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Image(
                            contentDescription = country.name,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(country.imageUrl)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            Text(country.name)
                            Text("Ranking Fifa: " + country.ranking)
                            val saved = favorites.any { it.name == country.name }
                            TextButton(
                                enabled = loaded && !busy,
                                onClick = {
                                    if (saved) onRemove(country.name) else onSave(country)
                                }
                            ) {
                                Text(if (saved) "Quitar de favoritos" else "Guardar favorito")
                            }
                        }
                    }
                }
            }
        }
    }
}