package dev.HadesGuard;

import dev.HadesGuard.command.HadesGuardCommand;
import dev.HadesGuard.config.ACConfig;
import dev.HadesGuard.data.PlayerDataManager;
import dev.HadesGuard.listener.CombatListener;
import dev.HadesGuard.listener.MovementListener;
import dev.HadesGuard.listener.PlayerListener;
import dev.HadesGuard.manager.CheckManager;
import dev.HadesGuard.manager.PunishmentManager;
import dev.HadesGuard.manager.ViolationManager;
import dev.HadesGuard.util.GeyserUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class HadesGuard extends JavaPlugin
{
    private static HadesGuard instance;

    private ACConfig acConfig;
    private PlayerDataManager playerDataManager;
    private ViolationManager violationManager;
    private PunishmentManager punishmentManager;
    private CheckManager checkManager;

    @Override
    public void onEnable()
    {
        instance = this;
        saveDefaultConfig();
        acConfig = new ACConfig(this);

        GeyserUtil.init(this);

        playerDataManager = new PlayerDataManager();
        violationManager = new ViolationManager(this);
        punishmentManager = new PunishmentManager(this);
        checkManager = new CheckManager(this);

        getServer().getPluginManager().registerEvents(new MovementListener(this), this);
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);

        HadesGuardCommand cmd = new HadesGuardCommand(this);
        getCommand("HadesGuard").setExecutor(cmd);
        getCommand("HadesGuard").setTabCompleter(cmd);

        getLogger().info("HadesGuard v" + getDescription().getVersion() + " enabled");

        if (GeyserUtil.isFloodgatePresent())
        {
            getLogger().info("floodgate detected bedrock support active");
        }
    }

    @Override
    public void onDisable()
    {
        getLogger().info("HadesGuard disabled");
    }

    public static HadesGuard getInstance()
    {
        return instance;
    }

    public ACConfig getACConfig()
    {
        return acConfig;
    }

    public PlayerDataManager getPlayerDataManager()
    {
        return playerDataManager;
    }

    public ViolationManager getViolationManager()
    {
        return violationManager;
    }

    public PunishmentManager getPunishmentManager()
    {
        return punishmentManager;
    }

    public CheckManager getCheckManager()
    {
        return checkManager;
    }
}
