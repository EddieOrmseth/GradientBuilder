package GradientBuilder.Growths;

public class Trigonometric extends Growth {

    public static final String[] trigTypes = {"" + TrigType.Sin, "" + TrigType.Cos, "" + TrigType.Tan};

    public enum TrigType {
        Sin,
        Cos,
        Tan
    }

    TrigType trigType;

    double outerCoefficient = 0;
    double innerCoefficient = 0;
    double power = 0;

    public Trigonometric() {
        super(GrowthType.Trigonometric);
    }

    @Override
    public void setValues(double[] values) {

    }

    @Override
    public double getBaseValue(double distance) {
        return 0;
    }
}
