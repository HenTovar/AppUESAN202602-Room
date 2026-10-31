package dev.lchang.appuesan.presentation.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.lchang.appuesan.data.local.AppDatabase
import dev.lchang.appuesan.data.local.FavoriteCountry
import dev.lchang.appuesan.data.model.CountryModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).favoriteCountryDao()
    private val _favorites = MutableStateFlow<List<FavoriteCountry>>(emptyList())
    val favorites: StateFlow<List<FavoriteCountry>> = _favorites.asStateFlow()
    private val _loaded = MutableStateFlow(false)
    val loaded: StateFlow<Boolean> = _loaded.asStateFlow()
    private val _busy = MutableStateFlow(false)
    val busy: StateFlow<Boolean> = _busy.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                dao.observeAll().collect {
                    _favorites.value = it
                    _loaded.value = true
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _error.value = "No se pudieron leer los favoritos. Reiniciá la aplicación."
            }
        }
    }

    fun save(country: CountryModel) = change {
        dao.save(FavoriteCountry(country.name, country.ranking, country.imageUrl))
    }

    fun remove(name: String) = change { dao.remove(name) }

    private fun change(action: suspend () -> Unit) {
        if (_busy.value || !_loaded.value) return
        _busy.value = true
        viewModelScope.launch {
            try {
                action()
                _error.value = null
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _error.value = "No se pudo guardar el cambio. Intentá de nuevo."
            } finally {
                _busy.value = false
            }
        }
    }
}
