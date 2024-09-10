package io.github.tim06.xrayConfiguration.settings.tcp.header

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    @SerialName("version")
    val version: String,
    @SerialName("method")
    val method: String,
    @SerialName("path")
    val path: List<String>? = null,
    @SerialName("headers")
    val headers: Map<String, List<String>>
)