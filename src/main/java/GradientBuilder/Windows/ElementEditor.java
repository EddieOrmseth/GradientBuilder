package GradientBuilder.Windows;

import GradientBuilder.Elements.*;
import GradientBuilder.Elements.Element.ElementType;
import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;
import GradientBuilder.Util.InputChannelMask;
import GradientBuilder.Util.InputField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ElementEditor extends Window implements ActionListener {

    Image image;
    ArrayList<Element> elements;
    Element element;
    ElementType state;

    // Element Type
    JLabel elementLabel;
    JComboBox<String> elementType;

    // Inputs
    JTextField i1;
    JTextField i2;
    JTextField i3;
    JTextField i4;

    JLabel l1;
    JLabel l2;
    JLabel l3;
    JLabel l4;

    // Defaults
    // Channel Mask
    InputChannelMask maskInput;

    InputField minValue, maxValue;
    InputField minDist, maxDist;

    JButton growthManager;

    boolean growthWindowActive = false;
    GrowthManager growthManagerWindow;

    public ElementEditor(GradientBuilder builder, Image image, Element element) {
        super(WindowType.ElementEditor, builder);
        this.image = image;
        elements = image.elements.values;
        this.element = element;
        state = element.elementType;
        setBounds(100, 100, 0, 0);
        setTitle("Element Editor");
        configure();
        enterState(state);
        builder.addWindow(this);
        setVisible(true);
    }

    public void configure() {
        elementLabel = new JLabel();
        elementLabel.setText("Element Type:");
        elementLabel.setBounds(5, 25, 85, 25);
        elementLabel.setVisible(true);
        add(elementLabel);

        elementType = new JComboBox<>(Element.elementTypes);
        elementType.setSelectedIndex(Element.elementTypeIndexOf("" + element.elementType));
        elementType.setBounds(95, 25, 100, 25);
        elementType.setVisible(true);
        add(elementType);

        i1 = new JTextField();
        i2 = new JTextField();
        i3 = new JTextField();
        i4 = new JTextField();

        l1 = new JLabel();
        l2 = new JLabel();
        l3 = new JLabel();
        l4 = new JLabel();

        maskInput = new InputChannelMask(element.imageType);
        maskInput.setValues(element.mask);
        maskInput.add(this);

        minValue = new InputField();
        minValue.label.setText("Min Value:");
        minValue.label.setSize(60, 25);
        minValue.inputField.setText("" + element.minimum);
        minValue.inputField.setSize(35, 25);
        minValue.add(this);
        maxValue = new InputField();
        maxValue.label.setText("Max Value:");
        maxValue.label.setSize(65, 25);
        maxValue.inputField.setText("" + element.maximum);
        maxValue.inputField.setSize(35, 25);
        maxValue.add(this);
        minDist = new InputField();
        minDist.label.setText("Min Dist:");
        minDist.label.setSize(51, 25);
        minDist.inputField.setText("" + element.minimumDist);
        minDist.inputField.setSize(35, 25);
        minDist.add(this);
        maxDist = new InputField();
        maxDist.label.setText("Max Dist:");
        maxDist.label.setSize(56, 25);
        maxDist.inputField.setText("" + element.maximumDist);
        maxDist.inputField.setSize(35, 25);
        maxDist.add(this);

        growthManager = new JButton();
        growthManager.setText("Growth Manager");
        growthManager.setSize(150, 35);
        growthManager.addActionListener(this);
        add(growthManager);
    }

    @Override
    public void process() {

        ElementType newElementType = Element.parseElementType((String) elementType.getSelectedItem());
        if (element.elementType != newElementType) {
            Element newElement = Element.getElementForType(image.imageType, newElementType);
            newElement.addGrowths(element.growths.values);
            image.elements.replaceValue(element, newElement);
            element = newElement;
            switchState(newElementType);
            image.elements.getButton(element).setText("" + element.elementType);
            repaint();
            return;
        }

        double newR, newB, newG;
        double newMinValue, newMaxValue;
        double newMinDist, newMaxDist;

        try {
            switch (image.imageType) {
                case RGB:
                    newR = Double.parseDouble(maskInput.r.inputField.getText());
                    newB = Double.parseDouble(maskInput.b.inputField.getText());
                    newG = Double.parseDouble(maskInput.g.inputField.getText());

                    if (newR <= 1 && newR >= 0 && newG <= 1 && newG >= 0 && newB <= 1 && newB >= 0) {
                        element.mask.values[0] = newR;
                        element.mask.values[1] = newG;
                        element.mask.values[2] = newB;
                    }
                    break;
                case Greyscale:
                    newR = Double.parseDouble(maskInput.r.inputField.getText());

                    if (newR <= 1 && newR >= 0) {
                        element.mask.values[0] = newR;
                    }
                    break;
            }

            newMinValue = Double.parseDouble(minValue.inputField.getText());
            newMaxValue = Double.parseDouble(maxValue.inputField.getText());

            element.minimum = newMinValue;
            element.maximum = newMaxValue;

            newMinDist = Double.parseDouble(minDist.inputField.getText());
            newMaxDist = Double.parseDouble(maxDist.inputField.getText());

            element.minimumDist = newMinDist;
            element.maximumDist = newMaxDist;
        } catch (Exception e) {
            return;
        }

        try {
            double newX = Double.parseDouble(i1.getText());
            double newY = Double.parseDouble(i2.getText());

            switch (element.elementType) {
                case Point:
                    Point point = (Point) element;
                    point.x = newX;
                    point.y = newY;
                    break;
                case Line:
                    double lx2 = Double.parseDouble(i3.getText());
                    double ly2 = Double.parseDouble(i4.getText());

                    Line line = (Line) element;
                    line.x1 = newX;
                    line.y1 = newY;
                    line.x2 = lx2;
                    line.y2 = ly2;
                    break;
                case LineSegment:
                    double lsx2 = Double.parseDouble(i3.getText());
                    double lsy2 = Double.parseDouble(i4.getText());

                    LineSegment lineSegment = (LineSegment) element;
                    lineSegment.x1 = newX;
                    lineSegment.y1 = newY;
                    lineSegment.x2 = lsx2;
                    lineSegment.y2 = lsy2;
                    break;
                case Ray:
                    double newAngle = Double.parseDouble(i3.getText());
                    double newWidth = Double.parseDouble(i4.getText());

                    Ray ray = (Ray) element;
                    ray.x = (int) newX;
                    ray.y = (int) newY;
                    ray.angle = newAngle;
                    ray.width = newWidth;
                    break;
                case Circle:
                    double newRadius = Double.parseDouble(i3.getText());

                    Circle circle = (Circle) element;
                    circle.x = newX;
                    circle.y = newY;
                    circle.radius = newRadius;
                    break;
            }
        } catch (Exception e) {
            return;
        }

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == growthManager) {
            if (growthWindowActive) {
                builder.removeWindow(growthManagerWindow);
            }
            growthManagerWindow = new GrowthManager(builder, image, element);
            growthWindowActive = true;
        }
    }

    public void switchState(ElementType state) {
        exitState(this.state);
        enterState(state);
    }

    public void enterState(ElementType state) {
        switch (state) {
            case Point:
                Point point = (Point) element;
                l1.setText("X:");
                l1.setBounds(10, 60, 15, 25);
                add(l1);

                i1.setText("" + point.x);
                i1.setBounds(25, 60, 35, 25);
                add(i1);

                l2.setText("Y:");
                l2.setBounds(70, 60, 15, 25);
                add(l2);

                i2.setText("" + point.y);
                i2.setBounds(85, 60, 35, 25);
                add(i2);

                // Defaults
                arrangeDefaults(85);
                setSize(210 + widthOffset, 300 + heightOffset);
                break;
            case Line:
                // Point 1
                Line line = (Line) element;
                l1.setText("X1:");
                l1.setBounds(10, 60, 20, 25);
                add(l1);

                i1.setText("" + line.x1);
                i1.setBounds(30, 60, 35, 25);
                add(i1);

                l2.setText("Y1:");
                l2.setBounds(75, 60, 20, 25);
                add(l2);

                i2.setText("" + line.y1);
                i2.setBounds(95, 60, 35, 25);
                add(i2);

                // Point 2
                l3.setText("X2:");
                l3.setBounds(10, 90, 20, 25);
                add(l3);

                i3.setText("" + line.x2);
                i3.setBounds(30, 90, 35, 25);
                add(i3);

                l4.setText("Y2:");
                l4.setBounds(75, 90, 20, 25);
                add(l4);

                i4.setText("" + line.y2);
                i4.setBounds(95, 90, 35, 25);
                add(i4);

                // Defaults
                arrangeDefaults(115);
                setSize(210 + widthOffset, 300 + heightOffset);
                break;
            case LineSegment:
                // Point 1
                LineSegment lineSegment = (LineSegment) element;
                l1.setText("X1:");
                l1.setBounds(10, 60, 20, 25);
                add(l1);

                i1.setText("" + lineSegment.x1);
                i1.setBounds(30, 60, 35, 25);
                add(i1);

                l2.setText("Y1:");
                l2.setBounds(75, 60, 20, 25);
                add(l2);

                i2.setText("" + lineSegment.y1);
                i2.setBounds(95, 60, 35, 25);
                add(i2);

                // Point 2
                l3.setText("X2:");
                l3.setBounds(10, 90, 20, 25);
                add(l3);

                i3.setText("" + lineSegment.x2);
                i3.setBounds(30, 90, 35, 25);
                add(i3);

                l4.setText("Y2:");
                l4.setBounds(75, 90, 20, 25);
                add(l4);

                i4.setText("" + lineSegment.y2);
                i4.setBounds(95, 90, 35, 25);
                add(i4);

                // Defaults
                arrangeDefaults(115);
                setSize(210 + widthOffset, 300 + heightOffset);
                break;
            case Ray:
                Ray ray = (Ray) element;
                l1.setText("X:");
                l1.setBounds(10, 60, 15, 25);
                add(l1);

                i1.setText("" + ray.x);
                i1.setBounds(25, 60, 35, 25);
                add(i1);

                l2.setText("Y:");
                l2.setBounds(70, 60, 15, 25);
                add(l2);

                i2.setText("" + ray.y);
                i2.setBounds(85, 60, 35, 25);
                add(i2);

                l3.setText("Angle:");
                l3.setBounds(10, 90, 40, 25);
                add(l3);

                i3.setText("" + ray.angle);
                i3.setBounds(50, 90, 35, 25);
                add(i3);

                l4.setText("Width:");
                l4.setBounds(95, 90, 40, 25);
                add(l4);

                i4.setText("" + ray.width);
                i4.setBounds(135, 90, 35, 25);
                add(i4);

                // Defaults
                arrangeDefaults(115);
                setSize(210 + widthOffset, 300 + heightOffset);
                break;
            case Circle:
                Circle circle = (Circle) element;
                l1.setText("X:");
                l1.setBounds(10, 60, 15, 25);
                add(l1);

                i1.setText("" + circle.x);
                i1.setBounds(25, 60, 35, 25);
                add(i1);

                l2.setText("Y:");
                l2.setBounds(70, 60, 15, 25);
                add(l2);

                i2.setText("" + circle.y);
                i2.setBounds(85, 60, 35, 25);
                add(i2);

                l3.setText("Radius:");
                l3.setBounds(10, 90, 50, 25);
                add(l3);

                i3.setText("" + circle.radius);
                i3.setBounds(58, 90, 35, 25);
                add(i3);

                // Defaults
                arrangeDefaults(115);
                setSize(210 + widthOffset, 300 + heightOffset);
                break;
        }

        this.state = state;
    }

    public void arrangeDefaults(int y) {
        y += 10;

        maskInput.setPos(5, y);

        y += 55;

        minValue.label.setLocation(5, y);
        minValue.inputField.setLocation(minValue.label.getX() + minValue.label.getWidth(), y);

        maxValue.label.setLocation(minValue.inputField.getX() + minValue.inputField.getWidth() + 5, y);
        maxValue.inputField.setLocation(maxValue.label.getX() + maxValue.label.getWidth(), y);

        y += 30;

        minDist.label.setLocation(5, y);
        minDist.inputField.setLocation(minDist.label.getX() + minDist.label.getWidth(), y);

        maxDist.label.setLocation(minDist.inputField.getX() + minDist.inputField.getWidth() + 5, y);
        maxDist.inputField.setLocation(maxDist.label.getX() + maxDist.label.getWidth(), y);

        y += 40;

        growthManager.setLocation(25, y);

    }

    public void exitState(ElementType state) {
        switch (state) {
            case Point:
                remove(i1);
                remove(i2);

                remove(l1);
                remove(l2);
                break;
            case Line:
            case LineSegment:
            case Ray:
                remove(i1);
                remove(i2);
                remove(i3);
                remove(i4);

                remove(l1);
                remove(l2);
                remove(l3);
                remove(l4);
                break;
            case Circle:
                remove(i1);
                remove(i2);
                remove(i3);

                remove(l1);
                remove(l2);
                remove(l3);
                break;
        }
    }
}
