package GradientBuilder.Util;

import GradientBuilder.Images.Image.ImageType;

import java.util.Arrays;

public class ChannelMask {

    public ChannelMask(ImageType imageType) {
        this.imageType = imageType;
        if (imageType == ImageType.RGB) {
            values = new double[3];
        } else if (imageType == ImageType.Greyscale) {
            values = new double[1];
        } else {
            throw new IllegalArgumentException("That ImageType Is Not Supported");
        }

        Arrays.fill(values, 1);
    }

    public ChannelMask(ImageType imageType, double... newValues) {
        this(imageType);
        set(newValues);
    }

    public ImageType imageType;
    public double[] values;

    public void set(int channel, double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Channel Mask Values Are Percentages an Must Be Between 1 and 0");
        }
        values[channel] = value;
    }

    public void set(double... newValues) {

        if (values.length != newValues.length) {
            throw new IllegalArgumentException("When Passing In An Array You Must Provide an Argument For Every Value, Passing -1 Will Not Change The Value");
        }

        for (int i = 0; i < newValues.length; i++) {
            if (newValues[i] == -1) {
                continue;
            } else if (newValues[i] < 0 || newValues[i] > 1) {
                throw new IllegalArgumentException("Channel Mask Values Are Percentages an Must Be Between 1 and 0");
            } else {
                values[i] = newValues[i];
            }
        }

    }

}
