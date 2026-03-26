package dev.HadesGuard.checks.misc;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class FreecamCheck extends AbstractCheck
{
    public FreecamCheck(HadesGuard plugin)
    {
        super(plugin, "freecam");
    }

    public void check(Player player, PlayerData data, PlayerInteractEvent event)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (data.getTeleportExemptTicks() > 0) return;

        Location playerLoc = player.getLocation();
        Location interactLoc = event.getClickedBlock() != null
            ? event.getClickedBlock().getLocation()
            : null;

        if (interactLoc == null) return;

        double dist = playerLoc.distance(interactLoc);
        double maxInteractDist = 6.0;

        if (dist > maxInteractDist)
        {
            flag(player, data, "interacted at dist " + String.format("%.2f", dist));
            event.setCancelled(true);
            return;
        }

        if (!player.isOnGround() && data.getAirTicks() > 20)
        {
            Material blockType = event.getClickedBlock() != null
                ? event.getClickedBlock().getType()
                : Material.AIR;

            if (blockType != Material.AIR)
            {
                flag(player, data, "interacted while airborne ticks " + data.getAirTicks());
                event.setCancelled(true);
            }
        }
    }
}
