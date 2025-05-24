package ru.sobeninalex.common.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T: Parcelable> NavType.Companion.asType(): NavType<T> {
    return object : NavType<T>(false) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): T? {
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            } else {
                bundle.getParcelable(key, T::class.java)
            }
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(value)
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString<T>(value)
        }

        override val name: String
            get() = T::class.java.name
    }
}