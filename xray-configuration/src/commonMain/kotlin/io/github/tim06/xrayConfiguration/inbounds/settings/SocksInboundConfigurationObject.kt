package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocksInboundConfigurationObject(
    @SerialName("auth")
    val auth: Auth,
    @SerialName("accounts")
    val accounts: List<Account>? = null,
    @SerialName("udp")
    val udp: Boolean,
    @SerialName("ip")
    val ip: String? = null,
    @SerialName("userLevel")
    val userLevel: Int? = null,
) : InboundConfigurationObject {

    @Serializable
    data class Account(
        @SerialName("user")
        val user: String,
        @SerialName("pass")
        val pass: String,
    )

    @Serializable
    enum class Auth {
        @SerialName("noauth")
        NOAUTH,

        @SerialName("password")
        PASSWORD,
    }
}
