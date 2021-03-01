package tk.booky.vanillatempban.arguments;
// Created by booky10 in VanillaTempBan (20:34 28.02.21)

import dev.jorel.commandapi.arguments.CustomArgument;

import java.util.concurrent.TimeUnit;

public class RealTimeArgument extends CustomArgument<Long> {

    public RealTimeArgument(String nodeName) {
        super(nodeName, new RealTimeArgumentParser(), false);
        overrideSuggestions("y", "d", "h", "m", "s");
    }

    static class RealTimeArgumentParser implements CustomArgumentParser<Long> {

        @Override
        public Long apply(String input) throws CustomArgumentException {
            try {
                long time = Long.parseLong(input.substring(0, input.length() - 1));
                switch (input.charAt(input.length() - 1)) {
                    case 'y':
                        time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.DAYS) * 356;
                        break;
                    case 'd':
                        time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.DAYS);
                        break;
                    case 'h':
                        time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.HOURS);
                        break;
                    case 'm':
                        time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.MINUTES);
                        break;
                    case 's':
                        time = TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
                        break;
                    default:
                        throw new CustomArgumentException(new MessageBuilder().appendArgInput().append(" is not a valid time!"));
                }
                return time;
            } catch (NumberFormatException exception) {
                throw new CustomArgumentException(new MessageBuilder().appendArgInput().append(" is not a valid time!"));
            }
        }
    }
}