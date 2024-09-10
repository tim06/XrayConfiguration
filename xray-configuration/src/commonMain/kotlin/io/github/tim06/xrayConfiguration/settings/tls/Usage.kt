package io.github.tim06.xrayConfiguration.settings.tls

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Usage {
    @SerialName("encipherment")
    ENCIPHERMENT,

    @SerialName("verify")
    VERIFY,

    @SerialName("issue")
    ISSUE
}