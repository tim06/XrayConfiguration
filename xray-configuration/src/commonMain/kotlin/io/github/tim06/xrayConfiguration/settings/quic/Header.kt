package io.github.tim06.xrayConfiguration.settings.quic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("type")
    val type: String,
)
