package com.zyr.frequently_used_kotlin_ext.ext

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

class BundleBoolean {
    operator fun getValue(bundle: Bundle, property: KProperty<*>): Boolean =
        bundle.getBoolean(property.name, false)

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: Boolean) =
        bundle.putBoolean(property.name, value)
}

class BundleInt {
    companion object {
        const val DEFAULT = 89757
    }

    operator fun getValue(bundle: Bundle, property: KProperty<*>): Int {
        val result = bundle.getInt(property.name, DEFAULT)
        if (result == DEFAULT) {
            throw IllegalArgumentException(property.name + " is not set value[Int].")
        }
        return result
    }

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: Int) =
        bundle.putInt(property.name, value)
}

class BundleParcelable<T : Parcelable> {
    operator fun getValue(bundle: Bundle, property: KProperty<*>): T {
        return bundle.getParcelable(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[Parcelable].")
    }

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: T) =
        bundle.putParcelable(property.name, value)
}

class BundleParcelableArrayList<T : Parcelable> {
    operator fun getValue(bundle: Bundle, property: KProperty<*>): ArrayList<T> {
        return bundle.getParcelableArrayList<T>(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[Parcelable].")
    }

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: ArrayList<T>) =
        bundle.putParcelableArrayList(property.name, value)
}

class BundleString {
    operator fun getValue(bundle: Bundle, property: KProperty<*>): String {
        return bundle.getString(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[String].")
    }

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: String) =
        bundle.putString(property.name, value)
}

class BundleSerializable {
    operator fun getValue(bundle: Bundle, property: KProperty<*>): Serializable {
        return bundle.getSerializable(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[Serializable]")
    }

    operator fun setValue(bundle: Bundle, property: KProperty<*>, value: Serializable) {
        bundle.putSerializable(property.name, value)
    }
}
