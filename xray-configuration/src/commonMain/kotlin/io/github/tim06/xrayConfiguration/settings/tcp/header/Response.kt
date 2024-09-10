package io.github.tim06.xrayConfiguration.settings.tcp.header

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    @SerialName("version")
    val version: String,
    @SerialName("status")
    val status: String,
    @SerialName("reason")
    val reason: String,
    @SerialName("headers")
    val headers: Map<String, List<String>>
)