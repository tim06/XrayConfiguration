package io.github.tim06.xrayConfiguration.settings.reality

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reality(
    @SerialName("show")
    val show: Boolean,
    @SerialName("dest")
    val dest: String? = null,
    @SerialName("xver")
    val xver: String? = null,
    @SerialName("serverNames")
    val serverNames: List<String>? = null,
    @SerialName("privateKey")
    val privateKey: String? = null,
    @SerialName("minClientVer")
    val minClientVer: String? = null,
    @SerialName("maxClientVer")
    val maxClientVer: String? = null,
    @SerialName("maxTimeDiff")
    val maxTimeDiff: Int? = null,
    @SerialName("shortIds")
    val shortIds: List<String>? = null,
    @SerialName("serverName")
    val serverName: String,
    @SerialName("fingerprint")
    val fingerprint: String,
    @SerialName("publicKey")
    val publicKey: String,
    @SerialName("shortId")
    val shortId: String,
    @SerialName("spiderX")
    val spiderX: String,
)
