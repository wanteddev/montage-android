package com.wanted.android.montage.sample

import android.util.Log
import kotlin.reflect.full.memberProperties


internal fun <T : Any> T.toMap(): Map<String, Any?> {
    return this::class.memberProperties.associate { prop ->
        Log.d("_SMY", "memberProperties prop ${prop.name}")
        try {
            prop.name to prop.getter.call(this)
        } catch (e: Exception) {
            prop.name to "cannot access"
        }
    }
}

internal fun getStateList(
    map: Map<String, Any?>,
    includeKeyList: List<String> = emptyList()
): List<Map<String, Any?>> {
    val list = mutableListOf<Map<String, Any?>>()
    includeKeyList.map { key ->
        val value = map[key]
        val lastMap = list.lastOrNull() ?: map

        when (value) {
            is Boolean -> {
                var copy = lastMap.toMutableMap()
                copy.replace(key, false)
                list.add(copy)

                copy = lastMap.toMutableMap()
                copy.replace(key, true)
                list.add(copy)
            }

            is String -> {
                var copy = lastMap.toMutableMap()
                copy.replace(key, "")
                list.add(copy)

                copy = lastMap.toMutableMap()
                copy.replace(key, key)
                list.add(copy)
            }

            else -> Unit
        }

    }

    return list
}
//
//internal fun getStateList(
//    map: Map<String, Any?>,
//    notIncludeKeyList: List<String> = emptyList()
//): List<Map<String, Any?>> {
//    val list = mutableListOf<Map<String, Any?>>()
//    map.forEach {
//        if (!notIncludeKeyList.contains(it.key) || it.value == "cannot access") {
//            val lastMap = list.lastOrNull() ?: map
//            when (it.value) {
//                is Boolean -> {
//                    var copy = lastMap.toMutableMap()
//                    copy.replace(it.key, false)
//                    list.add(copy)
//
//                    copy = lastMap.toMutableMap()
//                    copy.replace(it.key, true)
//                    list.add(copy)
//                }
//
//                is String -> {
//                    var copy = lastMap.toMutableMap()
//                    copy.replace(it.key, "")
//                    list.add(copy)
//
//                    copy = lastMap.toMutableMap()
//                    copy.replace(it.key, it.key)
//                    list.add(copy)
//                }
//
//                else -> Unit
//            }
//        }
//    }
//
//    return list
//}
