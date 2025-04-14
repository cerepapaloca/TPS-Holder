package ceres.th;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gradient {

    public static final Gradient GRADIENT = new Gradient().addGradient(Color.red, 1).addGradient(Color.red, 10).addGradient(Color.yellow, 16).addGradient(Color.green, 2);

    private static class GradientSegment {
        Color color;
        double proportion;

        GradientSegment(Color color, double proportion) {
            this.color = color;
            this.proportion = proportion;
        }
    }

    private final List<GradientSegment> segments = new ArrayList<>();
    private double totalProportion = 0;

    public Gradient addGradient(Color color, double proportion) {
        if (proportion <= 0) {
            throw new IllegalArgumentException("La proporción debe ser mayor que 0.");
        }
        segments.add(new GradientSegment(color, proportion));
        totalProportion += proportion;
        return this;
    }

    public Color getColor(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("El valor debe estar entre 0 y 1. El valor es: " + value);
        }

        if (segments.isEmpty()) {
            throw new IllegalStateException("No hay segmentos de gradiente definidos.");
        }

        if (segments.size() == 1 || value == 0) {
            return segments.get(0).color;
        }

        double accumulatedProportion = 0;
        for (int i = 0; i < segments.size() - 1; i++) {
            GradientSegment start = segments.get(i);
            GradientSegment end = segments.get(i + 1);

            double startProportion = accumulatedProportion / totalProportion;
            accumulatedProportion += end.proportion;
            double endProportion = accumulatedProportion / totalProportion;

            if (value <= endProportion) {
                double localValue = normalize(startProportion, endProportion, value);
                return blendColors(start.color, end.color, localValue);
            }
        }

        return segments.get(segments.size()-1).color;
    }

    private static Color blendColors(Color c1, Color c2, double ratio) {
        int r = (int) (c1.getRed() * (1 - ratio) + c2.getRed() * ratio);
        int g = (int) (c1.getGreen() * (1 - ratio) + c2.getGreen() * ratio);
        int b = (int) (c1.getBlue() * (1 - ratio) + c2.getBlue() * ratio);
        return new Color(r, g, b);
    }

    private static double normalize(double min, double max, double value) {
        return (value - min) / (max - min);
    }
}

