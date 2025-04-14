package ceres.th;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Getter
@UtilityClass
public class Utils {

    public static final WeakHashMap<UUID, TpsData> tpsDatas = new WeakHashMap<>();

    // Código proporcionado por 8b8t
    public CompletableFuture<Double> getRegionTps(Location location) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        Bukkit.getRegionScheduler().run(TPSHolderCore.getInstance(),location, (st) -> future.complete(getCurrentRegionTps()));
        return future;
    }

    public double getCurrentRegionTps() {
        try {
            Object region = Class.forName("io.papermc.paper.threadedregions.TickRegionScheduler").getDeclaredMethod("getCurrentRegion").invoke(null);
            if (region != null) {
                Object tickData = region.getClass().getDeclaredMethod("getData").invoke(region);
                Object regionShceduleHandle = tickData.getClass().getDeclaredMethod("getRegionSchedulingHandle").invoke(tickData);
                Object tickReport = regionShceduleHandle.getClass().getMethod("getTickReport15s", long.class).invoke(regionShceduleHandle, System.nanoTime());
                Object segmentedAvg = tickReport.getClass().getDeclaredMethod("tpsData").invoke(tickReport);
                Object segAll = segmentedAvg.getClass().getDeclaredMethod("segmentAll").invoke(segmentedAvg);
                return (double) segAll.getClass().getDeclaredMethod("average").invoke(segAll);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return -1;
    }

    @NotNull
    @Contract(pure = true)
    public CompletableFuture<TpsData> getRegionTpsData(@NotNull Player p) {
        List<CompletableFuture<Double>> futuresAll = new ArrayList<>();
        List<CompletableFuture<Double>> futures = new ArrayList<>();
        CompletableFuture<Double> future = getRegionTps(p.getLocation());
        futuresAll.add(future);
        for (Player player : Bukkit.getOnlinePlayers()) {
            CompletableFuture<Double> f = getRegionTps(player.getLocation());
            futuresAll.add(f);
            futures.add(f);
        }
        return CompletableFuture.allOf(futuresAll.toArray(new CompletableFuture[0])).thenApply(v -> {
            LinkedList<Double> list = new LinkedList<>();
            for (CompletableFuture<Double> f : futures) {
                try {
                    list.add(f.get());
                } catch (InterruptedException | ExecutionException e) {

                }
            }
            double[] array = list.stream().mapToDouble(Double::doubleValue).toArray();
            double currentTPS = -1d;
            try {
                currentTPS = future.get();
            }catch (InterruptedException | ExecutionException ignored){

            }

            double average = Arrays.stream(array).average().orElse(0.0);
            double max = Arrays.stream(array).max().orElse(0.0);
            double min = Arrays.stream(array).min().orElse(0.0);
            return new TpsData(average, max, min, currentTPS);
        });
    }

    public record TpsData(double average, double max, double min, double current) {
    }

    @NotNull
    @Contract("-> new")
    public TpsData invalidateTpsData() {
        return new TpsData(-1, -1, -1, -1);
    }

    @Contract(pure = true)
    public String javaColorToStringHex(java.awt.Color color) {
        String r = addZeros(Integer.toString(color.getRed(), 16));
        String g = addZeros(Integer.toString(color.getGreen(), 16));
        String B = addZeros(Integer.toString(color.getBlue(), 16));
        return ("#" + r + g + B).toUpperCase();
    }

    @Contract(pure = true)
    private @NotNull String addZeros(@NotNull String s){
        switch (s.length()){
            case 0 -> {
                return "00";
            }
            case 1 -> {
                return "0" + s;
            }
            default -> {
                return s;
            }
        }
    }
}
