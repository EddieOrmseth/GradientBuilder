package GradientBuilder.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyBoardListener implements KeyEventDispatcher {

    public static ArrayList<Integer> keyCodes;
    public static ArrayList<Boolean> keyValues;

    private KeyBoardListener() {

    }

    public static void initialize() {
        keyCodes = new ArrayList<>();
        keyValues = new ArrayList<>();
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyBoardListener());
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (keyCodes.contains(e.getKeyCode())) {
            setValue(e);
        } else {
            keyCodes.add(e.getKeyCode());
            keyValues.add(true);
        }

        return false;
    }

    private static void setValue(KeyEvent e) {
        int index = indexOf(e.getKeyCode());
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            keyValues.set(index, true);
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            keyValues.set(index, false);
        }
    }

    private static int indexOf(int keyCode) {
        for (int i = 0; i < keyCodes.size(); i++) {
            if (keyCodes.get(i) == keyCode) {
                return i;
            }
        }

        return -1;
    }

    public static boolean get(int keyCode) {
        if (keyCodes.contains(keyCode)) {
            return keyValues.get(indexOf(keyCode));
        } else {
            return false;
        }
    }

}
