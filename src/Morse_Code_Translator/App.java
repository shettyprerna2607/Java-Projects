package Morse_Code_Translator;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        // Create and display the GUI window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MorseCodeTranslatorGUI().setVisible(true);
            }
        });
    }
}

