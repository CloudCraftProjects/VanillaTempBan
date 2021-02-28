package tk.booky.vanillatempban.commands;
// Created by booky10 in VanillaTempBan (18:04 28.02.21)

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.arguments.TimeArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import tk.booky.vanillatempban.arguments.OfflinePlayerArgument;

import java.util.Date;

public class TempBanCommand extends CommandAPICommand implements CommandExecutor {

    public TempBanCommand() {
        super("tempban");

        withPermission("minecraft.command.tempban");
        withArguments(new TimeArgument("time"), new OfflinePlayerArgument("player"), new GreedyStringArgument("reason"));

        executes(this);
    }

    @Override
    public void run(CommandSender sender, Object[] args) throws WrapperCommandSyntaxException {
        long time = System.currentTimeMillis() + ((int) args[0]) * 50;
        OfflinePlayer player = (OfflinePlayer) args[1];
        String reason = (String) args[2], senderName = sender instanceof ConsoleCommandSender ? "Server" : sender.getName();

        if (time <= System.currentTimeMillis()) {
            CommandAPI.fail("The time can't be negative or zero!");
        } else {
            player.banPlayer(reason, new Date(time), senderName, false);
            if (player.isOnline() && player.getPlayer() != null) player.getPlayer().kickPlayer("You were banned from this server");

            sender.sendMessage("Banned " + player.getName() + ": " + reason);
            Bukkit.broadcast("§o" + ChatColor.stripColor("[" + senderName + ": Banned " + player.getName() + ": " + reason + "]"), "minecraft.admin.command_feedback");
        }
    }
}