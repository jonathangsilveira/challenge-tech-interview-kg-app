package edu.jgsilveira.challenge.kg.domain.usecase

import edu.jgsilveira.challenge.kg.domain.exception.ActiveSportEventsNotFoundException
import edu.jgsilveira.challenge.kg.domain.repository.SportEventsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertIs

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetActiveSportEventsUseCaseTest {
    private val sportEventsRepository = mockk<SportEventsRepository>()

    private val useCase = GetActiveSportEventsUseCase(
        sportEventsRepository = sportEventsRepository,
        ioDispatcher = UnconfinedTestDispatcher()
    )

    @Test
    fun `invoke Should return all active sport events When sport event id is null`() = runTest {
        // Given
        val expected = GetActiveSportEventsStub.activeSportEvents
        coEvery {
            sportEventsRepository.getActiveEvents()
        } returns expected

        // When
        val actual = useCase.invoke(null)

        // Then
        assertEquals(expected, actual)
        coVerify {
            sportEventsRepository.getActiveEvents()
        }
    }

    @Test
    fun `invoke Should return active events by sport When sport event id is not null`() = runTest {
        // Given
        val expected = GetActiveSportEventsStub.activeSportEvents
        val sportEventId = "foot"
        coEvery {
            sportEventsRepository.getActiveEventsBySport(sportEventId)
        } returns expected

        // When
        val actual = useCase.invoke(sportEventId)

        // Then
        assertEquals(expected, actual)
        coVerify {
            sportEventsRepository.getActiveEventsBySport(sportEventId)
        }
    }

    @Test
    fun `invoke Should throw ActiveSportEventsNotFoundException When there are no active events`() = runTest {
        // Given
        coEvery {
            sportEventsRepository.getActiveEvents()
        } returns emptyList()

        // When
        val exception = assertFails { useCase.invoke(null) }

        // Then
        assertIs<ActiveSportEventsNotFoundException>(exception)
        coVerify {
            sportEventsRepository.getActiveEvents()
        }
    }
}