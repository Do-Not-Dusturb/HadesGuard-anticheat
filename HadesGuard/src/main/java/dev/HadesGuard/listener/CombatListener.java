package dev.HadesGuard.listener;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.manager.CheckManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener
{
    private final HadesGuard plugin;
    private final CheckManager checks;

    public CombatListener(HadesGuard plugin)
    {
        this.plugin = plugin;
        checks = plugin.getCheckManager();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player player)) return;
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(player);

        checks.getReachCheck().check(player, data, event.getEntity());
        checks.getKillAuraCheck().check(player, data, event.getEntity());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(player);

        checks.getScaffoldCheck().check(player, data, event);
        checks.getAirPlaceCheck().check(player, data, event);
    }
}
