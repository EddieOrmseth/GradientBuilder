package GradientBuilder.Elements;

import GradientBuilder.Images.Image;

public class Line extends Element {

    public double x1 = 0, y1 = 0, x2 = 0, y2 = 0;

    public Line(Image.ImageType imageType) {
        super(ElementType.Line, imageType);
    }

    @Override
    protected double getDistance(double x, double y) {

        if (x1 == x2 && y1 == y2) {
            return Math.sqrt(((this.x1 - x) * (this.x2 - x)) + ((this.y1 - y) * (this.y2 - y)));
        }

        if (x1 == x2) {
            return Math.abs(x - x1);
        } else if (y1 == y2) {
            return Math.abs(y - y1);
        }

        double slope = (y2 - y1) / (x2 - x1);
        double yIntercept = y1 - (slope * x1);

        double perpSlope = -1 * (1 / slope);
        double perpYIntercept = y - (perpSlope * x);

        double intersectX = (perpYIntercept - yIntercept) / (slope - perpSlope);
        double intersectY = intersectX * slope + yIntercept;

        return Math.sqrt(((intersectX - x) * (intersectX - x)) + ((intersectY - y) * (intersectY - y)));
    }

    @Override
    protected boolean isActive(double x, double y) {
        return true;
    }
}
