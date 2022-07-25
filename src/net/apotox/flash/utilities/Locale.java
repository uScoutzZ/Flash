package net.apotox.flash.utilities;

import net.apotox.flash.Flash;
import net.apotox.gameapi.user.User;
import org.bukkit.entity.Player;

public class Locale {

    public static String get(Player player, String locale, Object... variables) {
        if(User.getFromPlayer(player) != null) {
            String message = Flash.getInstance().getLanguageHandler().translate(User.getFromPlayer(player).getLanguage(), locale, new Object[0]);
            int i = 0;
            for(Object argument : variables) {
                message = message.replace("{" + i + "}", String.valueOf(variables[i]));
                i++;
            }
            return message;
        }

        return "N/A";
    }
}
