package io.github.tim06.xrayConfiguration.outbounds.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BlackholeOutboundConfigurationObject(
    @SerialName("response")
    val response: Response,
) : OutboundConfigurationObject {

    @Serializable
    data class Response(
        @SerialName("type")
        val type: Type,
    ) {

        @Serializable
        enum class Type {
            @SerialName("none")
            NONE,

            @SerialName("http")
            HTTP
        }
    }
}
