package net.apotox.flash;

import net.apotox.flash.game.Game;
import lombok.Getter;
import net.apotox.flash.game.ScoreboardManager;
import net.apotox.flash.utilities.LocationManager;
import net.apotox.gameapi.GameConfig;
import net.apotox.gameapi.GamePlugin;
import net.apotox.gameapi.language.LanguageHandler;

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

        getAutoRegister().registerCommands("net.apotox.flash.commands");
        getAutoRegister().registerListeners("net.apotox.flash.listener");
    }

    @Override
    public void onPluginDisable() {

    }
}
