package GradientBuilder.Util;

import GradientBuilder.Images.Image.ImageType;

import javax.swing.*;

public class InputChannelMask {

    public int xPos, yPos;
    public JLabel channelMask;
    public InputField r, g, b;

    int width, height = 25;
    int buffer = 10;

    ImageType state;

    public InputChannelMask(int xPos, int yPos, ImageType state) {
        this.state = state;
        this.xPos = xPos;
        this.yPos = yPos;
        channelMask = new JLabel();
        channelMask.setText("Channel Mask:");
        channelMask.setVisible(true);
        r = new InputField();
        g = new InputField();
        b = new InputField();
        enterState(state);
    }

    public void arrange() {
        enterState(state);
    }

    public InputChannelMask(ImageType state) {
        this(0,0, state);
    }

    public void add(JFrame frame) {
        frame.add(channelMask);
        r.add(frame);
        g.add(frame);
        b.add(frame);
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        arrange();
    }

    public void switchState(ImageType state) {
        enterState(state);
    }

    public void enterState(ImageType state) {

        channelMask.setBounds(xPos, yPos, 100, 25);
        int yPos = this.yPos + 25;

        this.state = state;
        switch (state) {
            case RGB:
                r.label.setText("R:");
                g.label.setText("G:");
                b.label.setText("B:");

                r.label.setBounds(xPos, yPos, 15, height);
                r.inputField.setBounds(xPos + r.label.getWidth(), yPos, 40, height);

                b.label.setBounds(r.inputField.getX() + r.inputField.getWidth() + buffer, yPos, 15, height);
                b.inputField.setBounds(b.label.getX() + b.label.getWidth(), yPos, 40, height);

                g.label.setBounds(b.inputField.getX() + b.inputField.getWidth() + buffer, yPos, 15, height);
                g.inputField.setBounds(g.label.getX() + g.label.getWidth(), yPos, 40, height);
                break;
            case Greyscale:
                r.label.setText("G:");

                r.label.setBounds(xPos, yPos, 15, height);
                r.inputField.setBounds(xPos + r.label.getWidth(), yPos, 40, height);

                b.label.setBounds(-100, 100, 0, 0);
                b.inputField.setBounds(-100, 100, 0, 0);

                g.label.setBounds(-100, 100, 0, 0);
                g.inputField.setBounds(-100, 100, 0, 0);
                break;
            default:
                throw new IllegalArgumentException("That Type Is Not Supported");
        }
    }

    public void setValues(ChannelMask mask) {
        switch (mask.imageType) {
            case RGB:
                r.inputField.setText("" + mask.values[0]);
                g.inputField.setText("" + mask.values[1]);
                b.inputField.setText("" + mask.values[2]);
                break;
            case Greyscale:
                r.inputField.setText("" + mask.values[0]);
                break;
        }

    }

}
