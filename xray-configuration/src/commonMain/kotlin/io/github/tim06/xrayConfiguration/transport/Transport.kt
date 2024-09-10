package io.github.tim06.xrayConfiguration.transport

import io.github.tim06.xrayConfiguration.routing.DomainStrategy
import io.github.tim06.xrayConfiguration.settings.grpc.Grpc
import io.github.tim06.xrayConfiguration.settings.http.Http
import io.github.tim06.xrayConfiguration.settings.httpUpgrade.HttpUpgrade
import io.github.tim06.xrayConfiguration.settings.kcp.Kcp
import io.github.tim06.xrayConfiguration.settings.quic.Quic
import io.github.tim06.xrayConfiguration.settings.tcp.Tcp
import io.github.tim06.xrayConfiguration.settings.ws.Ws
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Transport(
    @SerialName("tcpSettings")
    val tcpSettings: Tcp? = null,
    @SerialName("kcpSettings")
    val kcpSettings: Kcp? = null,
    @SerialName("wsSettings")
    val wsSettings: Ws? = null,
    @SerialName("httpSettings")
    val httpSettings: Http? = null,
    @SerialName("quicSettings")
    val quicSettings: Quic? = null,
    @SerialName("dsSettings")
    val dsSettings: DomainStrategy? = null,
    @SerialName("grpcSettings")
    val grpcSettings: Grpc? = null,
    @SerialName("httpupgradeSettings")
    val httpupgradeSettings: HttpUpgrade? = null,
)
