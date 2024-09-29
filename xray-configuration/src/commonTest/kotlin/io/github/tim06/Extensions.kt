package io.github.tim06

import io.github.tim06.xrayConfiguration.XrayConfiguration
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

fun ensureSingleOutbound(configuration: XrayConfiguration) {
    val outbounds = configuration.outbounds!!
    assertEquals(outbounds.count(), 1)
    assertEquals(outbounds.first().tag, "proxy")

    val routing = configuration.routing!!
    assertNull(routing.balancers)
    assertTrue(routing.rules!!.all { it.balancerTag == null})

    assertNull(configuration.burstObservatory)
}

fun ensureMultipleOutbounds(configuration: XrayConfiguration) {
    val outbounds = configuration.outbounds!!
    assertTrue(outbounds.count() > 1)
    assertTrue(outbounds.none { it.tag == "proxy" })
    assertTrue(outbounds.all { it.tag!!.startsWith("outbound-") })

    val routing = configuration.routing!!
    assertTrue(!routing.rules.isNullOrEmpty())
    assertTrue(routing.rules!!.any { it.balancerTag != null })
    assertTrue(!routing.balancers.isNullOrEmpty())
    assertEquals(routing.balancers!!.first().selector.count(), outbounds.count())

    assertNotNull(configuration.burstObservatory)
}