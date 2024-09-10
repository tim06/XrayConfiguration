package io.github.tim06.xrayConfiguration.reverse

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Portal(
    @SerialName("tag")
    val tag: String,
    @SerialName("domain")
    val domain: String,
)
