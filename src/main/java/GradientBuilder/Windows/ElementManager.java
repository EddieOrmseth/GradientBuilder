package GradientBuilder.Windows;

import GradientBuilder.Elements.Element;
import GradientBuilder.Elements.Point;
import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;
import GradientBuilder.Images.Image.ImageType;
import GradientBuilder.Util.ButtonMap;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ElementManager extends Window implements ActionListener, KeyListener {

    Image image;
    ButtonMap<Element> elements;
    ArrayList<Element> toBeRemoved;

    JLabel elementsLabel;
    JButton newElement;

    ImageType imageType;

    int bufferHeight = 25;
    int buttonHeight = 35;
    int buttonWidth = 150;

    private int previousButtonSize;
    private boolean controlPressed = false;

    public ElementManager(GradientBuilder builder, Image image) {
        super(WindowType.ElementManager, builder);
        this.image = image;
        elements = image.elements;
        imageType = image.imageType;
        toBeRemoved = new ArrayList<>();
        setBounds(100, 100, 200 + widthOffset, 300 + heightOffset);
        setTitle("Element Manager");
        configure();
        addKeyListener(this);
        setVisible(true);
        builder.addWindow(this);
    }

    public void configure() {
        elementsLabel = new JLabel();
        elementsLabel.setText("Elements:");
        elementsLabel.setBounds(25, 10, 150, 10);
        elementsLabel.setVisible(true);
        add(elementsLabel);

        newElement = new JButton();
        newElement.setText("New Element");
        newElement.addActionListener(this);
        newElement.setVisible(true);
        add(newElement);

        for (int i = 0; i < elements.buttons.size(); i++) {
            add(elements.buttons.get(i));
            elements.buttons.get(i).addActionListener(this);
        }

        arrange();
    }

    public void arrange() {

        if (elements.buttons.size() != previousButtonSize) {
            for (int i = previousButtonSize; i < elements.buttons.size(); i++) {
                add(elements.buttons.get(i));
                elements.buttons.get(i).addActionListener(this);
            }
            previousButtonSize = elements.buttons.size();
        }

        JButton button;
        int x = 25, y = 30;

        for (int i = 0; i < elements.buttons.size(); i++) {
            button = elements.buttons.get(i);

            button.setBounds(x, y, buttonWidth, buttonHeight);
            y += 10 + bufferHeight;
        }

        newElement.setBounds(x, y + 15, buttonWidth, buttonHeight);

        setSize(200 + widthOffset, y + 15 + buttonHeight + heightOffset + 25);
    }

    @Override
    public void process() {
        arrange();
        for (int i = 0; i < toBeRemoved.size(); i++) {
            Element element = toBeRemoved.get(i);
            elements.removePair(element);
            toBeRemoved.remove(element);
            i--;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newElement) {
            Element element = new Point(imageType);
            addElement(element);
            JButton button = image.elements.getButton(element);
            button.setText("" + element.elementType);
            add(button);
            element.activateWindow(builder, image);
            return;
        }

        for (int i = 0; i < elements.buttons.size(); i++) {
            if (e.getSource() == elements.buttons.get(i)) {
                if (controlPressed) {
                    elements.removePair(elements.buttons.get(i));
                } else {
                    elements.values.get(i).activateWindow(builder, image);
                }
                break;
            }
        }
    }

    public void addElement(Element element) {
        elements.addValue(element);
    }

    public void removeElement(Element element) {
        toBeRemoved.add(element);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = true;
            System.out.println("Control Key Pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = false;
            System.out.println("Control Key Released");
        }
        System.out.println("Control Key Released");
    }
}
