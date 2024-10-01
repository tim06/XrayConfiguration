package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Strategy(
    @SerialName("type")
    val type: String = "leastLoad",
)
