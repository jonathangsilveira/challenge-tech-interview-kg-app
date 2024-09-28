package edu.jgsilveira.challenge.kg.data.mapper

import edu.jgsilveira.challenge.kg.data.remote.model.RemoteActiveEvent
import edu.jgsilveira.challenge.kg.domain.model.ActiveEvent
import kotlin.test.Test
import kotlin.test.assertEquals

class ActiveEventMapperTest {

    @Test
    fun `map Should return correct model`() {
        // Given
        val expected = ActiveEvent(
            id = "1",
            homeCompetitor = "home",
            awayCompetitor = "away",
            sportId = "FOOT",
            startsAt = 1000L
        )
        val mapper = ActiveEventMapper()

        // When
        val actual = mapper.map(
            RemoteActiveEvent(
                i = "1",
                d = "home - away",
                sh = "",
                si = "FOOT",
                tt = 1
            )
        )

        // Then
        assertEquals(expected, actual)
    }
}