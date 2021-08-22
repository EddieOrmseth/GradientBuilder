package GradientBuilder.Util;

import javax.swing.*;

public class InputPoint {

    int xPos, yPos;
    public InputField x, y;

    int width, height = 25;
    int buffer = 10;

    public InputPoint(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        x = new InputField();
        y = new InputField();

    }

    public void arrange() {
        x.label.setText("X:");
        y.label.setText("Y:");
        x.inputField.setText("0");
        y.inputField.setText("0");

        x.label.setBounds(xPos, yPos, 15, height);
        x.inputField.setBounds(xPos + x.label.getWidth(), yPos, 35, height);

        y.label.setBounds(xPos + x.inputField.getWidth() + buffer, yPos, 15, height);
        y.inputField.setBounds(xPos + y.label.getWidth(), yPos, 35, height);
    }

    public void add(JFrame frame) {
        x.add(frame);
        y.add(frame);
    }

    public void remove(JFrame frame) {
        x.remove(frame);
        y.remove(frame);
    }

    public void setPos(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        arrange();
    }

}
