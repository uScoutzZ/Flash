package net.animalshomeland.flash;

import net.animalshomeland.flash.game.Game;
import lombok.Getter;
import net.animalshomeland.flash.game.ScoreboardManager;
import net.animalshomeland.flash.utilities.LocationManager;
import net.animalshomeland.gameapi.GameConfig;
import net.animalshomeland.gameapi.GamePlugin;
import net.animalshomeland.gameapi.language.LanguageHandler;

import java.io.IOException;

public class Flash extends GamePlugin {

    @Getter
    static Flash instance;
    @Getter
    private String path;
    @Getter
    private Game game;
    @Getter
    private LocationManager locationManager;
    @Getter
    private ScoreboardManager scoreboardManager;

    @Override
    public void onPluginEnable() throws IOException {
        instance = this;

        path = "/home/networksync/flash/";

        GameConfig gameConfig = new GameConfig(this);
        gameConfig.setPath(getPath());
        setGameConfig(gameConfig);

        gameConfig.setLanguageHandler(new LanguageHandler(this));
        gameConfig.setConfigFile(path + "config.yml");

        game = new Game();
        locationManager = new LocationManager();
        scoreboardManager = new ScoreboardManager();

        getAutoRegister().registerCommands("net.animalshomeland.flash.commands");
        getAutoRegister().registerListeners("net.animalshomeland.flash.listener");
    }

    @Override
    public void onPluginDisable() {

    }
}
