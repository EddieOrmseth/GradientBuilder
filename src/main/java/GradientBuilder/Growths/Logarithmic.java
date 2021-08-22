package GradientBuilder.Growths;

public class Logarithmic extends Growth {

    public double base = 0;
    public double coefficient = 0;

    public Logarithmic() {
        super(GrowthType.Logarithmic);
    }

    @Override
    public void setValues(double[] values) {

    }

    @Override
    public double getBaseValue(double distance) {
        return initialValue + (coefficient * (Math.log(distance) / Math.log(base)));
    }
}
