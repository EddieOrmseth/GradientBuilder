package GradientBuilder.Windows;

import GradientBuilder.Elements.Element;
import GradientBuilder.GradientBuilder;
import GradientBuilder.Growths.*;
import GradientBuilder.Growths.Growth.GrowthType;
import GradientBuilder.Images.Image;
import GradientBuilder.Util.InputField;

import javax.swing.*;

public class GrowthEditor extends Window {

    Image image;
    Element element;
    Growth growth;

    GrowthType state;

    JLabel growthTypeLabel;
    JComboBox<String> growthType;

    InputField f1, f2, f3;
    JLabel trigTypeLabel;
    JComboBox<String> trigType;

    InputField minimum, maximum;
    InputField initialValue;

    public GrowthEditor(GradientBuilder builder, Image image, Element element, Growth growth) {
        super(WindowType.GrowthEditor, builder);
        this.image = image;
        this.element = element;
        this.growth = growth;
        this.state = growth.growthType;
        setTitle("Growth Editor");
        setBounds(100, 100, 225, 300);
        configure();
        enterState(state);
        setVisible(true);
        builder.addWindow(this);
    }

    public void configure() {
        growthTypeLabel = new JLabel();
        growthTypeLabel.setText("GrowthType:");
        growthTypeLabel.setBounds(5, 25, 75, 25);
        growthTypeLabel.setVisible(true);
        add(growthTypeLabel);

        growthType = new JComboBox<>(Growth.growthTypes);
        growthType.setBounds(growthTypeLabel.getX() + growthTypeLabel.getWidth(), 25, 110, 25);
        growthType.setSelectedIndex(Growth.growthTypeIndexOf("" + growth.growthType));
        add(growthType);

        growthType.setVisible(true);
        f1 = new InputField();
        f1.label.setVisible(true);
        f1.inputField.setVisible(true);

        f2 = new InputField();
        f2.label.setVisible(true);
        f2.inputField.setVisible(true);

        f3 = new InputField();
        f3.label.setVisible(true);
        f3.inputField.setVisible(true);

        trigTypeLabel = new JLabel();
        trigTypeLabel.setVisible(true);
        trigType = new JComboBox<>(Trigonometric.trigTypes);
//        trigType.setSelectedIndex(Growth.growthTypeIndexOf("" + growth.growthType));
        trigType.setVisible(true);

        minimum = new InputField();
        minimum.label.setText("Minimum:");
        minimum.label.setSize(60, 25);
        minimum.label.setVisible(true);
        minimum.inputField.setText("" + growth.minValue);
        minimum.inputField.setSize(35, 25);
        minimum.inputField.setVisible(true);
        minimum.add(this);

        maximum = new InputField();
        maximum.label.setSize(60, 25);
        maximum.label.setText("Maximum:");
        maximum.label.setVisible(true);
        maximum.inputField.setText("" + growth.maxValue);
        maximum.inputField.setSize(35, 25);
        maximum.inputField.setVisible(true);
        maximum.add(this);

        initialValue = new InputField();
        initialValue.label.setSize(70, 25);
        initialValue.label.setText("Initial Value:");
        initialValue.label.setVisible(true);
        initialValue.inputField.setSize(35, 25);
        initialValue.inputField.setText("" + growth.initialValue);
        initialValue.inputField.setVisible(true);
        initialValue.add(this);
    }

    @Override
    public void process() {

        GrowthType newGrowthType = Growth.parseGrowth((String) growthType.getSelectedItem());
        if (growth.growthType != newGrowthType) {
            Growth newGrowth = Growth.getGrowthForType(newGrowthType);
            element.growths.replaceValue(growth, newGrowth);
            growth = newGrowth;
            switchState(newGrowthType);
            element.growths.getButton(newGrowth).setText("" + newGrowthType);
            repaint();
        }

        try {

            double newMinValue = Double.parseDouble(minimum.inputField.getText());
            double newMaxValue = Double.parseDouble(maximum.inputField.getText());
            double newInitialValue = Double.parseDouble(initialValue.inputField.getText());

            growth.minValue = newMinValue;
            growth.maxValue = newMaxValue;
            growth.initialValue = newInitialValue;

        } catch (Exception e) {
            return;
        }

        try {

            double v1 = Double.parseDouble(f1.inputField.getText());
            double v2 = Double.parseDouble(f2.inputField.getText());

            switch (state) {
                case Power:
                    Power power = (Power) growth;
                    power.power = v1;
                    power.coefficient = v2;
                    break;
                case Exponential:
                    double exponentCoefficient = Double.parseDouble(f3.inputField.getText());

                    Exponential exponential = (Exponential) growth;
                    exponential.base = v1;
                    exponential.scalar = v2;
                    exponential.exponentCoefficient = exponentCoefficient;
                    break;
                case Logarithmic:
                    double base = Double.parseDouble(f3.inputField.getText());

                    Logarithmic logarithmic = (Logarithmic) growth;
                    logarithmic.coefficient = v1;
                    logarithmic.base = base;
                    break;
                case Trigonometric:

                    break;
            }

        } catch (Exception e) {
            return;
        }

    }

    public void switchState(GrowthType state) {
        exitState(this.state);
        enterState(state);
    }

    public void enterState(GrowthType state) {
        this.state = state;
        switch (state) {
            case Power:
                Power power = (Power) growth;
                f1.label.setBounds(5, 60, 40, 25);
                f1.label.setText("Power:");
                f1.inputField.setBounds(47, 60, 35, 25);
                f1.inputField.setText("" + power.power);
                f1.add(this);

                f2.label.setBounds(87, 60, 70, 25);
                f2.label.setText("Coefficient:");
                f2.inputField.setBounds(155, 60, 35, 25);
                f2.inputField.setText("" + power.coefficient);
                f2.add(this);

                arrangeDefaults(85);
                break;
            case Exponential:
                Exponential exponential = (Exponential) growth;
                f1.label.setBounds(5, 60, 40, 25);
                f1.label.setText("Base:");
                f1.inputField.setBounds(41, 60, 35, 25);
                f1.inputField.setText("" + exponential.base);
                f1.add(this);

                f2.label.setBounds(81, 60, 70, 25);
                f2.label.setText("Scalar:");
                f2.inputField.setBounds(124, 60, 35, 25);
                f2.inputField.setText("" + exponential.scalar);
                f2.add(this);

                f3.label.setBounds(5, 90, 130, 25);
                f3.label.setText("Exponent Coefficient:");
                f3.inputField.setBounds(130, 90, 35, 25);
                f3.inputField.setText("" + exponential.exponentCoefficient);
                f3.add(this);

                arrangeDefaults(115);
                break;
            case Logarithmic:
                Logarithmic logarithmic = (Logarithmic) growth;
                f1.label.setBounds(5, 60, 70, 25);
                f1.label.setText("Coefficient:");
                f1.inputField.setBounds(73, 60, 35, 25);
                f1.inputField.setText("" + logarithmic.coefficient);
                f1.add(this);

                f2.label.setBounds(113, 60, 40, 25);
                f2.label.setText("Base:");
                f2.inputField.setBounds(149, 60, 35, 25);
                f2.inputField.setText("" + logarithmic.base);
                f2.add(this);

                arrangeDefaults(85);
                break;
            case Trigonometric:
                Trigonometric trigonometric = (Trigonometric) growth;

                arrangeDefaults(60);
                break;
        }
    }

    public void arrangeDefaults(int y) {
        y += 10;

        minimum.label.setLocation(5, y);
        minimum.inputField.setLocation(64, y);

        maximum.label.setLocation(104, y);
        maximum.inputField.setLocation(166, y);

        y += 30;

        initialValue.label.setLocation(5, y);
        initialValue.inputField.setLocation(76, y);
    }

    public void exitState(GrowthType state) {
        switch (state) {
            case Power:
                f1.remove(this);
                f2.remove(this);
                break;
            case Exponential:
                f1.remove(this);
                f2.remove(this);
                f3.remove(this);
                break;
            case Logarithmic:
                f1.remove(this);
                f2.remove(this);
                break;
            case Trigonometric:

                break;
        }
    }
}
