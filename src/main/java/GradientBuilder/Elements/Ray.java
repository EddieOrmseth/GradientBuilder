package GradientBuilder.Elements;

import GradientBuilder.Images.Image;

public class Ray extends Element {

    public double x = 0, y = 0;
    public double angle = 45, width = 0;

    public Ray( Image.ImageType imageType) {
        super(ElementType.Ray, imageType);
    }

    @Override
    protected double getDistance(double x, double y) {
        return 0;
    }

    @Override
    protected boolean isActive(double x, double y) {
        return false;
    }
}
