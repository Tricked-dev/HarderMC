/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.server.features.CustomSpawnLogic
import org.bukkit.Bukkit

/// This class implements stuff for the hardermc server that do not make mc harder but just helps making the server work
class ServerMain(private val plugin: HarderMC) {
    init {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(CustomSpawnLogic(plugin), plugin)
    }
}