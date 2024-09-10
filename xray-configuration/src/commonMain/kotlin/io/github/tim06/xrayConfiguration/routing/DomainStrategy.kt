package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class DomainStrategy {
    @SerialName("AsIs")
    AsIs,

    @SerialName("IPIfNonMatch")
    IPIfNonMatch,

    @SerialName("IPOnDemand")
    IPOnDemand
}