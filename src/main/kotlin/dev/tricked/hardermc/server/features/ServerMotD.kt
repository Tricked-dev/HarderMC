/*
 * Copyright (c) Tricked-dev 2023.
 */

package dev.tricked.hardermc.server.features

import dev.tricked.hardermc.HarderMC
import dev.tricked.hardermc.utilities.BaseTool
import dev.tricked.hardermc.utilities.ConfigProperty
import dev.tricked.hardermc.utilities.Description
import dev.tricked.hardermc.utilities.Name
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent
import java.util.*
import javax.imageio.ImageIO

@Name("Server MotD")
@Description("Server MOTD feature")
class ServerMotD(mc: HarderMC) : BaseTool(mc), Listener {
    private val random = Random()

    private var serverName: String by ConfigProperty(plugin, configPrefix, "HarderMC")
    private var maxPlayers: Int by ConfigProperty(plugin, configPrefix, 0)

    @EventHandler
    fun onServerListPing(event: ServerListPingEvent) {
        if (!enabled) return

        val colorChoices = listOf(NamedTextColor.RED, NamedTextColor.GREEN, NamedTextColor.BLUE, NamedTextColor.AQUA)
        val randomColor = colorChoices[random.nextInt(colorChoices.size)]

        val iconId = random.nextInt(36) + 1

        val icon = {}.javaClass.getResourceAsStream("/icons/$iconId.png")


        val image = ImageIO.read(icon)
        val serverIcon = Bukkit.loadServerIcon(image)
        event.setServerIcon(
            serverIcon
        )

        val motd = Component.text("")
            .append(
                Component.text(
                    "Welcome to ",
                    NamedTextColor.YELLOW
                )
            )
            .append(Component.text(serverName, randomColor))
            .append(Component.text("!"))
            .append(Component.newline())
            .append(
                Component.text("Some changes were made", NamedTextColor.RED)
                    .decoration(TextDecoration.BOLD, true)
            )

        event.motd(
            motd
        )
        event.maxPlayers = maxPlayers
    }
}