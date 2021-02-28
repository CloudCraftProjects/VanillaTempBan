package tk.booky.vanillatempban;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.booky.vanillatempban.commands.TempBanCommand;
import tk.booky.vanillatempban.listener.BannedLoginListener;

public final class VanillaTempBanMain extends JavaPlugin {

    public static VanillaTempBanMain main;

    @Override
    public void onEnable() {
        main = this;

        new TempBanCommand().register();
        Bukkit.getPluginManager().registerEvents(new BannedLoginListener(), this);
    }
}
