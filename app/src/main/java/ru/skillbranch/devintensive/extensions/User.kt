package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.models.UserView
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

fun User.toUserView(): UserView {
    val status = when {
        lastVisit == null -> "Ещё ни разу не был"
        isOnline -> "online"
        else -> "Последний раз был ${lastVisit!!.humanizeDiff(Date())}"
    }

    return UserView(
        id,
        "$firstName $lastName",
        Utils.transliteration("$firstName $lastName"),
        avatar,
        status,
        Utils.toInitials(firstName, lastName)
    )
}
