package GradientBuilder.Elements;

import GradientBuilder.Images.Image;

public class Circle extends Element {

    public double x = 0, y = 0;
    public double radius = 0;

    public Circle(Image.ImageType imageType) {
        super(ElementType.Circle, imageType);
    }

    @Override
    protected double getDistance(double x, double y) {
        double dist = Math.sqrt(((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)));
        return Math.abs(dist - radius);
    }

    @Override
    protected boolean isActive(double x, double y) {
        return true;
    }
}
