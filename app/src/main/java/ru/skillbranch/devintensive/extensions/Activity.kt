package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Activity.isKeyboardOpen(view: View): Boolean {
    val rect = Rect()
    view.getWindowVisibleDisplayFrame(rect)
    return rect.top != rect.bottom
}

fun Activity.isKeyboardClosed(view: View): Boolean {
    val rect = Rect()
    view.getWindowVisibleDisplayFrame(rect)
    return rect.top == rect.bottom
}
