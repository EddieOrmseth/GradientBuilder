package GradientBuilder.Util;

import javax.swing.*;
import java.awt.*;

public class InputField {

//    int buffer = 5;
//
//    int x, y, width, height, midpoint;
//
//    JLabel label;
//    JTextField input;
//
//    public InputField(int x, int y, int width, int height, int midPoint, String label) {
//        this.x = x;
//        this.y = y;
//        this.width = width;
//        this.height = height;
//        this.midpoint = midPoint;
//
//        this.label = new JLabel();
//        this.label.setText(label);
//
//
//        input = new JTextField();
//
//
//    }

    public JLabel label;
    public JTextField inputField;

    public InputField() {
        label = new JLabel();
        label.setVisible(true);

        inputField = new JTextField();
        inputField.setVisible(true);
    }

    public void add(JFrame frame) {
        frame.add(label);
        frame.add(inputField);
    }

    public void remove(JFrame frame) {
        frame.remove(label);
        frame.remove(inputField);
    }

}
