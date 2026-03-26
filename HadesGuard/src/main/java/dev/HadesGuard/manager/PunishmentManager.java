package dev.HadesGuard.manager;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.util.ColorUtil;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Date;

public class PunishmentManager
{
    private final HadesGuard plugin;

    public PunishmentManager(HadesGuard plugin)
    {
        this.plugin = plugin;
    }

    public void evaluate(Player player, PlayerData data, String check, int vl)
    {
        if (plugin.getACConfig().isBanEnabled() && vl >= plugin.getACConfig().getBanThreshold())
        {
            banPlayer(player, check);
            return;
        }

        if (plugin.getACConfig().isKickEnabled() && vl >= plugin.getACConfig().getKickThreshold())
        {
            kickPlayer(player, check);
        }
    }

    private void kickPlayer(Player player, String check)
    {
        String msg = ColorUtil.color(
            plugin.getACConfig().getKickMessage().replace("{check}", check)
        );
        Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer(msg));
    }

    private void banPlayer(Player player, String check)
    {
        String duration = plugin.getACConfig().getBanDuration();
        String msg = ColorUtil.color(
            plugin.getACConfig().getBanMessage()
                .replace("{check}", check)
                .replace("{duration}", duration)
        );

        Date expiry = null;
        if (!duration.equalsIgnoreCase("perm"))
        {
            expiry = new Date(System.currentTimeMillis() + parseDuration(duration));
        }

        final Date finalExpiry = expiry;
        Bukkit.getScheduler().runTask(plugin, () ->
        {
            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "HadesGuard " + check, finalExpiry, "HadesGuard");
            player.kickPlayer(msg);
        });
    }

    private long parseDuration(String input)
    {
        input = input.toLowerCase().trim();
        try
        {
            if (input.endsWith("d")) return Duration.ofDays(Long.parseLong(input.replace("d", ""))).toMillis();
            if (input.endsWith("h")) return Duration.ofHours(Long.parseLong(input.replace("h", ""))).toMillis();
            if (input.endsWith("m")) return Duration.ofMinutes(Long.parseLong(input.replace("m", ""))).toMillis();
        }
        catch (NumberFormatException ignored)
        {
        }
        return Duration.ofDays(7).toMillis();
    }
}
