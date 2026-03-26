package dev.HadesGuard.checks.misc;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;

public class ScaffoldCheck extends AbstractCheck
{
    public ScaffoldCheck(HadesGuard plugin)
    {
        super(plugin, "scaffold");
    }

    public void check(Player player, PlayerData data, BlockPlaceEvent event)
    {
        if (shouldSkip(player, data)) return;

        BlockFace face = event.getBlockAgainst().getFace(event.getBlockPlaced());
        if (face != BlockFace.UP) return;

        long now = System.currentTimeMillis();

        if (now - data.getBlockPlaceWindow() > 1000)
        {
            data.setBlockPlaceWindow(now);
            data.setBlocksPlacedThisSecond(1);
        }
        else
        {
            int placed = data.getBlocksPlacedThisSecond() + 1;
            data.setBlocksPlacedThisSecond(placed);

            int maxPlace = plugin.getACConfig().getInt("scaffold", "max-place-speed", 8);
            if (placed > maxPlace && data.getLastSpeed() > 0.1)
            {
                flag(player, data, "placed " + placed + " per second speed " + String.format("%.2f", data.getLastSpeed()));
            }
        }

        if (player.getLocation().getPitch() < -20)
        {
            flag(player, data, "pitch " + String.format("%.1f", player.getLocation().getPitch()) + " while bridging");
        }
    }
}
