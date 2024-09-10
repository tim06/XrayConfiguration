package io.github.tim06.xrayConfiguration.settings.tcp

import io.github.tim06.xrayConfiguration.settings.tcp.header.Header
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tcp(
    @SerialName("acceptProxyProtocol")
    val acceptProxyProtocol: Boolean? = null,
    @SerialName("header")
    val header: Header? = null,
)
