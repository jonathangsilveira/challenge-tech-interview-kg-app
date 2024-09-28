package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.repository.FavoriteSportsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class HandleFavoriteActiveSportEventChangesUseCaseTest {
    private val favoriteSportsRepository = mockk<FavoriteSportsRepository>()

    private val useCase = HandleFavoriteActiveSportEventChangesUseCase(
        favoriteSportEventsRepository = favoriteSportsRepository,
        ioDispatcher = Dispatchers.Unconfined
    )

    @Test
    fun `invoke Should add to favorite When isFavorite is true`() = runTest {
        // Given
        val sportId = "1"
        coEvery {
            favoriteSportsRepository.addToFavorite(sportId)
        } just runs

        // When
        useCase.invoke(sportId, true)

        // Then
        coVerify {
            favoriteSportsRepository.addToFavorite(sportId)
        }
    }

    @Test
    fun `invoke Should remove from favorite When isFavorite is false`() = runTest {
        // Given
        val sportId = "1"
        coEvery {
            favoriteSportsRepository.removeFromFavorite(sportId)
        } just runs

        // When
        useCase.invoke(sportId, false)

        // Then
        coVerify {
            favoriteSportsRepository.removeFromFavorite(sportId)
        }
    }

    @Test
    fun `invoke Should fail When an exception is thrown`() = runTest {
        // Given
        val sportId = "1"
        val expected = "test"
        coEvery {
            favoriteSportsRepository.removeFromFavorite(sportId)
        } throws Exception("test")

        // When
        val exception = assertFails {
            useCase.invoke(sportId, false)
        }

        // Then
        assertEquals(expected, exception.message)
        coVerify {
            favoriteSportsRepository.removeFromFavorite(sportId)
        }
    }
}