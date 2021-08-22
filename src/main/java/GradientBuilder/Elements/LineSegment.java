package GradientBuilder.Elements;

import GradientBuilder.Images.Image;

public class LineSegment extends Element {

    public double x1 = 0, y1 = 0, x2 = 0, y2 = 0;

    public LineSegment(Image.ImageType imageType) {
        super(ElementType.LineSegment, imageType);
    }

    @Override
    protected double getDistance(double x, double y) {

        if (x1 == x2 && y1 == y2) {
            return Math.sqrt(((x - x1) * (x - x2)) + ((y - y1) * (y - y2)));
        }

        if (x1 == x2) {
            double tempY1 = Math.min(y1, y2), tempY2 = Math.max(y1, y2);

            if (y < tempY1) {
                return Math.sqrt(((x1 - x) * (x1 - x)) + ((tempY1 - y) * (tempY1 - y)));
            } else if (y > tempY2) {
                return Math.sqrt(((x2 - x) * (x2 - x)) + ((tempY2 - y) * (tempY2 - y)));
            } else {
                return Math.abs(x - x1);
            }
        } else if (y1 == y2) {
            double tempX1 = Math.min(x1, x2), tempX2 = Math.max(x1, x2);

            if (x < tempX1) {
                return Math.sqrt(((tempX1 - x) * (tempX1 - x)) + ((y1 - y) * (y1 - y)));
            } else if (x > tempX2) {
                return Math.sqrt(((tempX2 - x) * (tempX2 - x)) + ((y2 - y) * (y2 - y)));
            } else {
                return Math.abs(y - y1);
            }
        }

//        double slope = (y2 - y1) / (x2 -x1);
//        double yIntercept = y1 - (slope * x1);
//
//        double perpSlope = -1 * (1 / slope);
//
//        double upperPerpYIntercept = y1 - (perpSlope * x1);
//
//        if (y > ((perpSlope * x) + upperPerpYIntercept)) {
////            double intersectX = (upperPerpYIntercept - yIntercept) / (slope - perpSlope);
////            double intersectY = intersectX * slope + yIntercept;
////            return Math.sqrt(((intersectX - x) * (intersectX - x)) + ((intersectY - y) * (intersectY - y)));
//            return Math.sqrt(((y - y1) * (y - y1)) + ((x - x1) * (x - x1)));
//        }
//
//        double lowerPerpYIntercept = y2 - (perpSlope * x2);
//
//        if (y < ((perpSlope * x) + lowerPerpYIntercept)) {
////            double intersectX = (lowerPerpYIntercept - yIntercept) / (slope - perpSlope);
////            double intersectY = intersectX * slope + yIntercept;
////            return Math.sqrt(((intersectX - x) * (intersectX - x)) + ((intersectY - y) * (intersectY - y)));
//            return Math.sqrt(((y - y2) * (y - y2)) + ((x - x2) * (x - x2)));
//        }
//
//        double perpYIntercept = y - (perpSlope * x);
//
//        double intersectX = (perpYIntercept - yIntercept) / (slope - perpSlope);
//        double intersectY = intersectX * slope + yIntercept;
//
//        return Math.sqrt(((intersectX - x) * (intersectX - x)) + ((intersectY - y) * (intersectY - y)));

        double slope = (y2 - y1) / (x2 - x1);
        double yInt = y1 - (slope * x1);

        double perpSlope = -1 * (1 / slope);

        double yInt1 = y1 - (perpSlope * x1);
        double yInt2 = y2 - (perpSlope * x2);

        double tempV1 = yInt1 + (x * perpSlope);
        double tempV2 = yInt2 + (x * perpSlope);

        double v1 = Math.min(tempV1, tempV2);
        double v2 = Math.max(tempV1, tempV2);

        if (y > v1 && y < v2) { // Between Lines

            double perpYIntercept = y - (perpSlope * x);

            double intersectX = (perpYIntercept - yInt) / (slope - perpSlope);
            double intersectY = intersectX * slope + yInt;

            return Math.sqrt(((intersectX - x) * (intersectX - x)) + ((intersectY - y) * (intersectY - y)));
        } else { // Not Between Lines
            double d1 = Math.sqrt(((x - x1) * (x - x1)) + ((y - y1) * (y - y1)));
            double d2 = Math.sqrt(((x - x2) * (x - x2)) + ((y - y2) * (y - y2)));
            return Math.min(d1, d2);
        }

    }

    @Override
    protected boolean isActive(double x, double y) {
        return true;
    }
}
