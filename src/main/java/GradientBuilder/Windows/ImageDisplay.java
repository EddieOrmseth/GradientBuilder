package GradientBuilder.Windows;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Images.Image;

import java.awt.*;

public class ImageDisplay extends Window {

    Image image;

    public ImageDisplay(GradientBuilder builder, Image image) {
        super(WindowType.ImageDisplay, builder);
        this.image = image;
        setBounds(100, 100, image.getWidth() + widthOffset, image.getHeight() + heightOffset);
        setTitle("Image Display");
        add(image);
        image.setBounds(0, 0, image.getWidth(), image.getHeight());
        setVisible(true);
        builder.addWindow(this);
        add(image);
    }

    @Override
    public void process() {
        setSize(image.getWidth() + widthOffset, image.getHeight() + heightOffset);
        image.setBounds(0, 0, image.getWidth() + widthOffset, image.getHeight() + heightOffset);
        image.set();
        repaint();
    }

//    @Override
//    public void paint(Graphics graphics) {
//        graphics.drawImage(image.image, 0, 0, this);
//    }
}
