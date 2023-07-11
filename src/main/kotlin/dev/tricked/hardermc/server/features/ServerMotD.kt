/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.util.*


class ServerMotD(mc: HarderMC) : BaseTool(mc), Listener {
    private val random = Random()

    @EventHandler
    fun onServerListPing(event: ServerListPingEvent) {

        val colorChoices = listOf(NamedTextColor.RED, NamedTextColor.GREEN, NamedTextColor.BLUE, NamedTextColor.YELLOW)
        val randomColor = colorChoices[random.nextInt(colorChoices.size)]

        val motd = Component.text("")
            .append(
                Component.text(
                    "Welcome to ",
                    if (randomColor == NamedTextColor.YELLOW) NamedTextColor.RED else NamedTextColor.YELLOW
                )
            )
            .append(Component.text("HarderMC", randomColor))
            .append(Component.text("!"))
            .append(Component.newline())
            .append(
                Component.text("Some changes were made", NamedTextColor.RED)
                    .decoration(TextDecoration.BOLD, true)
            )

        event.motd(
            motd
        )
        event.maxPlayers = 0
    }
}