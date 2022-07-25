package net.apotox.flash.game;

import lombok.Getter;
import lombok.Setter;
import net.apotox.flash.Flash;
import net.apotox.gameapi.util.Debug;
import net.apotox.gameapi.util.FileUtilities;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameMap {

    @Getter
    private String mapName, mapOwner;
    @Getter
    private FileConfiguration mapConfig;
    @Getter
    private File configFile;
    @Getter
    private World map;
    @Getter
    private List<Location> checkpoints;
    @Getter
    private HashMap<Integer, List<Location>> cornerpoints;
    @Getter @Setter
    private Location spawn;

    public GameMap() {
        File maps = new File(Flash.getInstance().getPath() + "/maps/");
        File world = maps.listFiles()[new Random().nextInt(maps.listFiles().length)];
        mapName = world.getName();
        FileUtilities.copyFolder(world, new File(Bukkit.getWorlds().get(0).getWorldFolder().getParentFile().getAbsolutePath() + "/" + mapName));

        File configFile = new File(world.getAbsolutePath() + "/config.yml");
        if(!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.configFile = configFile;
        mapConfig = YamlConfiguration.loadConfiguration(configFile);

        mapOwner = mapConfig.getString("builder");

        map = new WorldCreator(mapName).createWorld();
        map.setTime(6000);
        map.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        map.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        map.setGameRule(GameRule.MOB_GRIEFING, false);
        map.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        map.setGameRule(GameRule.DO_FIRE_TICK, false);
        map.setGameRule(GameRule.FIRE_DAMAGE, false);
        map.setGameRule(GameRule.KEEP_INVENTORY, true);
        Bukkit.getWorlds().get(0).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        checkpoints = new ArrayList<>();
        cornerpoints = new HashMap<>();
    }

    public void init() {

        int i = 1;
        while(Flash.getInstance().getLocationManager().getLocation("checkpoint" + i, true) != null) {
            checkpoints.add(Flash.getInstance().getLocationManager().getLocation("checkpoint" + i, true));

            Location cornerPoint1 = Flash.getInstance().getLocationManager().getLocation("checkpoint" + i + "_1", true);
            Location cornerPoint2 = Flash.getInstance().getLocationManager().getLocation("checkpoint" + i + "_2", true);

            cornerpoints.put(i, Arrays.asList(cornerPoint1, cornerPoint2));

            Debug.sendBroadcast(checkpoints.size() + " checks");
            i++;
        }
    }
}
