package io.github.tim06.xrayConfiguration.inbounds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Strategy {
    @SerialName("always")
    ALWAYS,

    @SerialName("random")
    RANDOM
}