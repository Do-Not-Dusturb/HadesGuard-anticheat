package dev.HadesGuard.checks.combat;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KillAuraCheck extends AbstractCheck
{
    public KillAuraCheck(HadesGuard plugin)
    {
        super(plugin, "killaura");
    }

    public void check(Player player, PlayerData data, Entity target)
    {
        if (shouldSkip(player, data)) return;

        long now = System.currentTimeMillis();
        long currentTick = now / 50;

        if (now - data.getHitsSecondWindow() > 1000)
        {
            data.setHitsSecondWindow(now);
            data.setHitsThisSecond(1);
        }
        else
        {
            int hits = data.getHitsThisSecond() + 1;
            data.setHitsThisSecond(hits);

            int maxCPS = plugin.getACConfig().getInt("killaura", "max-hits-per-second", 20);
            if (hits > maxCPS)
            {
                flag(player, data, "cps " + hits + " max " + maxCPS, 2);
            }
        }

        if (plugin.getACConfig().getBoolean("killaura", "multi-target-check", true))
        {
            if (currentTick == data.getLastAttackTick()
                && data.getLastAttackTarget() != null
                && !data.getLastAttackTarget().equals(target.getUniqueId()))
            {
                flag(player, data, "multi target same tick", 3);
            }
        }

        if (plugin.getACConfig().getBoolean("killaura", "yaw-check", true))
        {
            double yaw = Math.toRadians(player.getLocation().getYaw());
            double dx = target.getLocation().getX() - player.getLocation().getX();
            double dz = target.getLocation().getZ() - player.getLocation().getZ();
            double angleToTarget = Math.atan2(-dx, dz);
            double diff = Math.abs(angleDiff(angleToTarget, yaw));

            if (diff > Math.toRadians(120))
            {
                flag(player, data, "angle " + String.format("%.1f", Math.toDegrees(diff)) + " deg", 2);
            }
        }

        data.setLastAttackTick(currentTick);
        data.setLastAttackTarget(target.getUniqueId());
        data.setLastAttackTime(now);
    }

    private double angleDiff(double a, double b)
    {
        return (a - b + Math.PI * 3) % (Math.PI * 2) - Math.PI;
    }
}
