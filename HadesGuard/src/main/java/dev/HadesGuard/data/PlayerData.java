package dev.HadesGuard.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerData
{
    private final UUID uuid;
    private boolean bedrock;

    private Location lastLocation;
    private Location lastGroundLocation;
    private Location lastSafeLocation;
    private int airTicks;
    private int groundTicks;
    private double lastDeltaY;
    private double lastSpeed;
    private boolean wasOnGround;

    private double fallDistance;

    private long lastAttackTime;
    private int hitsThisSecond;
    private long hitsSecondWindow;
    private long lastAttackTick;
    private UUID lastAttackTarget;

    private int blocksPlacedThisSecond;
    private long blockPlaceWindow;
    private Location lastPlaceLocation;

    private int movePacketsThisSecond;
    private long movePacketWindow;

    private final Map<String, Integer> violations = new HashMap<>();
    private final Map<String, Long> lastAlertTime = new HashMap<>();

    private boolean exempt;
    private int teleportExemptTicks;
    private long lastTeleportTime;

    private long lastVelocityTime;
    private double lastVelocityY;

    private boolean interactedWhileOffGround;
    private Location lastInteractLocation;
    private long lastInteractTime;

    public PlayerData(Player player)
    {
        uuid = player.getUniqueId();
        lastLocation = player.getLocation().clone();
        lastGroundLocation = player.getLocation().clone();
        lastSafeLocation = player.getLocation().clone();
    }

    public int getVL(String check)
    {
        return violations.getOrDefault(check, 0);
    }

    public int addVL(String check, int amount)
    {
        int newVL = violations.getOrDefault(check, 0) + amount;
        violations.put(check, newVL);
        return newVL;
    }

    public void decayVL(String check, int amount)
    {
        violations.put(check, Math.max(0, violations.getOrDefault(check, 0) - amount));
    }

    public void resetVL(String check)
    {
        violations.put(check, 0);
    }

    public Map<String, Integer> getAllViolations()
    {
        return violations;
    }

    public boolean canAlert(String check, long cooldownMs)
    {
        long last = lastAlertTime.getOrDefault(check, 0L);
        if (System.currentTimeMillis() - last >= cooldownMs)
        {
            lastAlertTime.put(check, System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public UUID getUuid() { return uuid; }

    public boolean isBedrock() { return bedrock; }
    public void setBedrock(boolean b) { bedrock = b; }

    public Location getLastLocation() { return lastLocation; }
    public void setLastLocation(Location l) { lastLocation = l; }

    public Location getLastGroundLocation() { return lastGroundLocation; }
    public void setLastGroundLocation(Location l) { lastGroundLocation = l; }

    public Location getLastSafeLocation() { return lastSafeLocation; }
    public void setLastSafeLocation(Location l) { lastSafeLocation = l; }

    public int getAirTicks() { return airTicks; }
    public void setAirTicks(int t) { airTicks = t; }
    public void incrementAirTicks() { airTicks++; }

    public int getGroundTicks() { return groundTicks; }
    public void setGroundTicks(int t) { groundTicks = t; }
    public void incrementGroundTicks() { groundTicks++; }

    public double getLastDeltaY() { return lastDeltaY; }
    public void setLastDeltaY(double d) { lastDeltaY = d; }

    public double getLastSpeed() { return lastSpeed; }
    public void setLastSpeed(double s) { lastSpeed = s; }

    public boolean wasOnGround() { return wasOnGround; }
    public void setWasOnGround(boolean b) { wasOnGround = b; }

    public double getFallDistance() { return fallDistance; }
    public void setFallDistance(double d) { fallDistance = d; }

    public long getLastAttackTime() { return lastAttackTime; }
    public void setLastAttackTime(long t) { lastAttackTime = t; }

    public int getHitsThisSecond() { return hitsThisSecond; }
    public void setHitsThisSecond(int h) { hitsThisSecond = h; }

    public long getHitsSecondWindow() { return hitsSecondWindow; }
    public void setHitsSecondWindow(long t) { hitsSecondWindow = t; }

    public long getLastAttackTick() { return lastAttackTick; }
    public void setLastAttackTick(long t) { lastAttackTick = t; }

    public UUID getLastAttackTarget() { return lastAttackTarget; }
    public void setLastAttackTarget(UUID id) { lastAttackTarget = id; }

    public int getBlocksPlacedThisSecond() { return blocksPlacedThisSecond; }
    public void setBlocksPlacedThisSecond(int b) { blocksPlacedThisSecond = b; }

    public long getBlockPlaceWindow() { return blockPlaceWindow; }
    public void setBlockPlaceWindow(long t) { blockPlaceWindow = t; }

    public Location getLastPlaceLocation() { return lastPlaceLocation; }
    public void setLastPlaceLocation(Location l) { lastPlaceLocation = l; }

    public int getMovePacketsThisSecond() { return movePacketsThisSecond; }
    public void setMovePacketsThisSecond(int p) { movePacketsThisSecond = p; }

    public long getMovePacketWindow() { return movePacketWindow; }
    public void setMovePacketWindow(long t) { movePacketWindow = t; }

    public boolean isExempt() { return exempt; }
    public void setExempt(boolean b) { exempt = b; }

    public int getTeleportExemptTicks() { return teleportExemptTicks; }
    public void setTeleportExemptTicks(int t) { teleportExemptTicks = t; }
    public void decrementTeleportExemptTicks() { if (teleportExemptTicks > 0) teleportExemptTicks--; }

    public long getLastTeleportTime() { return lastTeleportTime; }
    public void setLastTeleportTime(long t) { lastTeleportTime = t; }

    public long getLastVelocityTime() { return lastVelocityTime; }
    public void setLastVelocityTime(long t) { lastVelocityTime = t; }

    public double getLastVelocityY() { return lastVelocityY; }
    public void setLastVelocityY(double v) { lastVelocityY = v; }

    public boolean isInteractedWhileOffGround() { return interactedWhileOffGround; }
    public void setInteractedWhileOffGround(boolean b) { interactedWhileOffGround = b; }

    public Location getLastInteractLocation() { return lastInteractLocation; }
    public void setLastInteractLocation(Location l) { lastInteractLocation = l; }

    public long getLastInteractTime() { return lastInteractTime; }
    public void setLastInteractTime(long t) { lastInteractTime = t; }
}
