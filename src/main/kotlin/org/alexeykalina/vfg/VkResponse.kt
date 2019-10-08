package org.alexeykalina.vfg

data class VkResponse(val items: List<User>?)

data class VkResponseWrapper(val response: VkResponse)