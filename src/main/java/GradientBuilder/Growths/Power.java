package GradientBuilder.Growths;

public class Power extends Growth {

    public double power = 0;
    public double coefficient = 0;

    public Power() {
        super(GrowthType.Power);
    }

    @Override
    public void setValues(double[] values) {

    }

    @Override
    public double getBaseValue(double distance) {
        return initialValue + (coefficient * Math.pow(distance, power));
    }
}
