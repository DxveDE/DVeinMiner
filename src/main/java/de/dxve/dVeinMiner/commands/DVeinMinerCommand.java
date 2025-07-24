package de.dxve.dVeinMiner.commands;

import de.dxve.dVeinMiner.DVeinMiner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        commandSender.sendMessage((DVeinMiner.getInstance().getConfig().getString("messages.no-permissions").replace("%prefix%", DVeinMiner.getInstance().getConfig().getString("prefix")).replace("&", "§")));
        return false;
    }
}
