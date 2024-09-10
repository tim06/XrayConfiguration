package io.github.tim06.xrayConfiguration.settings.tls

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Certificate(
    @SerialName("ocspStapling")
    val ocspStapling: Int,
    @SerialName("oneTimeLoading")
    val oneTimeLoading: Boolean,
    @SerialName("usage")
    val usage: Usage,
    @SerialName("certificateFile")
    val certificateFile: String,
    @SerialName("keyFile")
    val keyFile: String,
    @SerialName("certificate")
    val certificate: List<String>,
    @SerialName("key")
    val key: List<String>,
)
