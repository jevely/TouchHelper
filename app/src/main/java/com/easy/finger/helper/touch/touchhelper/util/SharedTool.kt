package com.easy.finger.helper.touch.touchhelper.util

import android.content.Context
import android.content.SharedPreferences
import com.easy.finger.helper.touch.touchhelper.TouchApplication.Companion.context


/**
 * 内部储存
 */
object SharedTool {

    val FIRST_START: String = "FIRST_START"

    val preferences: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        preferences = context.getSharedPreferences("tip", Context.MODE_PRIVATE)
        editor = preferences.edit()
    }

    fun setString(key: String, content: String) {
        editor.putString(key, content)
        editor.commit()
    }

    fun getString(key: String): String {
        return preferences.getString(key, null)
    }

    fun getString(key: String, default: String): String {
        return preferences.getString(key, default)
    }

    fun setInt(key: String, content: Int) {
        editor.putInt(key, content)
        editor.commit()
    }

    fun getInt(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun getInt(key: String, default: Int): Int {
        return preferences.getInt(key, default)
    }

    fun setBoolean(key: String, content: Boolean) {
        editor.putBoolean(key, content)
        editor.commit()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun getBoolean(key: String, default: Boolean): Boolean {
        return preferences.getBoolean(key, default)
    }

    fun setFloat(key: String, content: Float) {
        editor.putFloat(key, content)
        editor.commit()
    }

    fun getFloat(key: String): Float {
        return preferences.getFloat(key, 0f)
    }

    fun getFloat(key: String, default: Float): Float {
        return preferences.getFloat(key, default)
    }

    fun setLong(key: String, content: Long) {
        editor.putLong(key, content)
        editor.commit()
    }

    fun getLong(key: String): Long {
        return preferences.getLong(key, 0L)
    }

    fun getLong(key: String, default: Long): Long {
        return preferences.getLong(key, default)
    }
}