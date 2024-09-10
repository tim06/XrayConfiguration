package io.github.tim06.xrayConfiguration.settings.ds

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainSocket(
    @SerialName("path")
    val path: String,
    @SerialName("abstract")
    val abstract: Boolean,
    @SerialName("padding")
    val padding: Boolean,
)
