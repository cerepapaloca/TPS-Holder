package ceres.th.placeholders;

import ceres.th.BasePlaceHolder;
import ceres.th.Utils;
import org.bukkit.entity.Player;

public class CurrentTPS extends BasePlaceHolder {
    public CurrentTPS() {
        super("current-tps");
    }

    @Override
    public String onPlaceholderRequest(Player player) {
        Utils.getRegionTpsData(player).thenAccept(tpsData -> Utils.tpsDatas.put(player.getUniqueId(), tpsData));
        return String.format("%.2f", Utils.tpsDatas.getOrDefault(player.getUniqueId(), Utils.invalidateTpsData()).current());
    }
}
