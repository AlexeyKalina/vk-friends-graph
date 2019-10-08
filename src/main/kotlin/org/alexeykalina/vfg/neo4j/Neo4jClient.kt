package org.alexeykalina.vfg.neo4j

import org.neo4j.driver.v1.AuthTokens
import org.neo4j.driver.v1.GraphDatabase

class Neo4jClient(url: String, username: String, password: String) {
    private val driver = GraphDatabase.driver(url, AuthTokens.basic(username, password))
    private lateinit var nodes: List<Node>

    fun loadNodes(nodes: List<Node>) {
        this.nodes = nodes
        driver.session().use {
            for (node in nodes) {
                it.writeTransaction {
                    it.run("CREATE (n:${node.type} ${node.properties})")
                }
            }
        }
    }

    fun loadLinks(links: List<Link>) {
        driver.session().use {
            for (link in links) {
                if (nodes.any { it.id == link.second.id }) {
                    it.writeTransaction {
                        it.run("""
                            MATCH (a:${link.first.type}),(b:${link.second.type})
                            WHERE a.id = ${link.first.id} AND b.id = ${link.second.id}
                            CREATE UNIQUE (a)-[r:${link.type}]-(b)"""
                        )
                    }
                }
            }
        }
    }
}