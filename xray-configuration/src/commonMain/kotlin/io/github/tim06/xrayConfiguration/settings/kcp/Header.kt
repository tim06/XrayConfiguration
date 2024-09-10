package io.github.tim06.xrayConfiguration.settings.kcp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("type")
    val type: String,
)
