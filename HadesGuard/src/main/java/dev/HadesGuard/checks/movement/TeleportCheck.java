package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportCheck extends AbstractCheck
{
    public TeleportCheck(HadesGuard plugin)
    {
        super(plugin, "teleport");
    }

    public void check(Player player, PlayerData data, Location from, Location to)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isFlying() || player.getAllowFlight()) return;
        if (data.getTeleportExemptTicks() > 0) return;

        double maxDist = plugin.getACConfig().getDouble("teleport", "max-distance-per-tick", 10.0);
        double distSq = from.distanceSquared(to);

        if (distSq > maxDist * maxDist)
        {
            flagAndLagback(player, data, "dist " + String.format("%.2f", Math.sqrt(distSq)) + " max " + maxDist);
        }
        else
        {
            decayVL(data);
        }
    }
}
