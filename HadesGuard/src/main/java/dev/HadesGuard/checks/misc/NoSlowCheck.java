package dev.HadesGuard.checks.misc;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NoSlowCheck extends AbstractCheck
{
    private static final double MAX_USING_SPEED = 0.18;

    public NoSlowCheck(HadesGuard plugin)
    {
        super(plugin, "noslow");
    }

    public void check(Player player, PlayerData data)
    {
        if (shouldSkip(player, data)) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;

        if (!player.isHandRaised())
        {
            decayVL(data);
            return;
        }

        ItemStack item = player.getActiveItem();
        if (item == null || item.getType().isAir()) return;

        boolean slowsPlayer = switch (item.getType())
        {
            case BOW, CROSSBOW, SHIELD, TRIDENT -> true;
            default -> item.getType().isEdible();
        };

        if (!slowsPlayer) return;

        if (data.getLastSpeed() > MAX_USING_SPEED)
        {
            flagAndLagback(player, data, "speed " + String.format("%.3f", data.getLastSpeed()) + " while using " + item.getType().name().toLowerCase());
        }
    }
}
