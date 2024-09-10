package io.github.tim06.xrayConfiguration.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Api(
    @SerialName("tag")
    val tag: String,
    @SerialName("services")
    val services: List<String>,
)