package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class FlightCheck extends AbstractCheck
{
    public FlightCheck(HadesGuard plugin)
    {
        super(plugin, "flight");
    }

    public void check(Player player, PlayerData data, Location from, Location to)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isFlying() || player.getAllowFlight()) return;
        if (player.isGliding()) return;
        if (player.hasPotionEffect(PotionEffectType.SLOW_FALLING)) return;
        if (player.isInsideVehicle()) return;
        if (data.getTeleportExemptTicks() > 0) return;

        if (player.isOnGround())
        {
            data.setAirTicks(0);
            data.incrementGroundTicks();
            decayVL(data);
            return;
        }

        double deltaY = to.getY() - from.getY();
        data.setLastDeltaY(deltaY);
        data.incrementAirTicks();
        data.setGroundTicks(0);

        int maxAirTicks = plugin.getACConfig().getInt("flight", "max-air-ticks", 12);

        if (data.getAirTicks() > maxAirTicks && deltaY >= -0.05)
        {
            flagAndLagback(player, data, "airticks " + data.getAirTicks() + " dy " + String.format("%.3f", deltaY));
        }
    }
}
