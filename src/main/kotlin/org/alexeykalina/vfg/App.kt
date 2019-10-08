package org.alexeykalina.vfg

import org.alexeykalina.vfg.neo4j.Link
import org.alexeykalina.vfg.neo4j.Neo4jClient
import org.alexeykalina.vfg.neo4j.Node
import org.alexeykalina.vfg.vk.VkClient

class App {
    fun start(vkClient: VkClient, neo4jClient: Neo4jClient) {
        val users = vkClient.getFriends()
        neo4jClient.loadNodes(users.map { Node(it.id, it.buildProperties()) })
        for (user in users) {
            val friends = vkClient.getFriends(user.id)
            neo4jClient.loadLinks(friends.map {
                Link(Node(user.id, user.buildProperties()), Node(it.id, it.buildProperties()))
            })
        }
    }
}

fun main(args: Array<String>) {
    if (args.size != 4) throw IllegalArgumentException("${args.size} arguments. Must be: 4")
    val accessToken = args[0]
    val url = args[1]
    val username = args[2]
    val password = args[3]
    App().start(VkClient(accessToken), Neo4jClient(url, username, password))
}
