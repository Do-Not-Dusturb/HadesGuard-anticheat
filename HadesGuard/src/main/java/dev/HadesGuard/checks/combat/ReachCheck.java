package dev.HadesGuard.checks.combat;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ReachCheck extends AbstractCheck
{
    public ReachCheck(HadesGuard plugin)
    {
        super(plugin, "reach");
    }

    public void check(Player player, PlayerData data, Entity target)
    {
        if (shouldSkip(player, data)) return;
        if (!(target instanceof LivingEntity)) return;

        double maxReach = plugin.getACConfig().getDouble("reach", "max-reach", 3.2);
        double distance = player.getEyeLocation().distance(target.getLocation().add(0, 1, 0));

        if (distance > maxReach)
        {
            flag(player, data, "dist " + String.format("%.2f", distance) + " max " + maxReach);
        }
        else
        {
            decayVL(data);
        }
    }
}
