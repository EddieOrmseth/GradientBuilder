package GradientBuilder;

import GradientBuilder.Windows.Menu;
import GradientBuilder.Windows.Window;

import java.util.ArrayList;

public class GradientBuilder {

    volatile private ArrayList<Window> windows;
    volatile private ArrayList<Window> toBeRemoved;
    volatile public boolean running = true;

    public static void main(String[] args) {
        GradientBuilder builder = new GradientBuilder();
    }

    public GradientBuilder() {
        windows = new ArrayList<>();
        toBeRemoved = new ArrayList<>();
        new Menu(this);
        start();
    }

    public void start() {
        while (running) {
            for (int i = 0; i < windows.size(); i++) {
                windows.get(i).process();
            }
            if (windows.size() == 0) {
                exit();
            }
            if (toBeRemoved.size() != 0) {
                for (int i = 0; i < toBeRemoved.size(); i++) {
                    Window window = toBeRemoved.get(i);
                    if (window == null) {
                        continue;
                    }
                    windows.remove(window);
                    toBeRemoved.remove(window);
                    window.dispose();
                    i--;
                }
            }
        }
    }

    public void exit() {
        for (int i = 0; i < windows.size(); i++) {
            windows.get(i).dispose();
        }
        running = false;
        System.exit(0);
    }

    public void addWindow(Window window) {
        windows.add(window);
    }

    public void removeWindow(Window window) {
        toBeRemoved.add(window);
    }

}
