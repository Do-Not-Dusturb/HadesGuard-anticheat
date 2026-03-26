package dev.HadesGuard.data;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataManager
{
    private final Map<UUID, PlayerData> dataMap = new ConcurrentHashMap<>();

    public PlayerData getOrCreate(Player player)
    {
        return dataMap.computeIfAbsent(player.getUniqueId(), id -> new PlayerData(player));
    }

    public PlayerData get(Player player)
    {
        return dataMap.get(player.getUniqueId());
    }

    public PlayerData get(UUID uuid)
    {
        return dataMap.get(uuid);
    }

    public void remove(UUID uuid)
    {
        dataMap.remove(uuid);
    }

    public Collection<PlayerData> getAll()
    {
        return dataMap.values();
    }
}
