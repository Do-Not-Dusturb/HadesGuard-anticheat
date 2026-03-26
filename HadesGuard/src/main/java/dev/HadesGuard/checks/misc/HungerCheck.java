package dev.HadesGuard.checks.misc;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HungerCheck extends AbstractCheck
{
    private static final double MAX_SLOWDOWN_SPEED = 0.22;

    public HungerCheck(HadesGuard plugin)
    {
        super(plugin, "hunger");
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

        boolean causesSlowdown = switch (item.getType())
        {
            case CROSSBOW, BOW, SHIELD -> true;
            default -> item.getType().isEdible();
        };

        if (!causesSlowdown) return;

        if (data.getLastSpeed() > MAX_SLOWDOWN_SPEED)
        {
            flag(player, data, "speed " + String.format("%.3f", data.getLastSpeed()) + " while using " + item.getType().name().toLowerCase());
        }
    }
}
