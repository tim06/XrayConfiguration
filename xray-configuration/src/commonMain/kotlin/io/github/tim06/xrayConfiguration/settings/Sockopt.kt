package io.github.tim06.xrayConfiguration.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sockopt(
    @SerialName("mark")
    val mark: Int? = null,
    @SerialName("tcpFastOpen")
    val tcpFastOpen: Boolean? = null,
    @SerialName("tproxy")
    val tproxy: Tproxy? = null,
    @SerialName("domainStrategy")
    val domainStrategy: io.github.tim06.xrayConfiguration.settings.DomainStrategy? = null,
    @SerialName("dialerProxy")
    val dialerProxy: String? = null,
    @SerialName("tcpMaxSeg")
    val tcpMaxSeg: Int? = null,
    @SerialName("acceptProxyProtocol")
    val acceptProxyProtocol: Boolean? = null,
    @SerialName("tcpKeepAliveInterval")
    val tcpKeepAliveInterval: Int? = null,
    @SerialName("tcpKeepAliveIdle")
    val tcpKeepAliveIdle: Int? = null,
    @SerialName("tcpUserTimeout")
    val tcpUserTimeout: Int? = null,
    @SerialName("tcpcongestion")
    val tcpcongestion: String? = null,
    @SerialName("interface")
    val `interface`: String? = null,
    @SerialName("tcpMptcp")
    val tcpMptcp: Boolean? = null,
    @SerialName("tcpNoDelay")
    val tcpNoDelay: Boolean,
    @SerialName("v6only")
    val v6only: Boolean? = null,
    @SerialName("tcpWindowClamp")
    val tcpWindowClamp: Int? = null,
)
