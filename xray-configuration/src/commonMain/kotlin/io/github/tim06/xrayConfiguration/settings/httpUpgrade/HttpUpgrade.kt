package io.github.tim06.xrayConfiguration.settings.httpUpgrade

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpUpgrade(
    @SerialName("acceptProxyProtocol")
    val acceptProxyProtocol: Boolean,
    @SerialName("path")
    val path: String,
    @SerialName("host")
    val host: String,
    @SerialName("headers")
    val headers: Map<String, String>,
)
