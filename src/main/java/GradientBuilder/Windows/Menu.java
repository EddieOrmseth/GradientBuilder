package GradientBuilder.Windows;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends Window implements ActionListener {

    JButton newImage;
    JButton saveButton;
    JButton exitButton;

    public Menu(GradientBuilder builder) {
        super(WindowType.Menu, builder);
        setBounds(100, 100, 200 + widthOffset, 250 + heightOffset);
        setTitle("Menu");
        configureButtons();
        addButtons();
        setVisible(true);
        builder.addWindow(this);
    }

    public void configureButtons() {
        newImage = new JButton();
        newImage.setBounds(25, 25, 150, 50);
        newImage.setText("New Image");
        newImage.setVisible(true);
        newImage.addActionListener(this);

        saveButton = new JButton();
        saveButton.setBounds(25, 100, 150, 50);
        saveButton.setText("Save");
        saveButton.setVisible(true);
        saveButton.addActionListener(this);

        exitButton = new JButton();
        exitButton.setBounds(25, 175, 150, 50);
        exitButton.setText("Exit");
        exitButton.setVisible(true);
        exitButton.addActionListener(this);
    }

    public void addButtons() {
        add(newImage);
        add(saveButton);
        add(exitButton);
    }

    @Override
    public void process() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newImage) {
            Image image = new Image(Image.ImageType.Greyscale, 500, 500);
            new ImageDisplay(builder, image);
            new ImageEditor(builder, image);
        } else if (e.getSource() == saveButton) {

        } if (e.getSource() == exitButton) {
            builder.exit();
        }
    }
}
