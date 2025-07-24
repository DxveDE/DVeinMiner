# 📦 DVeinMiner

A lightweight but powerful Minecraft plugin that brings the classic _VeinMining_ mechanic from mods into your server!

> Developed for **Minecraft 1.21.8** – fully configurable, permission-based, and survival-friendly!

---

## ✨ Features

- ✅ **VeinMining Tool**: Get a special pickaxe via `/dveinminer`
- 🌍 **Whitelist / Blacklist for Worlds**
- 🧍‍♂️ **Sneak Detection**: VeinMining only works while sneaking – optional!  
- 🛠️ **Fully configurable**:  
  - Messages  
  - World access lists  
  - Permissions  
- ⛏️ **Ore Linking**: Deepslate ores and regular ores are vein-mined together (e.g., Iron + Deepslate Iron)  
- 🔄 **Update Checker**: Informs operators when a new version is available (on server start and join)  

---

## 📥 Installation

1. Download the latest version from the [Releases page](https://github.com/DxveDE/DVeinMiner/releases)  
2. Drop the plugin `.jar` into your `/plugins` folder  
3. Start your server  
4. Edit the `config.yml` as needed  

**Tested with:**  
🧱 **Minecraft 1.21.8**  
🪵 Compatible with **Spigot** and **Paper**  

---

## ⚙️ Configuration example (insert in your README without code block)

<details>
  <summary>Click to view the default <code>config.yml</code></summary>

```
#
#  ██████████   █████   █████           ███             ██████   ██████  ███
# ░░███░░░░███ ░░███   ░░███           ░░░             ░░██████ ██████  ░░░
#  ░███   ░░███ ░███    ░███   ██████  ████  ████████   ░███░█████░███  ████  ████████    ██████  ████████
#  ░███    ░███ ░███    ░███  ███░░███░░███ ░░███░░███  ░███░░███ ░███ ░░███ ░░███░░███  ███░░███░░███░░███
#  ░███    ░███ ░░███   ███  ░███████  ░███  ░███ ░███  ░███ ░░░  ░███  ░███  ░███ ░███ ░███████  ░███ ░░░
#  ░███    ███   ░░░█████░   ░███░░░   ░███  ░███ ░███  ░███      ░███  ░███  ░███ ░███ ░███░░░   ░███
#  ██████████      ░░███     ░░██████  █████ ████ █████ █████     █████ █████ ████ █████░░██████  █████
# ░░░░░░░░░░        ░░░       ░░░░░░  ░░░░░ ░░░░ ░░░░░ ░░░░░     ░░░░░ ░░░░░ ░░░░ ░░░░░  ░░░░░░  ░░░░░
#

# DVeinMiner v1.0.0 by DxveDE
# https://discord.gg/dxve

settings:
    # Checks automatically for updates
    # If an update was found, you get a message on server join (you need operator to get the message)
    update-checker: true

    # Maximal amount of blocks that will be breaked when a player veinmine a block
    max-blockamount: 64

    # Ores are connected with deepslate variants
    deepslate-connection: true

    # Permission to use the '/dveinminer' command
    permission: "dveinminer.use"

    # Permission to use the veinminer tools
    permission-worlds:
        enabled: false
        permission: "dveinminer.use.%worldname%" # If name of world is "Spawn", the placeholder is "spawn" (lower case)

    # Only in this world list the veinminer tools can be used
    world-whitelist:
        enabled: false
        worlds: []

    # In this world list the veinminer tools are blocked
    world-blacklist:
        enabled: false
        worlds: []

    # If whitelist or blacklist is enabled, with this permission you can use the veinminer tool in all worlds
    bypass:
        enabled: false
        permission: "dveinminer.bypass"

    # Choose if player have to sneak on block break to veinmine
    sneak-needed: true


# The prefix for all messages (can be used with %prefix% in the messages, not for command-only-players)
prefix: "&4DVeinMiner &8┃&7"

messages:
    no-permissions: "%prefix% &cYou do not have sufficient permissions for this."
    command-only-players: "This command is only for players."
    no-material-found: "%prefix% &cThe specified material was not found. Try 'STONE_PICKAXE', 'IRON_PICKAXE', 'DIAMOND_PICKAXE', ..."
    get-item: "%prefix% &7You have received a §4DVeinMiner pickaxe§8."
    syntax: "%prefix% &cUsage of command: &7/dveinminer give <material>"
```
</details>

---

## 🧾 Commands

| Command       | Description                    | Permission       |  
|---------------|--------------------------------|------------------|  
| /dveinminer   | Gives you a VeinMiner pickaxe  | dveinminer.use   |  

---

## 🔐 Permissions

| Permission                  | Description                                      |  
|-----------------------------|------------------------------------------------|  
| dveinminer.use              | Allows access to the `/dveinminer` command      |  
| dveinminer.use.worldname    | Allows VeinMining in the specified world        |  
| dveinminer.bypass           | Bypasses whitelist and blacklist restrictions   |  

---

## 📸 Screenshots & Videos

> 📹 Screenshots and a short demo video will be available soon:  

- 🎥 [YouTube Demo](https://youtube.com/DEINVIDEO) (placeholder)  
- 🖼️ GIFs coming soon...  

---

## 🆘 Support & Feedback

Need help or want to report a bug?  

- 📧 Email: kontakt@dxve.de  
- 💬 Discord: https://discord.gg/dxve  
- 🐞 GitHub Issues: https://github.com/DxveDE/DVeinMiner/issues  

---

## 🔓 License

This project is licensed under the **MIT License**.  
You’re free to use, modify, and redistribute the code as long as you include credit to the original author.  

> ⚠️ Provided *as-is*, without warranty.  

See LICENSE file for details.  
