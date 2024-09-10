package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VlessInboundConfigurationObject(
    @SerialName("clients")
    val clients: List<Client>,
    @SerialName("decryption")
    val decryption: String = "none",
    @SerialName("fallbacks")
    val fallbacks: List<Fallback>,
) : InboundConfigurationObject {

    @Serializable
    data class Client(
        @SerialName("id")
        val id: String,
        @SerialName("level")
        val level: Int,
        @SerialName("email")
        val email: String,
        @SerialName("flow")
        val flow: String,
    )
}
