package org.alexeykalina.vfg.neo4j

data class Node(val id: Int, val properties: String, val type: String = "User")