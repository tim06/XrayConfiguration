package io.github.tim06.xrayConfiguration.outbounds.settings

import io.github.tim06.xrayConfiguration.inbounds.settings.Method
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShadowsocksOutboundConfigurationObject(
    @SerialName("servers")
    val servers: List<Server>,
) : OutboundConfigurationObject {

    @Serializable
    data class Server(
        @SerialName("email")
        val email: String? = null,
        @SerialName("address")
        val address: String,
        @SerialName("port")
        val port: Int,
        @SerialName("method")
        val method: Method,
        @SerialName("password")
        val password: String,
        @SerialName("uot")
        val uot: Boolean? = null,
        @SerialName("level")
        val level: Int? = null,
    )
}
