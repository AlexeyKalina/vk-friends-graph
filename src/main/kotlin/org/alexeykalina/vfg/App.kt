package org.alexeykalina.vfg

class App {
    fun start(vkClient: VkClient, neo4jClient: Neo4jClient) {
        val users = vkClient.getFriends()
        neo4jClient.loadNodes(users)
        for (user in users) {
            val friends = vkClient.getFriends(user.id)
            neo4jClient.loadLinks(friends.map { Link(user, it) })
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
