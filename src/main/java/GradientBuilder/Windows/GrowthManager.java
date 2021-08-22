package GradientBuilder.Windows;

import GradientBuilder.Elements.Element;
import GradientBuilder.GradientBuilder;
import GradientBuilder.Growths.Growth;
import GradientBuilder.Growths.Power;
import GradientBuilder.Images.Image;
import GradientBuilder.Images.Image.ImageType;
import GradientBuilder.Util.ButtonMap;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GrowthManager extends Window implements ActionListener, KeyListener {

    Image image;
    Element element;
    ButtonMap<Growth> growths;
    ArrayList<Growth> toBeRemoved;

    JLabel growthsLabel;
    JButton newGrowth;

    ImageType imageType;

    int bufferHeight = 25;
    int buttonHeight = 35;
    int buttonWidth = 150;

    private int previousButtonSize;
    private boolean controlPressed = false;

    public GrowthManager(GradientBuilder builder, Image image, Element element) {
        super(WindowType.GrowthManager, builder);
        this.image = image;
        this.element = element;
        growths = element.growths;
        imageType = image.imageType;
        toBeRemoved = new ArrayList<>();
        setBounds(100, 100, 200 + widthOffset, 300 + heightOffset);
        setTitle("Growth Manager");
        configure();
        addKeyListener(this);
        setVisible(true);
        builder.addWindow(this);
    }

    public void configure() {
        growthsLabel = new JLabel();
        growthsLabel.setText("Growths:");
        growthsLabel.setBounds(25, 10, 150, 10);
        growthsLabel.setVisible(true);
        add(growthsLabel);

        newGrowth = new JButton();
        newGrowth.setText("New Growth");
        newGrowth.addActionListener(this);
        newGrowth.setVisible(true);
        add(newGrowth);

        for (int i = 0; i < growths.buttons.size(); i++) {
            add(growths.buttons.get(i));
            growths.buttons.get(i).addActionListener(this);
        }

        arrange();
    }

    public void arrange() {

        if (growths.buttons.size() != previousButtonSize) {
            for (int i = previousButtonSize; i < growths.buttons.size(); i++) {
                add(growths.buttons.get(i));
                growths.buttons.get(i).addActionListener(this);
            }
            previousButtonSize = growths.buttons.size();
        }

        JButton button;
        int x = 25, y = 30;

        for (int i = 0; i < growths.buttons.size(); i++) {
            button = growths.buttons.get(i);

            button.setBounds(x, y, buttonWidth, buttonHeight);
            y += 10 + bufferHeight;
        }

        newGrowth.setBounds(x, y + 15, buttonWidth, buttonHeight);

        setSize(200 + widthOffset, y + 15 + buttonHeight + heightOffset + 25);
    }

    @Override
    public void process() {
        arrange();
        for (int i = 0; i < toBeRemoved.size(); i++) {
            Growth growth = toBeRemoved.get(i);
            growths.removePair(growth);
            toBeRemoved.remove(growth);
            i--;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGrowth) {
            Growth growth = new Power();
            addGrowth(growth);
            JButton button = element.growths.getButton(growth);
            button.setText("" + growth.growthType);
            add(button);
            growth.activateWindow(builder, image, element);
            return;
        }

        for (int i = 0; i < growths.buttons.size(); i++) {
            if (e.getSource() == growths.buttons.get(i)) {
                if (controlPressed) {
                    growths.removePair(growths.buttons.get(i));
                } else {
                    growths.values.get(i).activateWindow(builder, image, element);
                }
                break;
            }
        }
    }

    public void addGrowth(Growth growth) {
        growths.addValue(growth);
    }

    public void removeGrowth(Growth growth) {
        toBeRemoved.add(growth);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = false;
        }
    }
}
