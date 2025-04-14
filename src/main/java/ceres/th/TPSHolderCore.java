package ceres.th;

import ceres.th.placeholders.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class TPSHolderCore extends JavaPlugin {

    @Getter
    private static TPSHolderCore instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        new PlaceHolderHandler().register();

        new AverageTPS();
        new MaxTPS();
        new MinTPS();
        new CurrentTPS();
        new AverageColor();
        new MaxColor();
        new MinColor();
        new CurrentColor();
    }

    @Override
    public void onDisable() {
        instance = null;
        // Plugin shutdown logic
    }
}
