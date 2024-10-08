package io.github.tim06.xrayConfiguration.outbounds.settings

import io.github.tim06.xrayConfiguration.settings.Security
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

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
            val encryption: Security,
            @SerialName("flow")
            val flow: Flow,
            @SerialName("level")
            val level: Int,
        ) {

            @OptIn(ExperimentalObjCRefinement::class)
            @HiddenFromObjC
            @Serializable
            enum class Flow {
                @SerialName("")
                EMPTY,

                @SerialName("none")
                NONE,

                @SerialName("xtls-rprx-vision")
                `XTLS-RPRX-VISION`,

                @SerialName("xtls-rprx-vision-udp443")
                `XTLS-RPRX-VISION-UDP443`;

                companion object {
                    fun find(name: String): Flow? {
                        return Flow.entries.find { it.name.equals(other = name, ignoreCase = true) }
                    }
                }
            }
        }
    }
}
