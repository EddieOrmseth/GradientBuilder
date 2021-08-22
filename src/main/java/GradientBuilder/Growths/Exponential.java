package GradientBuilder.Growths;

public class Exponential extends Growth {

    public double base = 0;
    public double scalar = 0;
    public double exponentCoefficient = 0;

    public Exponential() {
        super(GrowthType.Exponential);
    }

    @Override
    public void setValues(double[] values) {

    }

    @Override
    public double getBaseValue(double distance) {
        return scalar * (Math.pow(base, distance * exponentCoefficient));
    }
}
