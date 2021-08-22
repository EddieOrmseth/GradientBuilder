package GradientBuilder.Windows;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;
import GradientBuilder.Images.Image.ImageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageEditor extends Window implements ActionListener {

    JLabel widthLabel, heightLabel;
    JTextField width, height;
    JLabel imageTypeLabel;
    JComboBox<String> imageType;
    JButton elementManager;

    Image image;

    boolean elementWindowActive = false;
    ElementManager elementManagerWindow;

    public ImageEditor(GradientBuilder builder, Image image) {
        super(WindowType.ImageEditor, builder);
        this.image = image;
        setBounds(100, 100, 200 + widthOffset, 195 + heightOffset);
        setTitle("Image Editor");
        configure();
        setVisible(true);
        builder.addWindow(this);
    }

    public void configure() {
        widthLabel = new JLabel();
        widthLabel.setText("Width:");
        widthLabel.setBounds(10, 25, 40, 25);
        widthLabel.setVisible(true);
        add(widthLabel);

        width = new JTextField();
        width.addActionListener(this);
        width.setText("" + image.getWidth());
        width.setBounds(50, 25, 35, 25);
        width.setVisible(true);
        add(width);

        heightLabel = new JLabel();
        heightLabel.setText("Height:");
        heightLabel.setBounds(90, 25, 50, 25);
        heightLabel.setVisible(true);
        add(heightLabel);

        height = new JTextField();
        height.addActionListener(this);
        height.setText("" + image.getHeight());
        height.setBounds(135, 25, 35, 25);
        height.setVisible(true);
        add(height);

        imageTypeLabel = new JLabel();
        imageTypeLabel.setText("ImageType:");
        imageTypeLabel.setBounds(8, 80, 70, 25);
        imageTypeLabel.setVisible(true);
        add(imageTypeLabel);

        imageType = new JComboBox<>(Image.imageTypes);
        imageType.setSelectedIndex(Image.imageTypeIndexOf("" + image.imageType));
        imageType.addActionListener(this);
        imageType.setBounds(80, 80, 110, 25);
        imageType.setVisible(true);
        add(imageType);

        elementManager = new JButton();
        elementManager.setText("Element Manager");
        elementManager.addActionListener(this);
        elementManager.setBounds(25, 135, 150, 35);
        elementManager.setVisible(true);
        add(elementManager);
    }

    @Override
    public void process() {

        int newWidth, newHeight;
        ImageType newImageType;

        try {
            newWidth = Integer.parseInt(width.getText());
            newHeight = Integer.parseInt(height.getText());
            newImageType = Image.parseImageType((String) imageType.getSelectedItem());
        } catch (Exception e) {
            return;
        }

        if (newWidth <= 0 || newHeight <= 0) {
            return;
        }

        if (image.elements.values.size() == 0) {
            if (newWidth != image.getWidth() || newHeight != image.getHeight() || newImageType != image.imageType) {
                image.realloc(newImageType, newWidth, newHeight);
            }
        } else {
            if (newWidth != image.getWidth() || newHeight != image.getHeight()) {
                image.realloc(image.imageType, newWidth, newHeight);
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == elementManager) {
            if (elementWindowActive) { // What If We Close Window Manually First?
                builder.removeWindow(elementManagerWindow);
            }
            elementManagerWindow = new ElementManager(builder, image);
            elementWindowActive = true;
        }
    }
}
