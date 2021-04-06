package tk.booky.vanillatempban.commands;
// Created by booky10 in VanillaTempBan (18:04 28.02.21)

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.GreedyStringArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tk.booky.vanillatempban.arguments.OfflinePlayerArgument;
import tk.booky.vanillatempban.arguments.RealTimeArgument;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TempBanCommand extends CommandAPICommand implements CommandExecutor {

    public TempBanCommand() {
        super("tempban");

        withPermission("minecraft.command.tempban");
        withArguments(new OfflinePlayerArgument("player"), new RealTimeArgument("time"), new GreedyStringArgument("reason"));

        executes(this);
    }

    @Override
    public void run(CommandSender sender, Object[] args) throws WrapperCommandSyntaxException {
        OfflinePlayer player = (OfflinePlayer) args[0];
        long time = (long) args[1];
        String reason = (String) args[2];
        String senderName = sender instanceof ConsoleCommandSender ? "Server" : sender.getName();

        if (time <= 0) {
            CommandAPI.fail("The time can't be negative or zero!");
        } else {
            long days = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS);
            boolean permitted = false;

            if (days < 1) {
                permitted = true;
            } else {
                for (int i = 1; i <= days; i++) {
                    if (!sender.hasPermission("minecraft.command.tempban." + i)) continue;
                    permitted = true;
                    break;
                }
            }

            if (permitted) {
                player.banPlayer(reason, new Date(System.currentTimeMillis() + time), senderName, false);
                if (player.isOnline() && player.getPlayer() != null) player.getPlayer().kickPlayer("You were banned from this server");

                sender.sendMessage("Banned " + player.getName() + ": " + reason);
                for (Player target : Bukkit.getOnlinePlayers()) {
                    if (!target.hasPermission("minecraft.admin.command_feedback")) continue;
                    if (sender instanceof Player && ((Player) sender).getUniqueId().equals(target.getUniqueId())) continue;
                    target.sendMessage("§7§o[" + senderName + ": Banned " + player.getName() + ": " + ChatColor.stripColor(reason) + "]");
                }
            } else {
                CommandAPI.fail("Dazu hast du keine Rechte!");
            }
        }
    }
}