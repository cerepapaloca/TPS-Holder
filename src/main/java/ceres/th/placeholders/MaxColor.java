package ceres.th.placeholders;

import ceres.th.BasePlaceHolder;
import ceres.th.Gradient;
import ceres.th.Utils;
import org.bukkit.entity.Player;

public class MaxColor extends BasePlaceHolder {
    public MaxColor() {
        super("max-color");
    }

    @Override
    public String onPlaceholderRequest(Player player) {
        Utils.getRegionTpsData(player).thenAccept(tpsData -> Utils.tpsDatas.put(player.getUniqueId(), tpsData));
        double d = Utils.tpsDatas.getOrDefault(player.getUniqueId(), Utils.invalidateTpsData()).max();
        return Utils.javaColorToStringHex(Gradient.GRADIENT.getColor(d/20));
    }
}
