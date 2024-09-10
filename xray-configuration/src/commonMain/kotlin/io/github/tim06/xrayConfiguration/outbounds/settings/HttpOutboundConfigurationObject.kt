package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpOutboundConfigurationObject(
    @SerialName("servers")
    val servers: List<Server>,
) : OutboundConfigurationObject {

    @Serializable
    data class Server(
        @SerialName("address")
        val address: String,
        @SerialName("port")
        val port: Int,
        @SerialName("users")
        val users: List<User>,
    ) {

        @Serializable
        data class User(
            @SerialName("user")
            val user: String,
            @SerialName("pass")
            val pass: String,
        )
    }
}
