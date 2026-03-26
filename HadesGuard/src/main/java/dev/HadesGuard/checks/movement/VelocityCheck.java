package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class VelocityCheck extends AbstractCheck
{
    public VelocityCheck(HadesGuard plugin)
    {
        super(plugin, "velocity");
    }

    public void check(Player player, PlayerData data, Location from, Location to)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (data.getTeleportExemptTicks() > 0) return;

        long now = System.currentTimeMillis();
        long timeSinceVelocity = now - data.getLastVelocityTime();

        if (timeSinceVelocity > 2000 || data.getLastVelocityTime() == 0) return;

        double expectedY = data.getLastVelocityY();
        double actualDeltaY = to.getY() - from.getY();

        if (expectedY > 0.1 && actualDeltaY < 0.01)
        {
            flagAndLagback(player, data, "ignored velocity expected dy " + String.format("%.2f", expectedY));
            data.setLastVelocityTime(0);
        }
    }

    public void onVelocity(PlayerData data, double velocityY)
    {
        if (velocityY > 0.1)
        {
            data.setLastVelocityTime(System.currentTimeMillis());
            data.setLastVelocityY(velocityY);
        }
    }
}
