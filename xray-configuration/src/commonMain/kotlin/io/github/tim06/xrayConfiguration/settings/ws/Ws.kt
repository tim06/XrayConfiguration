package io.github.tim06.xrayConfiguration.settings.ws

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ws(
    @SerialName("acceptProxyProtocol")
    val acceptProxyProtocol: Boolean? = null,
    @SerialName("path")
    val path: String,
    @SerialName("host")
    val host: String? = null,
    @SerialName("headers")
    val headers: Map<String, String>,
)
