package io.github.tim06.xrayConfiguration.log

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Log(
    @SerialName("access")
    val access: String? = null,
    @SerialName("error")
    val error: String? = null,
    @SerialName("loglevel")
    val level: LogLevel? = null,
    @SerialName("dnsLog")
    val dnsLog: Boolean? = null,
)