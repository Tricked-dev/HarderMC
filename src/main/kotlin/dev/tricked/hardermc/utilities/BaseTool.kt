/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.utilities

import dev.tricked.hardermc.HarderMC
import org.bukkit.event.Listener
import java.util.logging.Logger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Retention(AnnotationRetention.RUNTIME)
annotation class Name(val value: String)

@Retention(AnnotationRetention.RUNTIME)
annotation class Description(val value: String)

class ConfigProperty<T>(private val plugin: HarderMC, private val configPrefix: String, private val defaultValue: T) :
    ReadWriteProperty<Any?, T> {


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        var value = plugin.config.get("$configPrefix.${property.name}")
        if (value == null) {
            setValue(thisRef, property, defaultValue)
            value = defaultValue
        }
        return value as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        plugin.config.set("$configPrefix.${property.name}", value)
    }
}

abstract class BaseTool(var plugin: HarderMC) : Listener {
    protected val configPrefix = "feature.${this::class.simpleName}"

    var enabled: Boolean by ConfigProperty(plugin, configPrefix, true)

    init {
        // I'm sorry
        instances.add(this)
    }

    companion object {
        val instances = mutableListOf<BaseTool>()
    }

    val name: String
        get() {
            val nameAnnotation = this::class.java.getAnnotation(Name::class.java)
            return nameAnnotation?.value ?: ""
        }

    val description: String
        get() {
            val nameAnnotation = this::class.java.getAnnotation(Description::class.java)
            return nameAnnotation?.value ?: ""
        }


    fun getLog(): Logger {
        return plugin.logger
    }
}
