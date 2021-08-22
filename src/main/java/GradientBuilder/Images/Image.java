package GradientBuilder.Images;

import GradientBuilder.Elements.Element;
import GradientBuilder.Util.ButtonMap;
import GradientBuilder.Util.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Image extends Component {

    public static final String[] imageTypes = {"" + ImageType.RGB, "" + ImageType.Greyscale};

    public enum ImageType {
        RGB,
        Greyscale
    }

    int width, height;
    Pixel backGround;
    public BufferedImage image;

    public ImageType imageType;

    public ButtonMap<Element> elements;

    public Image(ImageType imageType, int width, int height) {
        this.width = width;
        this.height = height;
        this.imageType = imageType;
        elements = new ButtonMap<>();
        backGround = new Pixel(imageType);

        if (imageType == ImageType.Greyscale) {
            image = new BufferedImage(width, height, 10);
        } else if (imageType == ImageType.RGB) {
            image = new BufferedImage(width, height, 1);
        } else {
            throw new IllegalArgumentException("That Argument Type Is Not Supported");
        }
    }

    public void set() {
        WritableRaster raster = image.getRaster();
        Pixel pixel = new Pixel(backGround);

        for (int y = 0; y < raster.getHeight(); y++) {
            for (int x = 0; x < raster.getWidth(); x++) {
                pixel.set(backGround.values);

                for (int i = 0; i < elements.values.size(); i++) {
                    pixel.add(elements.values.get(i).get(x, y));
                }

                finalThresh(pixel);

                raster.setPixel(x, y, pixel.values);
            }
        }

    }

    public void finalThresh(Pixel pixel) {
        for (int i = 0; i < pixel.values.length; i++) {
            if (pixel.values[i] > 255) {
                pixel.values[i] = 255;
            } else if (pixel.values[i] < 0) {
                pixel.values[i] = 0;
            }

            pixel.values[i] = (int) pixel.values[i];
        }
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(image, 0, 0, this);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public static int imageTypeIndexOf(String imageType) {

        for (int i = 0; i < imageTypes.length; i++) {
            if (imageType.equals(imageTypes[i])) {
                return i;
            }
        }

        System.out.println("Failed To Find ImageType Index");
        return -1;
    }

    public static ImageType parseImageType(String imageType) {
        if (imageType.equals("Greyscale")) {
            return ImageType.Greyscale;
        } else if (imageType.equals("RGB")) {
            return ImageType.RGB;
        } else {
            throw new IllegalArgumentException("Could Not Parse ImageType");
        }
    }

    public void realloc(ImageType imageType, int width, int height) {
        this.width = width;
        this.height = height;
        this.imageType = imageType;
        backGround = new Pixel(imageType);

        if (imageType == ImageType.Greyscale) {
            image = new BufferedImage(width, height, 10);
        } else if (imageType == ImageType.RGB) {
            image = new BufferedImage(width, height, 1);
        } else {
            throw new IllegalArgumentException("That ImageType Is Not Supported");
        }

    }



}
