package io.github.tim06.xrayConfiguration.settings.tls

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tls(
    @SerialName("serverName")
    val serverName: String? = null,
    @SerialName("rejectUnknownSni")
    val rejectUnknownSni: Boolean? = null,
    @SerialName("allowInsecure")
    val allowInsecure: Boolean? = null,
    @SerialName("alpn")
    val alpn: List<String>? = null,
    @SerialName("minVersion")
    val minVersion: String? = null,
    @SerialName("maxVersion")
    val maxVersion: String? = null,
    @SerialName("cipherSuites")
    val cipherSuites: String? = null,
    @SerialName("certificates")
    val certificates: List<Certificate>? = null,
    @SerialName("disableSystemRoot")
    val disableSystemRoot: Boolean? = null,
    @SerialName("enableSessionResumption")
    val enableSessionResumption: Boolean? = null,
    @SerialName("fingerprint")
    val fingerprint: String? = null,
    @SerialName("pinnedPeerCertificateChainSha256")
    val pinnedPeerCertificateChainSha256: List<String>? = null,
    @SerialName("masterKeyLog")
    val masterKeyLog: String? = null,
)