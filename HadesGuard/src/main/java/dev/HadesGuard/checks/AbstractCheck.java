package dev.HadesGuard.checks;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.entity.Player;

public abstract class AbstractCheck
{
    protected final HadesGuard plugin;
    protected final String name;

    public AbstractCheck(HadesGuard plugin, String name)
    {
        this.plugin = plugin;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    protected boolean shouldSkip(Player player, PlayerData data)
    {
        if (!plugin.getACConfig().isCheckEnabled(name)) return true;
        if (player.hasPermission("HadesGuard.bypass")) return true;
        if (data.isExempt()) return true;
        if (data.isBedrock() && plugin.getACConfig().isBedrockExempt(name)) return true;
        return false;
    }

    protected void flag(Player player, PlayerData data, String detail)
    {
        plugin.getViolationManager().flag(player, data, name, detail, 1, false);
    }

    protected void flag(Player player, PlayerData data, String detail, int amount)
    {
        plugin.getViolationManager().flag(player, data, name, detail, amount, false);
    }

    protected void flagAndLagback(Player player, PlayerData data, String detail)
    {
        plugin.getViolationManager().flag(player, data, name, detail, 1, true);
    }

    protected void flagAndLagback(Player player, PlayerData data, String detail, int amount)
    {
        plugin.getViolationManager().flag(player, data, name, detail, amount, true);
    }

    protected void decayVL(PlayerData data)
    {
        data.decayVL(name, 1);
    }
}
