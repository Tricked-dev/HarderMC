/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.utilities

import dev.tricked.hardermc.HarderMC
import org.bukkit.event.Listener
import java.util.logging.Logger
import kotlin.reflect.KProperty
import kotlin.properties.ReadWriteProperty

annotation class Name(val value: String)
annotation class Description(val value: String)
annotation class Unstable

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class ToolDecorator(
    val name: String,
    val description: String,
    val unstable: Boolean = false
)

class ConfigProperty<T>(private val plugin: HarderMC, private val configPrefix: String, private val defaultValue: T) :
    ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return plugin.config.get("$configPrefix.${property.name}", defaultValue) as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        plugin.config.set("$configPrefix.${property.name}", value)
    }
}

abstract class BaseTool(var plugin: HarderMC) : Listener {
    protected val configPrefix = "feature.${this::class.simpleName}"

    var enabled: Boolean by ConfigProperty(plugin, configPrefix, true)

    init {
        instances.add(this)
    }

    companion object {
        val instances = mutableListOf<BaseTool>()
    }

    val name: String
        get() {
            val decoratorAnnotation = this::class.annotations.find { it is ToolDecorator } as? ToolDecorator
            return decoratorAnnotation?.name ?: ""
        }

    val description: String
        get() {
            val decoratorAnnotation = this::class.annotations.find { it is ToolDecorator } as? ToolDecorator
            return decoratorAnnotation?.description ?: ""
        }

    val unstable: Boolean
        get() {
            val decoratorAnnotation = this::class.annotations.find { it is ToolDecorator } as? ToolDecorator
            return decoratorAnnotation?.unstable ?: false
        }


    fun getLog(): Logger {
        return plugin.logger
    }
}
