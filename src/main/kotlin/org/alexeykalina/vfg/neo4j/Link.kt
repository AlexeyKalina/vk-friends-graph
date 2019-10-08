package org.alexeykalina.vfg.neo4j

data class Link(val first: Node, val second: Node, val type: String = "Friend")