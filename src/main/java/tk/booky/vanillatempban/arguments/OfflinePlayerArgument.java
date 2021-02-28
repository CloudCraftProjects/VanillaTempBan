package tk.booky.vanillatempban.arguments;
// Created by booky10 in VanillaTempBan (18:30 28.02.21)

import dev.jorel.commandapi.arguments.CustomArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerArgument extends CustomArgument<OfflinePlayer> {

    public OfflinePlayerArgument(String nodeName) {
        super(nodeName, new OfflinePlayerArgumentParser(), false);
    }

    static class OfflinePlayerArgumentParser implements CustomArgument.CustomArgumentParser<OfflinePlayer> {

        @Override
        public OfflinePlayer apply(String input) throws CustomArgumentException {
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(input);

            if (player == null) {
                throw new CustomArgumentException("The player named %input% could not be found!");
            } else {
                return player;
            }
        }
    }
}