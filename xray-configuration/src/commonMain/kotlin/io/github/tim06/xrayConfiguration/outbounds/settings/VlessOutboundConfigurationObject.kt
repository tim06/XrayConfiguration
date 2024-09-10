package io.github.tim06.xrayConfiguration.outbounds.settings

import io.github.tim06.xrayConfiguration.settings.Network
import io.github.tim06.xrayConfiguration.settings.Security
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VlessOutboundConfigurationObject(
    @SerialName("vnext")
    val vnext: List<Server>,
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
            @SerialName("id")
            val id: String,
            @SerialName("encryption")
            val encryption: Security = Security.NONE,
            @SerialName("flow")
            val flow: Flow,
            @SerialName("level")
            val level: Int,
        ) {

            @Serializable
            enum class Flow {
                @SerialName("")
                EMPTY,

                @SerialName("none")
                NONE,

                @SerialName("xtls-rprx-vision")
                XTLS_RPRX_VISION,

                @SerialName("xtls-rprx-vision-udp443")
                XTLS_RPRX_VISION_UDP443;

                companion object {
                    fun find(name: String): Flow? {
                        return Flow.entries.find { it.name.equals(other = name, ignoreCase = true) }
                    }
                }
            }
        }
    }
}
