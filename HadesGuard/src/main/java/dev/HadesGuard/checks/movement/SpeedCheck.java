package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class SpeedCheck extends AbstractCheck
{
    public SpeedCheck(HadesGuard plugin)
    {
        super(plugin, "speed");
    }

    public void check(Player player, PlayerData data, Location from, Location to)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isFlying() || player.getAllowFlight()) return;
        if (data.getTeleportExemptTicks() > 0) return;

        double dx = to.getX() - from.getX();
        double dz = to.getZ() - from.getZ();
        double speed = Math.sqrt(dx * dx + dz * dz);

        double maxSpeed = 0.45;

        if (player.hasPotionEffect(PotionEffectType.SPEED))
        {
            int level = player.getPotionEffect(PotionEffectType.SPEED).getAmplifier() + 1;
            maxSpeed += level * 0.12;
        }

        double multiplier = data.isBedrock()
            ? plugin.getACConfig().getDouble("speed", "bedrock-multiplier", 1.6)
            : plugin.getACConfig().getDouble("speed", "max-speed-multiplier", 1.35);

        maxSpeed *= multiplier;

        if (speed > maxSpeed)
        {
            flagAndLagback(player, data, "spd " + String.format("%.2f", speed) + " max " + String.format("%.2f", maxSpeed));
        }
        else
        {
            decayVL(data);
        }

        data.setLastSpeed(speed);
    }
}
