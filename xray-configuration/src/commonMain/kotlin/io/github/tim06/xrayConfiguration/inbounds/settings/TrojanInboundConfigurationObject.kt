package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrojanInboundConfigurationObject(
    @SerialName("clients")
    val clients: List<Client>,
    @SerialName("fallbacks")
    val fallbacks: List<Fallback>,
) : InboundConfigurationObject {

    @Serializable
    data class Client(
        @SerialName("password")
        val password: String,
        @SerialName("email")
        val email: String,
        @SerialName("level")
        val level: Int,
    )
}

@Serializable
data class Fallback(
    @SerialName("dest")
    val dest: Int,
)