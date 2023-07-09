package dev.tricked.hardermc.end;

import dev.tricked.hardermc.HarderMC;
import dev.tricked.hardermc.utilities.BaseTool;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class EndEventHandler extends BaseTool implements Listener  {
    public EndEventHandler(@NotNull HarderMC plugin) {
        super(plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null) return;
        //TODO make this work after restarts
        if (clickedBlock.getType() == Material.END_PORTAL_FRAME) {
            //end portal gets created after it is clicked
            Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                int radius = 4; // Radius of 3 blocks around the clicked block this should find any end portal blocks
                var portalsBlocks = new HashSet<Block>();
                var portalFrameBlocks = new HashSet<Block>();
                for (int xOffset = -radius; xOffset <= radius; xOffset++) {
                    for (int yOffset = -radius; yOffset <= radius; yOffset++) {
                        for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                            Block adjacentBlock = clickedBlock.getRelative(xOffset, yOffset, zOffset);
                            if (adjacentBlock.getType() == Material.END_PORTAL) {
                                getLog().info("Adjacent End Portal at offset (" + xOffset + ", " + yOffset + ", " + zOffset + ")");
                                portalsBlocks.add(adjacentBlock);
                            } else if (adjacentBlock.getType() == Material.END_PORTAL_FRAME) {
                                getLog().info("Adjacent End Portal Frame at offset (" + xOffset + ", " + yOffset + ", " + zOffset + ")");
                                portalFrameBlocks.add(adjacentBlock);
                            }
                        }
                    }
                }
                getLog().info("Found " + portalsBlocks.size() + " end portal blocks.");
                var world = event.getPlayer().getWorld();
                Bukkit.getScheduler().runTaskLater(getPlugin(), () -> {
                    if (!portalsBlocks.isEmpty()) {
                        double speed = 0;
                        int particleCount = 400;
                        double particleSizeX = 0.3;
                        double particleSizeY = 0.3;
                        double particleSizeZ = 0.3;

                        for (Block portalBlock : portalsBlocks) {
                            int centerX = portalBlock.getX();
                            int centerY = portalBlock.getY();
                            int centerZ = portalBlock.getZ();

                            getLog().info("Portal coordinates: (" + centerX + ", " + centerY + ", " + centerZ + ")");
                            var loc = new Location(world, centerX, centerY, centerZ);

                            world.spawnParticle(
                                    Particle.PORTAL,
                                    loc.getX() + 0.5, loc.getY() + 0.2, loc.getZ() + 0.5, // Adjust the coordinates to be at the center of the block
                                    particleCount,
                                    particleSizeX, particleSizeY, particleSizeZ,
                                    speed
                            );

                            portalBlock.setType(Material.AIR);
                        }
                        for (Block portalFrameBlock : portalFrameBlocks) {
                            int centerX = portalFrameBlock.getX();
                            int centerY = portalFrameBlock.getY();
                            int centerZ = portalFrameBlock.getZ();
                            getLog().info("Portal Frame coordinates: (" + centerX + ", " + centerY + ", " + centerZ + ")");
                            var loc = new Location(world, centerX, centerY, centerZ);
                            world.spawnParticle(
                                    Particle.SPELL_WITCH,
                                    loc.getX() + 0.5, loc.getY() + 0.2, loc.getZ() + 0.5,
                                    30,
                                    particleSizeX, particleSizeY, particleSizeZ,
                                    speed
                            );
                            EndPortalFrame endPortalFrame = (EndPortalFrame) portalFrameBlock.getBlockData();
                            endPortalFrame.setEye(false);
                            portalFrameBlock.setBlockData(endPortalFrame);
                            world.playSound(loc, Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
                        }
                    } else {
                        getLog().info("No adjacent end portals found.");
                    }
                    //10 minutes
                }, 10 * 60 * 20);
            }, 2); // Delay of 2 ticks (20ms per tick) - adjust this value as needed
        }
    }

}
