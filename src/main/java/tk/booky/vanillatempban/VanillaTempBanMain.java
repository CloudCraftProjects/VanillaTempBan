package tk.booky.vanillatempban;

import org.bukkit.plugin.java.JavaPlugin;
import tk.booky.vanillatempban.commands.TempBanCommand;

public final class VanillaTempBanMain extends JavaPlugin {

    public static VanillaTempBanMain main;

    @Override
    public void onEnable() {
        main = this;

        new TempBanCommand().register();
    }
}
