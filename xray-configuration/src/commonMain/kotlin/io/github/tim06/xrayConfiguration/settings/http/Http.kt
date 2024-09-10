package io.github.tim06.xrayConfiguration.settings.http

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Http(
    @SerialName("host")
    val host: List<String>,
    @SerialName("path")
    val path: String,
    @SerialName("read_idle_timeout")
    val readIdleTimeout: Int,
    @SerialName("health_check_timeout")
    val healthCheckTimeout: Int,
    @SerialName("method")
    val method: String,
    @SerialName("headers")
    val headers: Map<String, List<String>>,
)
