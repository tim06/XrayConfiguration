package io.github.tim06.xrayConfiguration.inbounds.settings

import kotlinx.serialization.Serializable
import io.github.tim06.xrayConfiguration.routing.Network
import kotlinx.serialization.SerialName
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@Serializable
data class ShadowsocksInboundConfigurationObject(
    @SerialName("clients")
    val clients: List<Client>? = null,
    @SerialName("password")
    val password: String,
    @SerialName("method")
    val method: Method,
    @SerialName("level")
    val level: Int,
    @SerialName("email")
    val email: String? = null,
    @SerialName("network")
    val network: Network,
) : InboundConfigurationObject {

    @Serializable
    data class Client(
        @SerialName("password")
        val password: String,
        @SerialName("method")
        val method: Method,
        @SerialName("level")
        val level: Int,
        @SerialName("email")
        val email: String,
    )
}

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Serializable
enum class Method {
    @SerialName("2022-blake3-aes-128-gcm")
    `2022-BLAKE3-AES-128-GCM`,

    @SerialName("2022-blake3-aes-256-gcm")
    `2022-BLAKE3-AES-256-GCM`,

    @SerialName("2022-blake3-chacha20-poly1305")
    `2022-BLAKE3-CHACHA20-POLY1305`,

    @SerialName("aes-256-gcm")
    `AES-256-GCM`,

    @SerialName("aes-128-gcm")
    `AES-128-GCM`,

    @SerialName("chacha20-poly1305")
    `CHACHA20-POLY1305`,

    @SerialName("chacha20-ietf-poly1305")
    `CHACHA20-IETF-POLY1305`,

    @SerialName("xchacha20-poly1305")
    `XCHACHA20-POLY1305`,

    @SerialName("xchacha20-ietf-poly1305")
    `XCHACHA20-IETF-POLY1305`,

    @SerialName("none")
    NONE,

    @SerialName("plain")
    PLAIN;

    companion object {
        fun find(name: String): Method? {
            return entries.find { it.name.equals(other = name, ignoreCase = true) }
        }
    }
}
