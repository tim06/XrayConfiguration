package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VmessInboundConfigurationObject(
    @SerialName("clients")
    val clients: List<Client>,
    @SerialName("default")
    val default: Default,
    @SerialName("detour")
    val detour: Detour,
) : InboundConfigurationObject {

    @Serializable
    data class Client(
        @SerialName("id")
        val id: String,
        @SerialName("level")
        val level: Int,
        @SerialName("email")
        val email: String,
    )

    @Serializable
    data class Default(
        @SerialName("level")
        val level: Int,
    )

    @Serializable
    data class Detour(
        @SerialName("to")
        val to: String,
    )
}
