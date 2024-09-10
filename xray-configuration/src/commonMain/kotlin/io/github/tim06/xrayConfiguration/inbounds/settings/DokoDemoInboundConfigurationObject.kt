package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import io.github.tim06.xrayConfiguration.routing.Network

@Serializable
data class DokoDemoInboundConfigurationObject(
    @SerialName("address")
    val address: String,
    @SerialName("port")
    val port: Int? = null,
    @SerialName("network")
    val network: Network? = null,
    @SerialName("timeout")
    val timeout: Int? = null,
    @SerialName("followRedirect")
    val followRedirect: Boolean? = null,
    @SerialName("userLevel")
    val userLevel: Int? = null,
) : InboundConfigurationObject