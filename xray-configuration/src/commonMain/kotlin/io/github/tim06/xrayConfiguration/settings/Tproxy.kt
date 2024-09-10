package io.github.tim06.xrayConfiguration.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Tproxy {
    @SerialName("redirect")
    REDIRECT,
    @SerialName("tproxy")
    TPROXY,
    @SerialName("off")
    OFF
}