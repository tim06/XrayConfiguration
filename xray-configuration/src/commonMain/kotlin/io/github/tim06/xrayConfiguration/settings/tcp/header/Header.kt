package io.github.tim06.xrayConfiguration.settings.tcp.header

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("type")
    val type: String,
    @SerialName("request")
    val request: Request? = null,
    @SerialName("response")
    val response: Response? = null,
)