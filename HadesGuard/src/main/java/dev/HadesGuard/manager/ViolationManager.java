package dev.HadesGuard.manager;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ViolationManager
{
    private final HadesGuard plugin;
    private static final long ALERT_COOLDOWN_MS = 3000;

    public ViolationManager(HadesGuard plugin)
    {
        this.plugin = plugin;
    }

    public void flag(Player player, PlayerData data, String check, String detail, int vlAdd)
    {
        flag(player, data, check, detail, vlAdd, false);
    }

    public void flag(Player player, PlayerData data, String check, String detail, int vlAdd, boolean lagback)
    {
        if (data.isExempt()) return;
        if (player.hasPermission("HadesGuard.bypass")) return;

        int vl = data.addVL(check, vlAdd);

        if (lagback)
        {
            lagBack(player, data);
        }

        if (plugin.getACConfig().isAlertEnabled() && vl >= plugin.getACConfig().getAlertThreshold())
        {
            if (data.canAlert(check, ALERT_COOLDOWN_MS))
            {
                sendAlert(player, check, detail, vl);
            }
        }

        if (plugin.getACConfig().isDebug())
        {
            plugin.getLogger().info("debug " + player.getName() + " flagged " + check + " vl " + vl + " " + detail);
        }

        plugin.getPunishmentManager().evaluate(player, data, check, vl);
    }

    public void lagBack(Player player, PlayerData data)
    {
        if (data.getLastSafeLocation() == null) return;
        data.setTeleportExemptTicks(10);
        Bukkit.getScheduler().runTask(plugin, () ->
            player.teleport(data.getLastSafeLocation())
        );
    }

    private void sendAlert(Player flagged, String check, String detail, int vl)
    {
        String msg = ColorUtil.color(
            plugin.getACConfig().getPrefix()
            + "&e" + flagged.getName()
            + " &7failed &b" + check
            + " &7vl &c" + vl
            + " &8" + detail
        );

        for (Player online : Bukkit.getOnlinePlayers())
        {
            if (online.hasPermission("HadesGuard.alerts"))
            {
                online.sendMessage(msg);
            }
        }
    }
}
