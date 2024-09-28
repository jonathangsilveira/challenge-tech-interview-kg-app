package edu.jgsilveira.challenge.kg.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteActiveEvent(
    val d: String,
    val i: String,
    val sh: String,
    val si: String,
    val tt: Int
)