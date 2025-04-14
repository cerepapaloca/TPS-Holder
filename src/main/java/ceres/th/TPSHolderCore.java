package ceres.th;

import ceres.th.placeholders.*;
import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
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
        for (double i = 0; i < 1; i += 0.05) {
            String hex = Utils.javaColorToStringHex(Gradient.GRADIENT.getColor(i));
            Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<" + hex + ">" + hex + "</" + hex + ">"));
        }
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
