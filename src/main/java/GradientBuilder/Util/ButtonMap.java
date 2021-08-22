package GradientBuilder.Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonMap<T> extends Component {

    public ArrayList<T> values;
    public ArrayList<JButton> buttons;

    public ArrayList<T> toBeRemoved;

    public ButtonMap() {
        values = new ArrayList<>();
        buttons = new ArrayList<>();
    }

    public void addValue(T value) {
        addPair(value, new JButton());
    }

    public void addPair(T value, JButton button) {
        values.add(value);
        buttons.add(button);
    }

    public void removePair(T value) {
        int index = indexOf(value);
        if (index == -1) {
            return;
        }
        values.remove(index);
        buttons.remove(index);
    }

    public void removePair(JButton button) {
        int index = indexOf(button);
        if (index == -1) {
            return;
        }
        values.remove(index);
        buttons.remove(index);
    }

    public int indexOf(T value) {
        for (int i = 0; i < values.size(); i++) {
            if (value == values.get(i)) {
                return i;
            }
        }

        return -1;
    }

    public int indexOf(JButton button) {
        for (int i = 0; i < buttons.size(); i++) {
            if (button == buttons.get(i)) {
                return i;
            }
        }

        return -1;
    }

    public T getValue(JButton button) {
        return values.get(indexOf(button));
    }

    public T getValue(int index) {
        return values.get(index);
    }

    public JButton getButton(T value) {
        return buttons.get(indexOf(value));
    }

    public JButton getButton(int index) {
        return buttons.get(index);
    }

    @Override
    public void paint(Graphics graphics) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).paint(graphics);
        }
    }

    public void replaceValue(T oldValue, T newValue) {
        values.set(indexOf(oldValue), newValue);
    }

}
