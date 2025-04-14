package ceres.th;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

@SuppressWarnings("deprecation")
public final class PlaceHolderHandler extends PlaceholderExpansion {

    public static final HashSet<BasePlaceHolder> holders = new HashSet<>();

    @Override
    public @NotNull String getIdentifier() {
        return TPSHolderCore.getInstance().getDescription().getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return TPSHolderCore.getInstance().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return TPSHolderCore.getInstance().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier){
        return holders.stream().filter(placeHolder -> placeHolder.getIdentifier().equals(identifier)).findFirst().map(placeHolder -> placeHolder.onPlaceholderRequest(player)).orElse(null);
    }
}
