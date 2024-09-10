package io.github.tim06.xrayConfiguration.outbounds.settings

import io.github.tim06.xrayConfiguration.inbounds.settings.Method
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VmessOutboundConfigurationObject(
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
            @SerialName("security")
            val security: Security,
            @SerialName("level")
            val level: Int? = null,
        )

        @Serializable
        enum class Security {
            @SerialName("none")
            NONE,

            @SerialName("auto")
            AUTO,

            @SerialName("zero")
            ZERO,

            @SerialName("aes-128-gcm")
            AES_128_GCM,

            @SerialName("chacha20-poly1305")
            CHACHA20_POLY1305;

            companion object {
                fun find(name: String): Security? {
                    return Security.entries.find { it.name.equals(other = name, ignoreCase = true) }
                }
            }
        }
    }
}
