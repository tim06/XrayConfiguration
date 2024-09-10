package io.github.tim06.xrayConfiguration.settings.grpc

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Grpc(
    @SerialName("serviceName")
    val serviceName: String,
    @SerialName("multiMode")
    val multiMode: Boolean,
    @SerialName("idle_timeout")
    val idleTimeout: Int,
    @SerialName("health_check_timeout")
    val healthCheckTimeout: Int,
    @SerialName("permit_without_stream")
    val permitWithoutStream: Boolean,
    @SerialName("initial_windows_size")
    val initialWindowsSize: Int,
)
