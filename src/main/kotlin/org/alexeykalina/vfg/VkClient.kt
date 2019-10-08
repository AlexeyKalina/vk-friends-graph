package org.alexeykalina.vfg

import com.google.gson.Gson
import java.net.URL

class VkClient(accessToken: String) {
    companion object {
        val gson = Gson()
    }

    private val properties = listOf("city", "sex", "education", "schools")
    private val friendsUrl = "https://api.vk.com/method/friends.get?fields=${properties.joinToString(",")}&v=5.52&access_token=$accessToken"

    fun getFriends(id: Int? = null): List<User> {
        val response =
            URL("$friendsUrl${if (id != null) "&user_id=$id" else ""}")
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        val vkResponse = gson.fromJson(response, VkResponseWrapper::class.java)

        return vkResponse.response?.items ?: emptyList()
    }
}