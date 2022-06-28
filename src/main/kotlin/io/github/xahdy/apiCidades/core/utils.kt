package io.github.xahdy.apiCidades.core

import java.util.*

fun formatar(str: String): String {
    return str.trim().split("\\s+".toRegex())
        .joinToString(" ")
//        deprecated
//        { it.capitalize() }
        {
            it.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else it.toString()
            }
        }
}