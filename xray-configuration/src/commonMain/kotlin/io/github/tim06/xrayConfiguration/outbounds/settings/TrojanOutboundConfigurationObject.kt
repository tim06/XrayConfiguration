package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrojanOutboundConfigurationObject(
    @SerialName("servers")
    val servers: List<Server>,
) : OutboundConfigurationObject {

    @Serializable
    data class Server(
        @SerialName("address")
        val address: String,
        @SerialName("port")
        val port: Int,
        @SerialName("password")
        val password: String,
        @SerialName("email")
        val email: String? = null,
        @SerialName("level")
        val level: Int? = null,
    )
}
