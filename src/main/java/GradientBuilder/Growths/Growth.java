package GradientBuilder.Growths;

import GradientBuilder.Elements.Element;
import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;
import GradientBuilder.Windows.GrowthEditor;

public abstract class Growth {

    public static final String[] growthTypes = {"" + GrowthType.Power, "" + GrowthType.Exponential, "" + GrowthType.Logarithmic, "" + GrowthType.Trigonometric};

    public enum GrowthType {
        Power,
        Exponential,
        Logarithmic,
        Trigonometric
    }

    public double initialValue = 0;
    public double minValue = 0, maxValue = 0;

    public GrowthType growthType;

    boolean growthWindowActive = false;
    GrowthEditor growthEditorWindow;

    public Growth(GrowthType type) {
        this.growthType = type;
    }

    public abstract void setValues(double[] values);

    public abstract double getBaseValue(double distance);

    public double getValue(double distance) {
        return threshold(getBaseValue(distance));
    }

    private double threshold(double value) {
        if (maxValue == 0 && minValue == 0) {
            return value;
        }

        if (value > maxValue) {
            value = maxValue;
        } else if (value < minValue) {
            value = minValue;
        }

        return value;
    }

    public static GrowthType parseGrowth(String growthType) {
        if (growthType.equals("Power")) {
            return GrowthType.Power;
        } else if (growthType.equals("Exponential")) {
            return GrowthType.Exponential;
        } else if (growthType.equals("Logarithmic")) {
            return GrowthType.Logarithmic;
        } else if (growthType.equals("Trigonometric")) {
            return GrowthType.Trigonometric;
        } else {
            throw new IllegalArgumentException("Could Not Parse Growth Type");
        }
    }

    public static int growthTypeIndexOf(String growthType) {

        for (int i = 0; i < growthTypes.length; i++) {
            if (growthType.equals(growthTypes[i])) {
                return i;
            }
        }

        System.out.println("Failed To Find GrowthType Index");
        return -1;
    }

    public static Growth getGrowthForType(GrowthType growthType) {
        switch (growthType) {
            case Power:
                return new Power();
            case Exponential:
                return new Exponential();
            case Logarithmic:
                return new Logarithmic();
            case Trigonometric:
                return new Trigonometric();
            default:
                throw new IllegalArgumentException("That GrowthType Is Not Supported");
        }

    }

    public void activateWindow(GradientBuilder builder, Image image, Element element) {
        if (growthWindowActive = true) { // What If We Close Window Manually First?
            builder.removeWindow(growthEditorWindow);
        }
        growthEditorWindow = new GrowthEditor(builder, image, element, this);
        growthWindowActive = true;
    }

}
