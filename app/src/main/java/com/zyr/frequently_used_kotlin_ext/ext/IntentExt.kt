package com.zyr.frequently_used_kotlin_ext.ext

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

class IntentExtraBoolean {
    operator fun getValue(intent: Intent, property: KProperty<*>): Boolean =
        intent.getBooleanExtra(property.name, false)

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Boolean) =
        intent.putExtra(property.name, value)
}

class IntentExtraInt {
    companion object {
        const val DEFAULT = 89757
    }

    operator fun getValue(intent: Intent, property: KProperty<*>): Int {
        val result = intent.getIntExtra(property.name, DEFAULT)
        if (result == DEFAULT) {
            throw IllegalArgumentException(property.name + " is not set value[Int].")
        }
        return result
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Int) =
        intent.putExtra(property.name, value)
}

class IntentExtraFloat {
    companion object {
        const val DEFAULT = -1f
    }

    operator fun getValue(intent: Intent, property: KProperty<*>): Float {
        val result = intent.getFloatExtra(property.name, DEFAULT)
        if (result == DEFAULT) {
            throw IllegalArgumentException(property.name + " is not set value[Int].")
        }
        return result
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Float) =
        intent.putExtra(property.name, value)
}

class IntentExtraParcelable<T : Parcelable> {
    operator fun getValue(intent: Intent, property: KProperty<*>): T {
        return intent.getParcelableExtra<T>(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[Parcelable].")
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: T) =
        intent.putExtra(property.name, value)
}

class IntentExtraParcelableArrayList<T : Parcelable> {
    operator fun getValue(intent: Intent, property: KProperty<*>): ArrayList<T> {
        return intent.getParcelableArrayListExtra<T>(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[Parcelable].")
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: ArrayList<T>) =
        intent.putExtra(property.name, value)
}

class IntentExtraSerializable {
    operator fun getValue(intent: Intent, property: KProperty<*>): Serializable? {
        return intent.getSerializableExtra(property.name)
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Serializable?) =
        intent.putExtra(property.name, value)
}

class IntentExtraString {
    operator fun getValue(intent: Intent, property: KProperty<*>): String {
        return intent.getStringExtra(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[String].")
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: String) =
        intent.putExtra(property.name, value)
}

class IntentExtraStringArrayList {
    operator fun getValue(intent: Intent, property: KProperty<*>): ArrayList<String> {
        return intent.getStringArrayListExtra(property.name)
            ?: throw IllegalArgumentException(property.name + " is not set value[StringArrayList].")
    }

    operator fun setValue(intent: Intent, property: KProperty<*>, value: ArrayList<String>) =
        intent.putStringArrayListExtra(property.name, value)
}
