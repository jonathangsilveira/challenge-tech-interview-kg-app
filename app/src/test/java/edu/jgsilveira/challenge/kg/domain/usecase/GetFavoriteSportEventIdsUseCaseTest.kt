package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.repository.FavoriteSportsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GetFavoriteSportEventIdsUseCaseTest {

    private val repository = mockk<FavoriteSportsRepository>(
        relaxed = true
    )

    private val useCase = GetFavoriteSportEventIdsUseCase(
        favoriteSportEventsRepository = repository,
        ioDispatcher = Dispatchers.Unconfined
    )

    @Test
    fun `invoke Should return favorite sport event ids`() = runTest {
        // Given
        val expected = listOf("1", "2")
        coEvery { repository.getFavoriteIds() } returns expected

        // When
        val actual = useCase()

        // Then
        assertEquals(expected, actual)
    }
}