package dev.tricked.hardermc.utilities

import dev.tricked.hardermc.HarderMC
import java.util.logging.Logger

abstract class BaseTool(public var plugin: HarderMC) {
    fun getLog(): Logger {
        return plugin.logger
    }
}
