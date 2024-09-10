package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HttpInboundConfigurationObject(
    @SerialName("timeout")
    val timeout: Int? = null,
    @SerialName("accounts")
    val accounts: List<Account>? = null,
    @SerialName("allowTransparent")
    val allowTransparent: Boolean? = null,
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
}
