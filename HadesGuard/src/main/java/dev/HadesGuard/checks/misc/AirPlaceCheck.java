package dev.HadesGuard.checks.misc;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class AirPlaceCheck extends AbstractCheck
{
    private static final BlockFace[] FACES =
    {
        BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
        BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };

    public AirPlaceCheck(HadesGuard plugin)
    {
        super(plugin, "airplace");
    }

    public void check(Player player, PlayerData data, BlockPlaceEvent event)
    {
        if (shouldSkip(player, data)) return;

        Block placed = event.getBlockPlaced();
        Block against = event.getBlockAgainst();

        if (against.getType() == Material.AIR || against.getType() == Material.CAVE_AIR)
        {
            flag(player, data, "placed against air");
            event.setCancelled(true);
            return;
        }

        boolean hasNeighbour = false;
        for (BlockFace face : FACES)
        {
            if (placed.getRelative(face).getType().isSolid())
            {
                hasNeighbour = true;
                break;
            }
        }

        if (!hasNeighbour)
        {
            flag(player, data, "no solid neighbour");
            event.setCancelled(true);
        }
    }
}
