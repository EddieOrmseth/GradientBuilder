package GradientBuilder.Elements;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Growths.Growth;
import GradientBuilder.Images.Image;
import GradientBuilder.Images.Image.ImageType;
import GradientBuilder.Util.ButtonMap;
import GradientBuilder.Util.ChannelMask;
import GradientBuilder.Util.Pixel;
import GradientBuilder.Windows.ElementEditor;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Element {

    public static final String[] elementTypes = {"" + ElementType.Point, "" + ElementType.Line, "" + ElementType.LineSegment, "" + ElementType.Ray, "" + ElementType.Circle};

    public enum ElementType {
        Point,
        Line,
        LineSegment,
        Ray,
        Circle
    }

    public double maximumDist = 0, minimumDist = 0;
    public double minimum = 0, maximum = 0;

    public ElementType elementType;
    public ImageType imageType;
    public ChannelMask mask;

    public ButtonMap<Growth> growths;

    public boolean elementWindowActive = false;
    public ElementEditor elementEditorWindow;

    public Element(ElementType elementType, ImageType imageType) {
        this.elementType = elementType;
        this.imageType = imageType;
        mask = new ChannelMask(imageType);
        growths = new ButtonMap<>();
    }

    private double getDistance(Point point) {
        return getDistance(point.x, point.y);
    }
    protected abstract double getDistance(double x, double y);

    private boolean isActive(Point point) {
        return isActive(point.x, point.y);
    }
    protected abstract boolean isActive(double x, double y);

    public Pixel get(Point point) {
        return get(point.x, point.y);
    }
    public Pixel get(double x, double y) {

        Pixel pixel = new Pixel(imageType);

        if (!isActive(x, y)) {
            return pixel;
        }

        double distance = getDistance(x, y);
        if (!thresholdDistance(distance)) {
            return pixel;
        }

        double value = 0;

        for (int i = 0; i < growths.values.size(); i++) {
            value += growths.values.get(i).getValue(distance);
        }

        Arrays.fill(pixel.values, value);

        pixel.applyMask(mask);

        return thresholdPixel(pixel);
    }

    private boolean thresholdDistance(double distance) {
        if (maximumDist == 0 && minimumDist == 0) {
            return true;
        }
        return distance <= maximumDist && distance >= minimumDist;
    }

    private Pixel thresholdPixel(Pixel pixel) {

        for (int i = 0; i < pixel.values.length; i++) {

            if (maximum == 0 && minimum == 0) {
                break;
            }

            if (pixel.values[i] > maximum) {
                pixel.values[i] = maximum;
            } else if (pixel.values[i] < minimum) {
                pixel.values[i] = minimum;
            }

        }

        return pixel;
    }

    public static ElementType parseElementType(String elementType) {
        if (elementType.equals("Point")) {
            return ElementType.Point;
        } else if (elementType.equals("Line")) {
            return ElementType.Line;
        } else if (elementType.equals("LineSegment")) {
            return ElementType.LineSegment;
        } else if (elementType.equals("Ray")) {
            return ElementType.Ray;
        } else if (elementType.equals("Circle")) {
            return ElementType.Circle;
        } else {
            throw new IllegalArgumentException("ElementType Could Not Be Parsed");
        }
    }

    public static int elementTypeIndexOf(String elementType) {

        for (int i = 0; i < elementTypes.length; i++) {
            if (elementType.equals(elementTypes[i])) {
                return i;
            }
        }

        System.out.println("Failed To Find ElementType Index");
        return -1;
    }

    public static Element getElementForType(ImageType imageType, ElementType elementType) {
        switch (elementType) {
            case Point:
                return new Point(imageType);
            case Line:
                return new Line(imageType);
            case LineSegment:
                return new LineSegment(imageType);
            case Ray:
                return new Ray(imageType);
            case Circle:
                return new Circle(imageType);
            default:
                throw new IllegalArgumentException("That ElementType Is No Supported");
        }
    }

    public void activateWindow(GradientBuilder builder, Image image) {
        if (elementWindowActive) { // What If We Close Window Manually First?
            builder.removeWindow(elementEditorWindow);
        }
        elementEditorWindow = new ElementEditor(builder, image, this);
        elementWindowActive = true;
    }

    public void addGrowths(ArrayList<Growth> growthsToAdd) {

        for (int i = 0; i < growthsToAdd.size(); i++) {
            Growth growth = growthsToAdd.get(i);
            growths.addValue(growth);
            growths.getButton(growth).setText("" + growth.growthType);
        }

    }

}
