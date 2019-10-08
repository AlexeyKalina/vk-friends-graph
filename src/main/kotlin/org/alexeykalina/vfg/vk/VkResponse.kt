package org.alexeykalina.vfg.vk

data class VkResponse(val items: List<User>?)

data class VkResponseWrapper(val response: VkResponse)