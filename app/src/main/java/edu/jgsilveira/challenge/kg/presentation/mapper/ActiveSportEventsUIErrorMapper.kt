package edu.jgsilveira.challenge.kg.presentation.mapper

import edu.jgsilveira.challenge.kg.domain.exception.ActiveSportEventsNotFoundException
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUIError
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUIError.GENERIC
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUIError.NETWORK_ERROR
import edu.jgsilveira.challenge.kg.presentation.activesports.ActiveSportEventsUIError.NO_DATA_FOUND
import retrofit2.HttpException

internal fun Throwable.toUIError(): ActiveSportEventsUIError {
    return when (this) {
        is HttpException -> NETWORK_ERROR
        is ActiveSportEventsNotFoundException -> NO_DATA_FOUND
        else -> GENERIC
    }
}