package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DnsOutboundConfigurationObject(
    @SerialName("network")
    val network: Network? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("port")
    val port: Int? = null,
    @SerialName("nonIPQuery")
    val nonIPQuery: String? = null,
) : OutboundConfigurationObject {

    @Serializable
    enum class Network {
        @SerialName("tcp")
        TCP,

        @SerialName("udp")
        UDP
    }
}
