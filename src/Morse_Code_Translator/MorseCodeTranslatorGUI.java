package Morse_Code_Translator;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// GUI class for Morse Code Translator
public class MorseCodeTranslatorGUI extends JFrame implements KeyListener {

    private MorseCodeController morseCodeController;
    private JTextArea textInputArea, morseCodeArea;

    // Constructor to initialize the GUI
    public MorseCodeTranslatorGUI(){
        super("Morse Code Translator");
        setSize(new Dimension(540, 760));
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#660033"));
        setLocationRelativeTo(null);

        morseCodeController = new MorseCodeController();
        addGuiComponents();
    }

    // Adding GUI components to the frame
    private void addGuiComponents(){

        JLabel titleLabel = new JLabel("Morse Code Translator");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 0, 540, 100);

        JLabel textInputLabel = new JLabel("Text:");
        textInputLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        textInputLabel.setForeground(Color.WHITE);
        textInputLabel.setBounds(20, 100, 200, 30);

        textInputArea = new JTextArea();
        textInputArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        textInputArea.addKeyListener(this);
        textInputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textInputArea.setLineWrap(true);
        textInputArea.setWrapStyleWord(true);

        JScrollPane textInputScroll = new JScrollPane(textInputArea);
        textInputScroll.setBounds(20, 132, 484, 236);

        JLabel morseCodeInputLabel = new JLabel("Morse Code:");
        morseCodeInputLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        morseCodeInputLabel.setForeground(Color.WHITE);
        morseCodeInputLabel.setBounds(20, 390, 200, 30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog", Font.PLAIN, 18));
        morseCodeArea.setEditable(false);
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);
        morseCodeArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane morseCodeScroll = new JScrollPane(morseCodeArea);
        morseCodeScroll.setBounds(20, 430, 484, 236);

        // Button to play Morse code sound
        JButton playSoundButton = new JButton("Play Sound");
        playSoundButton.setBounds(210, 680, 100, 30);
        playSoundButton.setBackground(Color.decode("#E0E6FF"));
        playSoundButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                playSoundButton.setEnabled(false); // disable button while playing

                Thread playMorseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            morseCodeController.playSound(morseCodeMessage);
                        }catch(LineUnavailableException | InterruptedException ex){
                            ex.printStackTrace();
                        }finally{
                            playSoundButton.setEnabled(true); // enable button after playing
                        }
                    }
                });
                playMorseCodeThread.start();
            }
        });

        // Adding all components to the frame
        add(titleLabel);
        add(textInputLabel);
        add(textInputScroll);
        add(morseCodeInputLabel);
        add(morseCodeScroll);
        add(playSoundButton);
    }

    // Key event methods
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    // Trigger Morse code translation on key release
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() != KeyEvent.VK_SHIFT){
            String inputText = textInputArea.getText();
            morseCodeArea.setText(morseCodeController.translateToMorse(inputText));
        }
    }
}
