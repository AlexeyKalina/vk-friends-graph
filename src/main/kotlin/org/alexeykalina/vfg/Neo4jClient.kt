package org.alexeykalina.vfg

import org.neo4j.driver.v1.AuthTokens
import org.neo4j.driver.v1.GraphDatabase

class Neo4jClient(url: String, username: String, password: String) {
    private val driver = GraphDatabase.driver(url, AuthTokens.basic(username, password))
    private lateinit var users: List<User>

    fun loadNodes(users: List<User>) {
        this.users = users
        driver.session().use {
            for (user in users) {
                val properties = buildProperties(user)
                it.writeTransaction {
                    it.run("CREATE (n:User $properties)")
                }
            }
        }
    }

    fun loadLinks(links: List<Link>) {
        driver.session().use {
            for (link in links) {
                if (users.any { it.id == link.second.id }) {
                    it.writeTransaction {
                        it.run("""
                            MATCH (a:User),(b:User)
                            WHERE a.id = ${link.first.id} AND b.id = ${link.second.id}
                            CREATE UNIQUE (a)-[r:F]-(b)"""
                        )
                    }
                }
            }
        }
    }

    private fun buildProperties(user: User): String {
        return """{
            id: ${user.id},
            name: '${user.name}',
            sex: ${user.sex},
            surname: '${user.surname}',
            university_id: ${user.university},
            university_name: '${user.universityName}',
            city_id: ${user.city?.id ?: -1},
            city_name: '${user.city?.title ?: "no_city"}',
            school_ids: [${user.schools?.joinToString { it.id.toString() } ?: ""}],
            school_names: [${user.schools?.joinToString { "'${it.name}'" } ?: ""}] }
        """.trimIndent()
    }
}