# HadesGuard — Minecraft Anticheat Plugin

A comprehensive anticheat for **Paper 1.21.x** with Geyser/Floodgate (Bedrock) support.

## Checks Included

| Check | Category | Description |
|-------|----------|-------------|
| Speed | Movement | Detects moving faster than allowed |
| Flight | Movement | Detects sustained air time without cause |
| NoFall | Movement | Detects suppressing fall damage |
| Teleport | Movement | Detects impossible position jumps |
| Timer | Movement | Detects sending movement packets too fast |
| Reach | Combat | Detects hitting entities from too far away |
| KillAura | Combat | Detects CPS abuse, multi-target hits, angle spoofing |
| Scaffold | Misc | Detects bridging too fast at impossible angles |
| AirPlace | Misc | Detects placing blocks against air |
| Hunger/NoSlowdown | Misc | Detects moving at full speed while eating/blocking |
### More included that is not listed here
## Geyser / Floodgate Support

Install **Floodgate** on your server alongside Geyser. HadesGuard will automatically detect Bedrock players
and apply looser thresholds or full exemptions per-check (configurable in `config.yml`).

---

## How to Build

### Prerequisites
- **Java 21 JDK** — https://adoptium.net
- **Maven 3.8+** — https://maven.apache.org/download.cgi

### Steps

**1. Install Java 21**
Download from https://adoptium.net and follow the installer.
Verify: `java -version` should show `21.x.x`

**2. Install Maven**
Download the binary zip from https://maven.apache.org/download.cgi
Extract it, add the `bin/` folder to your system PATH.
Verify: `mvn -version`

**3. Build the plugin**
Open a terminal in the `HadesGuard/` folder (where `pom.xml` is) and run:

```bash
mvn clean package
```

Maven will automatically download the Paper API and Floodgate API from their repositories.
This requires an internet connection.

**4. Get your JAR**
After a successful build, find your JAR at:
```
HadesGuard/target/HadesGuard-1.0.0.jar
```

**5. Install on your server**
- Drop `HadesGuard-1.0.0.jar` into your server's `plugins/` folder
- Restart the server
- Edit `plugins/HadesGuard/config.yml` to tune thresholds
- Use `/HadesGuard reload` after making config changes

---

## Commands

| Command | Description |
|---------|-------------|
| `/HadesGuard reload` | Reload config without restart |
| `/HadesGuard info <player>` | View a player's violation levels |
| `/HadesGuard clearvl <player>` | Clear a player's violations |

## Permissions

| Permission | Description | Default |
|-----------|-------------|---------|
| `HadesGuard.admin` | Access all commands | OP |
| `HadesGuard.alerts` | Receive cheat alerts in chat | OP |
| `HadesGuard.bypass` | Bypass all checks | false |

---

## Tuning Tips

- **False positives on a specific check?** Increase the `buffer` value for that check in `config.yml`
- **Bedrock players getting flagged?** Set `bedrock-exempt: true` for the problematic check
- **Too many kicks?** Raise the `kick.threshold` under `punishments`
- Enable `debug: true` under `general` to see real-time flag data in console
