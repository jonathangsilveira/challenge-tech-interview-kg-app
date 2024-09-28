package edu.jgsilveira.challenge.kg.data.mapper

import edu.jgsilveira.challenge.kg.data.remote.model.RemoteActiveEvent
import edu.jgsilveira.challenge.kg.data.remote.model.RemoteSportEvent
import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent
import edu.jgsilveira.challenge.kg.domain.model.SportEvent
import io.mockk.spyk
import io.mockk.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class SportEventRemoteMapperTest {
    private val eventMapper = spyk<ActiveEventMapper>()

    private val remoteModel = RemoteSportEvent(
        i = "FOOT",
        d = "SOCCER",
        e = listOf(
            RemoteActiveEvent(
                i = "1",
                d = "home - away",
                sh = "",
                si = "FOOT",
                tt = 1
            )
        )
    )

    @Test
    fun `map Should return correct model`() {
        // Given
        val expected = SportEvent(
            id = "FOOT",
            name = "SOCCER",
            activeEvents = listOf(
                ActiveEvent(
                    id = "1",
                    homeCompetitor = "home",
                    awayCompetitor = "away",
                    sportId = "FOOT",
                    startsAt = 1000L
                )
            )
        )
        val mapper = SportEventsMapper(
            activeEventMapper = eventMapper
        )

        // When
        val actual = mapper.map(remoteModel)

        // Then
        assertEquals(expected, actual)
        verify(exactly = 1) {
            eventMapper.map(any())
        }
    }
}