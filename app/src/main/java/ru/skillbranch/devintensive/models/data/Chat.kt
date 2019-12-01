package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {

    fun unreadableMessageCount(): Int = messages.filter { !it.isReaded }.count()

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }

    fun lastMessageDate(): Date? =
        if (messages.isEmpty()) {
            null
        } else {
            messages.maxBy { it.date }!!.date
        }

    fun lastMessageShort(): Pair<String, String> {
        val lastMessage = messages.maxBy { it.date }
        return if (lastMessage != null) {
            when (lastMessage) {
                is TextMessage -> lastMessage.text.orEmpty() to lastMessage.from.firstName.orEmpty()
                else -> "${lastMessage.from.firstName} отправил фото" to lastMessage.from.firstName.orEmpty()
            }
        } else {
            "" to ""
        }
    }

    private fun isSingle(): Boolean = members.size == 1
}

enum class ChatType {
    SINGLE,
    GROUP,
    ARCHIVE
}
