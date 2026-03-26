package dev.HadesGuard.checks.movement;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.AbstractCheck;
import dev.HadesGuard.data.PlayerData;
import org.bukkit.entity.Player;

public class TimerCheck extends AbstractCheck
{
    private static final int MAX_PACKETS_PER_SECOND = 22;

    public TimerCheck(HadesGuard plugin)
    {
        super(plugin, "timer");
    }

    public void check(Player player, PlayerData data)
    {
        if (shouldSkip(player, data)) return;

        long now = System.currentTimeMillis();

        if (now - data.getMovePacketWindow() > 1000)
        {
            data.setMovePacketWindow(now);
            data.setMovePacketsThisSecond(1);
        }
        else
        {
            int count = data.getMovePacketsThisSecond() + 1;
            data.setMovePacketsThisSecond(count);

            if (count > MAX_PACKETS_PER_SECOND)
            {
                flag(player, data, "packets " + count + " per second");
            }
        }
    }
}
