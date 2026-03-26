package dev.HadesGuard.command;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.data.PlayerData;
import dev.HadesGuard.util.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HadesGuardCommand implements CommandExecutor, TabCompleter
{
    private final HadesGuard plugin;

    public HadesGuardCommand(HadesGuard plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        String prefix = plugin.getACConfig().getPrefix();

        if (!sender.hasPermission("HadesGuard.admin"))
        {
            sender.sendMessage(ColorUtil.color(prefix + "&cno permission"));
            return true;
        }

        if (args.length == 0)
        {
            sendHelp(sender, prefix);
            return true;
        }

        switch (args[0].toLowerCase())
        {
            case "reload" ->
            {
                plugin.getACConfig().reload();
                sender.sendMessage(ColorUtil.color(prefix + "&aconfig reloaded"));
            }

            case "info" ->
            {
                if (args.length < 2)
                {
                    sender.sendMessage(ColorUtil.color(prefix + "&cusage /HadesGuard info player"));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null)
                {
                    sender.sendMessage(ColorUtil.color(prefix + "&cplayer not found"));
                    return true;
                }

                PlayerData data = plugin.getPlayerDataManager().get(target);
                if (data == null)
                {
                    sender.sendMessage(ColorUtil.color(prefix + "&cno data for that player"));
                    return true;
                }

                sender.sendMessage(ColorUtil.color(prefix + "&e" + target.getName() + " &7bedrock &b" + data.isBedrock()));

                Map<String, Integer> vls = data.getAllViolations();
                if (vls.isEmpty())
                {
                    sender.sendMessage(ColorUtil.color("  &7no violations"));
                }
                else
                {
                    vls.forEach((check, vl) ->
                        sender.sendMessage(ColorUtil.color("  &8- &b" + check + " &7vl &c" + vl))
                    );
                }
            }

            case "clearvl" ->
            {
                if (args.length < 2)
                {
                    sender.sendMessage(ColorUtil.color(prefix + "&cusage /HadesGuard clearvl player"));
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null)
                {
                    sender.sendMessage(ColorUtil.color(prefix + "&cplayer not found"));
                    return true;
                }

                PlayerData data = plugin.getPlayerDataManager().get(target);
                if (data != null) data.getAllViolations().clear();

                sender.sendMessage(ColorUtil.color(prefix + "&acleared violations for &e" + target.getName()));
            }

            default -> sendHelp(sender, prefix);
        }

        return true;
    }

    private void sendHelp(CommandSender sender, String prefix)
    {
        sender.sendMessage(ColorUtil.color(prefix + "&bHadesGuard commands"));
        sender.sendMessage(ColorUtil.color("  &b/HadesGuard reload &7reload config"));
        sender.sendMessage(ColorUtil.color("  &b/HadesGuard info player &7view violations"));
        sender.sendMessage(ColorUtil.color("  &b/HadesGuard clearvl player &7clear violations"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if (args.length == 1)
        {
            return Arrays.asList("reload", "info", "clearvl");
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("clearvl")))
        {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }

        return List.of();
    }
}
