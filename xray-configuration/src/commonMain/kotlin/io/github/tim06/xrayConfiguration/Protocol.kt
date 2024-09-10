package io.github.tim06.xrayConfiguration

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Protocol {
    @SerialName("blackhole")
    BLACKHOLE,

    @SerialName("dns")
    DNS,

    @SerialName("dokodemo-door")
    DOKODEMO,

    @SerialName("http")
    HTTP,

    @SerialName("shadowsocks")
    SHADOWSOCKS,

    @SerialName("ss")
    SS,

    @SerialName("socks")
    SOCKS,

    @SerialName("trojan")
    TROJAN,

    @SerialName("vmess")
    VMESS,

    @SerialName("vless")
    VLESS,

    @SerialName("ws")
    WS,

    @SerialName("freedom")
    FREEDOM;

    companion object {
        fun find(name: String): Protocol? {
            return entries.find { it.name.equals(other = name, ignoreCase = true) }
        }
    }
}