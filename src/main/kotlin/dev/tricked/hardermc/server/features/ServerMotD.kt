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
import org.bukkit.util.CachedServerIcon
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
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

    @Throws(IOException::class)
    fun inputStreamToBase64(inputStream: InputStream): String? {
        // Step 1: Read input stream and store in byte array
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(4096)
        var bytesRead: Int
        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
            outputStream.write(buffer, 0, bytesRead)
        }
        val data = outputStream.toByteArray()

        // Step 2: Encode byte array to Base64
        return Base64.getEncoder().encodeToString(data)
    }
}