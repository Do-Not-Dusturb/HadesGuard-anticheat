package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallCheck extends AbstractCheck
{
    public NoFallCheck(HadesGuard plugin)
    {
        super(plugin, "nofall");
    }

    public void check(Player player, PlayerData data, EntityDamageEvent event)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;

        double minFall = plugin.getACConfig().getDouble("nofall", "min-fall-distance", 3.0);

        if (player.getFallDistance() < 0.1 && data.getFallDistance() > minFall)
        {
            flag(player, data, "clientfall " + String.format("%.2f", player.getFallDistance())
                + " serverfall " + String.format("%.2f", data.getFallDistance()));
        }
    }

    public void onMove(Player player, PlayerData data)
    {
        if (player.isOnGround())
        {
            data.setFallDistance(0);
        }
        else if (data.getLastDeltaY() < 0)
        {
            data.setFallDistance(data.getFallDistance() + Math.abs(data.getLastDeltaY()));
        }
    }
}
