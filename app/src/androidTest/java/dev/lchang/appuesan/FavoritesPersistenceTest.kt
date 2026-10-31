package dev.lchang.appuesan

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.lchang.appuesan.data.local.AppDatabase
import dev.lchang.appuesan.data.local.FavoriteCountry
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesPersistenceTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val name = "favorites-test-${UUID.randomUUID()}.db"
    private lateinit var database: AppDatabase

    private fun openDatabase() = Room.databaseBuilder(
        context, AppDatabase::class.java, name
    ).build()

    @Before fun setUp() { database = openDatabase() }

    @After fun tearDown() {
        database.close()
        context.deleteDatabase(name)
    }

    @Test fun savedCountrySurvivesDatabaseReopening() = runBlocking {
        val peru = FavoriteCountry("Perú", 50, "https://flagcdn.com/w320/pe.png")
        database.favoriteCountryDao().save(peru)
        database.close()
        database = openDatabase()
        assertEquals(listOf(peru), database.favoriteCountryDao().observeAll().first())
    }

    @Test fun savingSameNameUpdatesInsteadOfDuplicating() = runBlocking {
        val dao = database.favoriteCountryDao()
        dao.save(FavoriteCountry("Perú", 50, "old.png"))
        dao.save(FavoriteCountry("Perú", 40, "new.png"))
        assertEquals(listOf(FavoriteCountry("Perú", 40, "new.png")), dao.observeAll().first())
    }

    @Test fun removingOneCountryKeepsTheOthersAfterReopening() = runBlocking {
        val dao = database.favoriteCountryDao()
        val brasil = FavoriteCountry("Brasil", 11, "br.png")
        dao.save(FavoriteCountry("Perú", 50, "pe.png"))
        dao.save(brasil)
        dao.remove("Perú")
        database.close()
        database = openDatabase()
        assertEquals(listOf(brasil), database.favoriteCountryDao().observeAll().first())
    }
}
