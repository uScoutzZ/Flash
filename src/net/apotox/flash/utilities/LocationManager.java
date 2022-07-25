package net.apotox.flash.utilities;

import net.apotox.flash.Flash;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {

    public Location getLocation(String path, boolean ingameMap) {
        Location location;

        try {
            FileConfiguration cfg = Flash.getInstance().getGame().getGameMap().getMapConfig();
            String w;
            if(!ingameMap/*&& Flash.getInstance().getGame().getGameState() != GameState.INGAME*/) {
                cfg = Flash.getInstance().getGameConfig().getConfigFile();
                w = Bukkit.getWorlds().get(0).getName();
            } else {
                w = cfg.getString(path + ".world");
            }

            double x = cfg.getDouble(path + ".x");
            double y = cfg.getDouble(path + ".y");
            double z = cfg.getDouble(path + ".z");
            float yaw = (float) cfg.getDouble(path + ".yaw");
            float pitch = (float) cfg.getDouble(path + ".pitch");

            location = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);

            return location;
        } catch (Exception exception) {
            return null;
        }
    }
}
