package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DomainMatcher {
    @SerialName("hybrid")
    Hybrid,

    @SerialName("linear")
    Linear
}
