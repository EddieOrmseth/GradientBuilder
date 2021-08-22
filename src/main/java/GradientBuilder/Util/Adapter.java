package GradientBuilder.Util;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Windows.Window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Adapter extends WindowAdapter {

    GradientBuilder builder;
    Window window;

    public Adapter (GradientBuilder builder, Window window) {
        this.builder = builder;
        this.window = window;
    }

    @Override
    public void windowClosing(WindowEvent event) {
        builder.removeWindow(window);
    }


}
