package io.github.tim06.xrayConfiguration.routing

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Routing(
    @SerialName("domainStrategy")
    val domainStrategy: DomainStrategy,
    @SerialName("domainMatcher")
    val domainMatcher: DomainMatcher? = null,
    @SerialName("rules")
    val rules: List<Rule>,
    @SerialName("balancers")
    val balancers: List<Balancer>? = null
)
