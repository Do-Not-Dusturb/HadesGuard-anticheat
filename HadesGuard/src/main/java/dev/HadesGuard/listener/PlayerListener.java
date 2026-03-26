package dev.HadesGuard.listener;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.util.GeyserUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{
    private final HadesGuard plugin;

    public PlayerListener(HadesGuard plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(player);

        data.setBedrock(GeyserUtil.isBedrockPlayer(player));
        data.setTeleportExemptTicks(20);

        if (plugin.getACConfig().isDebug())
        {
            plugin.getLogger().info("debug " + player.getName() + " joined bedrock " + data.isBedrock());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event)
    {
        plugin.getPlayerDataManager().remove(event.getPlayer().getUniqueId());
    }
}
