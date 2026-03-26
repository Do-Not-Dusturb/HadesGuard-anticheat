package dev.HadesGuard.config;

import dev.HadesGuard.HadesGuard;
import org.bukkit.configuration.file.FileConfiguration;

public class ACConfig
{
    private final HadesGuard plugin;
    private FileConfiguration cfg;

    public ACConfig(HadesGuard plugin)
    {
        this.plugin = plugin;
        this.cfg = plugin.getConfig();
    }

    public void reload()
    {
        plugin.reloadConfig();
        cfg = plugin.getConfig();
    }

    public boolean isCheckEnabled(String check)
    {
        return cfg.getBoolean("checks." + check + ".enabled", true);
    }

    public int getBuffer(String check)
    {
        return cfg.getInt("checks." + check + ".buffer", 5);
    }

    public boolean isBedrockExempt(String check)
    {
        return cfg.getBoolean("checks." + check + ".bedrock-exempt", false);
    }

    public boolean isAlertEnabled()
    {
        return cfg.getBoolean("punishments.alert.enabled", true);
    }

    public int getAlertThreshold()
    {
        return cfg.getInt("punishments.alert.threshold", 5);
    }

    public boolean isKickEnabled()
    {
        return cfg.getBoolean("punishments.kick.enabled", true);
    }

    public int getKickThreshold()
    {
        return cfg.getInt("punishments.kick.threshold", 20);
    }

    public String getKickMessage()
    {
        return cfg.getString("punishments.kick.message", "&cyou have been kicked by HadesGuard\n&7reason &f{check}");
    }

    public boolean isBanEnabled()
    {
        return cfg.getBoolean("punishments.ban.enabled", true);
    }

    public int getBanThreshold()
    {
        return cfg.getInt("punishments.ban.threshold", 50);
    }

    public String getBanDuration()
    {
        return cfg.getString("punishments.ban.duration", "7d");
    }

    public String getBanMessage()
    {
        return cfg.getString("punishments.ban.message", "&cyou have been banned by HadesGuard\n&7reason &f{check}\n&7duration &f{duration}");
    }

    public String getPrefix()
    {
        return cfg.getString("general.prefix", "&8[&bHadesGuard&8] ");
    }

    public boolean isDebug()
    {
        return cfg.getBoolean("general.debug", false);
    }

    public double getDouble(String check, String key, double def)
    {
        return cfg.getDouble("checks." + check + "." + key, def);
    }

    public int getInt(String check, String key, int def)
    {
        return cfg.getInt("checks." + check + "." + key, def);
    }

    public boolean getBoolean(String check, String key, boolean def)
    {
        return cfg.getBoolean("checks." + check + "." + key, def);
    }
}
