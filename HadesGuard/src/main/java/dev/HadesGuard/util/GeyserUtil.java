package dev.HadesGuard.util;

import dev.HadesGuard.HadesGuard;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GeyserUtil
{
    private static boolean floodgatePresent = false;
    private static Object floodgateApi = null;

    public static void init(HadesGuard plugin)
    {
        if (plugin.getServer().getPluginManager().getPlugin("floodgate") != null)
        {
            try
            {
                Class<?> apiClass = Class.forName("org.geysermc.floodgate.api.FloodgateApi");
                floodgateApi = apiClass.getMethod("getInstance").invoke(null);
                floodgatePresent = true;
                plugin.getLogger().info("floodgate api hooked");
            }
            catch (Exception e)
            {
                plugin.getLogger().warning("floodgate hook failed " + e.getMessage());
            }
        }
    }

    public static boolean isFloodgatePresent()
    {
        return floodgatePresent;
    }

    public static boolean isBedrockPlayer(Player player)
    {
        if (!floodgatePresent || floodgateApi == null)
        {
            return false;
        }
        try
        {
            return (boolean) floodgateApi.getClass()
                .getMethod("isFloodgatePlayer", UUID.class)
                .invoke(floodgateApi, player.getUniqueId());
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
