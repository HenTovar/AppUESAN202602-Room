package dev.lchang.appuesan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCountryDao {
    @Query("SELECT * FROM favorite_countries ORDER BY ranking ASC, name ASC")
    fun observeAll(): Flow<List<FavoriteCountry>>

    // El nombre es único: guardar de nuevo actualiza, no duplica el país.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(country: FavoriteCountry)

    @Query("DELETE FROM favorite_countries WHERE name = :name")
    suspend fun remove(name: String)
}
