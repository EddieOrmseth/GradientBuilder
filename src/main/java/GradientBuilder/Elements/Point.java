package GradientBuilder.Elements;

import GradientBuilder.Images.Image;

public class Point extends Element {

    public double x = 0, y = 0;

    public Point(Image.ImageType imageType) {
        super(ElementType.Point, imageType);
    }

    @Override
    protected double getDistance(double x, double y) {
        return Math.sqrt(((this.x - x) * (this.x - x)) + ((this.y - y) * (this.y - y)));
    }

    @Override
    protected boolean isActive(double x, double y) {
        return true;
    }
}
