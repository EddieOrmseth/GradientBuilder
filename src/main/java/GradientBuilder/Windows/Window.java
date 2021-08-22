package GradientBuilder.Windows;

import GradientBuilder.GradientBuilder;
import GradientBuilder.Util.Adapter;

import javax.swing.JFrame;

public abstract class Window extends JFrame {

    public enum WindowType {
        Menu,
        ImageEditor,
        ElementManager,
        ElementEditor,
        GrowthManager,
        GrowthEditor,
        ImageDisplay
    }

    private WindowType windowType;
    public GradientBuilder builder;

    public final int widthOffset = 16, heightOffset = 39;

    public Window(WindowType windowType, GradientBuilder builder) {
        this.windowType = windowType;
        this.builder = builder;
        setLayout(null);
//        builder.addWindow(this);
        addWindowListener(new Adapter(builder, this));
    }

    public abstract void process();

}
