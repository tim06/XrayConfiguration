package io.github.tim06.xrayConfiguration.settings

import io.github.tim06.xrayConfiguration.settings.ds.DomainSocket
import io.github.tim06.xrayConfiguration.settings.grpc.Grpc
import io.github.tim06.xrayConfiguration.settings.http.Http
import io.github.tim06.xrayConfiguration.settings.httpUpgrade.HttpUpgrade
import io.github.tim06.xrayConfiguration.settings.kcp.Kcp
import io.github.tim06.xrayConfiguration.settings.quic.Quic
import io.github.tim06.xrayConfiguration.settings.reality.Reality
import io.github.tim06.xrayConfiguration.settings.tcp.Tcp
import io.github.tim06.xrayConfiguration.settings.tls.Tls
import io.github.tim06.xrayConfiguration.settings.ws.Ws
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamSettings(
    @SerialName("network")
    val network: Network? = null,
    @SerialName("security")
    val security: Security? = null,
    @SerialName("tlsSettings")
    val tlsSettings: Tls? = null,
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
    val dsSettings: DomainSocket? = null,
    @SerialName("grpcSettings")
    val grpcSettings: Grpc? = null,
    @SerialName("httpupgradeSettings")
    val httpupgradeSettings: HttpUpgrade? = null,
    @SerialName("realitySettings")
    val realitySettings: Reality? = null,
    @SerialName("sockopt")
    val sockopt: Sockopt? = null
)
