package tk.booky.vanillatempban.listener;
// Created by booky10 in VanillaTempBan (19:56 28.02.21)

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BannedLoginListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        if (!event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED) || !event.getPlayer().isBanned()) return;

        BanEntry entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(event.getPlayer().getUniqueId().toString());
        if (entry == null) entry = Bukkit.getBanList(BanList.Type.NAME).getBanEntry(event.getPlayer().getName());
        if (entry == null) return;

        StringBuilder remainingText = new StringBuilder();
        if (entry.getExpiration() != null) {
            long remaining = entry.getExpiration().getTime() - entry.getCreated().getTime();

            long days = 0;
            while (remaining > TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
                ++days;
                remaining -= TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
            }

            long hours = 0;
            while (remaining > TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)) {
                ++hours;
                remaining -= TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
            }

            long minutes = 0;
            while (remaining > TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)) {
                ++minutes;
                remaining -= TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
            }

            long seconds = 0;
            while (remaining > TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS)) {
                ++seconds;
                remaining -= TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
            }

            if (days > 0) remainingText.append(days).append("d");
            if (hours > 0) remainingText.append(hours).append("h");
            if (minutes > 0) remainingText.append(minutes).append("min");
            if (seconds > 0) remainingText.append(seconds).append("s");
        }

        event.setKickMessage("§cDu bist vom Server gebannt!\n" +
                "§cModerator: §f" + entry.getSource() + "\n" +
                "§cSeit: §f" + new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(entry.getCreated()) + "\n" +
                (remainingText.length() == 0 ? "" : "§cBis: §f" + remainingText + "\n") +
                "§cGrund: §f" + entry.getReason());
    }
}