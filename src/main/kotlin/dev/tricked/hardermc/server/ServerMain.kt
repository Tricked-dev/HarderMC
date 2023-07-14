/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.server.features.CustomSpawnLogic
import dev.tricked.hardermc.server.features.ServerMotD
import org.bukkit.Bukkit

/// This class implements stuff for the hardermc server that do not make mc harder but just helps making the server work
class ServerMain(plugin: HarderMC) {
    init {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(CustomSpawnLogic(plugin), plugin)
        pluginManager.registerEvents(ServerMotD(plugin), plugin)
    }
}