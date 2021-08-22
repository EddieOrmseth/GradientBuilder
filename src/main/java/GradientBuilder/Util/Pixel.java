package GradientBuilder.Util;

import GradientBuilder.Images.Image.ImageType;

import java.util.Arrays;

public class Pixel {

    public Pixel(ImageType imageType) {
        this.imageType = imageType;
        if (imageType == ImageType.RGB) {
            values = new double[3];
        } else if (imageType == ImageType.Greyscale) {
            values = new double[1];
        } else {
            throw new IllegalArgumentException("That ImageType Is Not Supported");
        }

        Arrays.fill(values, 0);
    }

    public Pixel(ImageType imageType, double... values) {
        this(imageType);
        set(values);
    }

    public Pixel(Pixel pixel) {
        this(pixel.imageType, pixel.values);
    }

    public ImageType imageType;
    public double[] values;

    public void set(int channel, double value) {
        values[channel] = value;
    }

    public void set(double... newValues) {

        if (values.length != newValues.length) {
            throw new IllegalArgumentException("When Passing In An Array You Must Provide an Argument For Every Value, Passing -1 Will Not Change The Value");
        }

        for (int i = 0; i < newValues.length; i++) {
            if (newValues[i] == -1) {
                continue;
            } else {
                values[i] = newValues[i];
            }
        }

    }

    public void applyMask(ChannelMask mask) {
        if (imageType != mask.imageType) {
            throw new IllegalArgumentException("The Applied Mask Must Have The Same ImageType as The Pixel\n\tPixel ImageType: " + imageType + "\n\tMask ImageType: " + mask.imageType);
        }
        for (int i = 0; i < values.length; i++) {
            values[i] *= mask.values[i];
        }
    }

    public void add(Pixel pixel) {
        if (imageType != pixel.imageType) {
            throw new IllegalArgumentException("The Pixels Must Have The Same ImageType To Add");
        }

        for (int i = 0; i < values.length; i++) {
            values[i] += pixel.values[i];
        }
    }

}
