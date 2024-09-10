package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WireguardOutboundConfigurationObject(
    @SerialName("secretKey")
    val secretKey: String,
    @SerialName("address")
    val address: List<String>,
    @SerialName("peers")
    val peers: List<Peer>,
    @SerialName("mtu")
    val mtu: Int,
    @SerialName("reserved")
    val reserved: List<Int>,
    @SerialName("workers")
    val workers: Int,
    @SerialName("domainStrategy")
    val domainStrategy: DomainStrategy,
) : OutboundConfigurationObject {

    @Serializable
    data class Peer(
        @SerialName("endpoint")
        val endpoint: String,
        @SerialName("publicKey")
        val publicKey: String,
        @SerialName("preSharedKey")
        val preSharedKey: String,
        @SerialName("keepAlive")
        val keepAlive: Int,
        @SerialName("allowedIPs")
        val allowedIPs: List<String>,
    )

    @Serializable
    enum class DomainStrategy {
        @SerialName("ForceIPv6v4")
        ForceIPv6v4,

        @SerialName("ForceIPv6")
        ForceIPv6,

        @SerialName("ForceIPv4v6")
        ForceIPv4v6,

        @SerialName("ForceIPv4")
        ForceIPv4,

        @SerialName("ForceIP")
        ForceIP,
    }
}