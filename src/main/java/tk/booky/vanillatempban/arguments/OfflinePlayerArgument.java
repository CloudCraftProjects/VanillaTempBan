package tk.booky.vanillatempban.arguments;
// Created by booky10 in VanillaTempBan (18:30 28.02.21)

import dev.jorel.commandapi.arguments.CustomArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class OfflinePlayerArgument extends CustomArgument<OfflinePlayer> {

    public OfflinePlayerArgument(String nodeName) {
        super(nodeName, new OfflinePlayerArgumentParser(), false);
        overrideSuggestions(commandSender -> Bukkit.getOnlinePlayers().stream().map((Function<Player, String>) HumanEntity::getName).toArray(String[]::new));
    }

    static class OfflinePlayerArgumentParser implements CustomArgument.CustomArgumentParser<OfflinePlayer> {

        @Override
        public OfflinePlayer apply(String input) throws CustomArgumentException {
            OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(input);

            if (player == null) {
                throw new CustomArgumentException(new MessageBuilder("The player named ").appendArgInput().append(" could not be found!"));
            } else {
                return player;
            }
        }
    }
}