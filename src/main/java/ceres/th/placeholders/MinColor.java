package ceres.th.placeholders;

import ceres.th.BasePlaceHolder;
import ceres.th.Gradient;
import ceres.th.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MinColor extends BasePlaceHolder {
    public MinColor() {
        super("min-color");
    }

    @Override
    public String onPlaceholderRequest(Player player) {
        Utils.getRegionTpsData(player).thenAccept(tpsData -> Utils.tpsDatas.put(player.getUniqueId(), tpsData));
        double d = Utils.tpsDatas.getOrDefault(player.getUniqueId(), Utils.invalidateTpsData()).min();
        return Utils.javaColorToStringHex(Gradient.GRADIENT.getColor(d/20));
    }
}
