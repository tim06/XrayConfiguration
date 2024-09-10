package io.github.tim06.xrayConfiguration.fakedns

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FakeDns(
    @SerialName("ipPool")
    val ipPool: String,
    @SerialName("poolSize")
    val poolSize: Int,
)
