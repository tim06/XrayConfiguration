package io.github.tim06.xrayConfiguration.settings.quic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quic(
    @SerialName("security")
    val security: Security,
    @SerialName("key")
    val key: String,
    @SerialName("header")
    val header: Header,
)
