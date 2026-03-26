package dev.HadesGuard.manager;

import dev.HadesGuard.HadesGuard;
import dev.HadesGuard.checks.combat.KillAuraCheck;
import dev.HadesGuard.checks.combat.ReachCheck;
import dev.HadesGuard.checks.misc.AirPlaceCheck;
import dev.HadesGuard.checks.misc.FreecamCheck;
import dev.HadesGuard.checks.misc.HungerCheck;
import dev.HadesGuard.checks.misc.NoSlowCheck;
import dev.HadesGuard.checks.misc.ScaffoldCheck;
import dev.HadesGuard.checks.movement.FlightCheck;
import dev.HadesGuard.checks.movement.NoFallCheck;
import dev.HadesGuard.checks.movement.SpeedCheck;
import dev.HadesGuard.checks.movement.TeleportCheck;
import dev.HadesGuard.checks.movement.TimerCheck;
import dev.HadesGuard.checks.movement.VelocityCheck;

public class CheckManager
{
    private final SpeedCheck speedCheck;
    private final FlightCheck flightCheck;
    private final NoFallCheck noFallCheck;
    private final TeleportCheck teleportCheck;
    private final TimerCheck timerCheck;
    private final VelocityCheck velocityCheck;
    private final ReachCheck reachCheck;
    private final KillAuraCheck killAuraCheck;
    private final ScaffoldCheck scaffoldCheck;
    private final AirPlaceCheck airPlaceCheck;
    private final HungerCheck hungerCheck;
    private final NoSlowCheck noSlowCheck;
    private final FreecamCheck freecamCheck;

    public CheckManager(HadesGuard plugin)
    {
        speedCheck    = new SpeedCheck(plugin);
        flightCheck   = new FlightCheck(plugin);
        noFallCheck   = new NoFallCheck(plugin);
        teleportCheck = new TeleportCheck(plugin);
        timerCheck    = new TimerCheck(plugin);
        velocityCheck = new VelocityCheck(plugin);
        reachCheck    = new ReachCheck(plugin);
        killAuraCheck = new KillAuraCheck(plugin);
        scaffoldCheck = new ScaffoldCheck(plugin);
        airPlaceCheck = new AirPlaceCheck(plugin);
        hungerCheck   = new HungerCheck(plugin);
        noSlowCheck   = new NoSlowCheck(plugin);
        freecamCheck  = new FreecamCheck(plugin);
    }

    public SpeedCheck getSpeedCheck()       { return speedCheck; }
    public FlightCheck getFlightCheck()     { return flightCheck; }
    public NoFallCheck getNoFallCheck()     { return noFallCheck; }
    public TeleportCheck getTeleportCheck() { return teleportCheck; }
    public TimerCheck getTimerCheck()       { return timerCheck; }
    public VelocityCheck getVelocityCheck() { return velocityCheck; }
    public ReachCheck getReachCheck()       { return reachCheck; }
    public KillAuraCheck getKillAuraCheck() { return killAuraCheck; }
    public ScaffoldCheck getScaffoldCheck() { return scaffoldCheck; }
    public AirPlaceCheck getAirPlaceCheck() { return airPlaceCheck; }
    public HungerCheck getHungerCheck()     { return hungerCheck; }
    public NoSlowCheck getNoSlowCheck()     { return noSlowCheck; }
    public FreecamCheck getFreecamCheck()   { return freecamCheck; }
}
