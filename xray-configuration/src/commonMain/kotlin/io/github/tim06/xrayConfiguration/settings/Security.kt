package io.github.tim06.xrayConfiguration.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Security {
    @SerialName("none")
    NONE,

    @SerialName("tls")
    TLS,

    @SerialName("reality")
    REALITY;

    companion object {
        fun find(name: String): Security? {
            return entries.find { it.name.equals(other = name, ignoreCase = true) }
        }
    }
}