package ceres.th;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class BasePlaceHolder {

    protected final String identifier;

    public BasePlaceHolder(String identifier) {
        this.identifier = identifier;
        PlaceHolderHandler.holders.add(this);
    }

    public abstract String onPlaceholderRequest(Player player);
}
