package dev.HadesGuard.listener;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.manager.CheckManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MovementListener implements Listener
{
    private final HadesGuard plugin;
    private final CheckManager checks;

    public MovementListener(HadesGuard plugin)
    {
        this.plugin = plugin;
        checks = plugin.getCheckManager();
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(player);

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) return;

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) return;

        data.decrementTeleportExemptTicks();
        data.setLastDeltaY(to.getY() - from.getY());

        checks.getTimerCheck().check(player, data);
        checks.getTeleportCheck().check(player, data, from, to);
        checks.getSpeedCheck().check(player, data, from, to);
        checks.getFlightCheck().check(player, data, from, to);
        checks.getVelocityCheck().check(player, data, from, to);
        checks.getNoFallCheck().onMove(player, data);
        checks.getHungerCheck().check(player, data);
        checks.getNoSlowCheck().check(player, data);

        if (player.isOnGround())
        {
            data.setLastSafeLocation(to.clone());
            data.setLastGroundLocation(to.clone());
        }

        data.setLastLocation(to.clone());
        data.setWasOnGround(player.isOnGround());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event)
    {
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(event.getPlayer());
        data.setTeleportExemptTicks(10);
        data.setLastTeleportTime(System.currentTimeMillis());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onFallDamage(EntityDamageEvent event)
    {
        if (!(event.getEntity() instanceof Player player)) return;
        PlayerData data = plugin.getPlayerDataManager().get(player);
        if (data == null) return;

        checks.getNoFallCheck().check(player, data, event);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVelocity(PlayerVelocityEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().get(player);
        if (data == null) return;

        checks.getVelocityCheck().onVelocity(data, event.getVelocity().getY());
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerDataManager().getOrCreate(player);

        checks.getFreecamCheck().check(player, data, event);
    }
}
