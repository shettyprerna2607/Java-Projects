package PasswordGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Main class extending JFrame to create GUI Window
public class Password_Generator extends JFrame {

    private Pass_Gen_Backend passwordGenerator; // Object for backend password generation logic

    // Constructor
    public Password_Generator(){
        super("Password Generator"); // Set window title
        setSize(540,570); // Set window size
        setResizable(false); // Window not resizable
        setLayout(null); // No layout manager (Absolute Positioning)
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close window on exit
        setLocationRelativeTo(null); // Center the window
        passwordGenerator = new Pass_Gen_Backend(); // Initialize backend object
        addGuiComponents(); // Call method to add GUI components
    }

    // Method to add GUI components
    private void addGuiComponents(){

        // Title Label
        JLabel titlelabel = new JLabel("Password Generator");
        titlelabel.setFont(new Font("Dialog",Font.BOLD,32));
        titlelabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlelabel.setBounds(0,10,540,39);
        add(titlelabel);

        // TextArea for displaying generated password
        JTextArea passOutput = new JTextArea();
        passOutput.setEditable(false); // Not editable
        passOutput.setFont(new Font("Dialog",Font.BOLD,32));

        // ScrollPane for password output
        JScrollPane passOutputpane = new JScrollPane(passOutput);
        passOutputpane.setBounds(25,97,479,70);
        passOutputpane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(passOutputpane);

        // Label for Password Length input
        JLabel passLenLabel = new JLabel("Password Length :");
        passLenLabel.setFont(new Font("Dialog",Font.PLAIN,32));
        passLenLabel.setBounds(25,215,272,39);
        add(passLenLabel);

        // TextArea to input password length
        JTextArea passLenInputArea = new JTextArea();
        passLenInputArea.setFont(new Font("Dialog",Font.PLAIN,32));
        passLenInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passLenInputArea.setBounds(310,215,192,39);
        add(passLenInputArea);

        // Toggle Buttons for selecting password options
        JToggleButton uppercaseToggle = new JToggleButton("Uppercase");
        uppercaseToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        uppercaseToggle.setBackground(Color.GREEN);
        uppercaseToggle.setBounds(25,302,225,54);
        add(uppercaseToggle);

        JToggleButton lowercaseToggle = new JToggleButton("Lowercase");
        lowercaseToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        lowercaseToggle.setBackground(Color.GREEN);
        lowercaseToggle.setBounds(282,302,225,56);
        add(lowercaseToggle);

        JToggleButton numsToggle = new JToggleButton("Numbers");
        numsToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        numsToggle.setBackground(Color.GREEN);
        numsToggle.setBounds(25,373,225,56);
        add(numsToggle);

        JToggleButton symbolsToggle = new JToggleButton("Symbols");
        symbolsToggle.setFont(new Font("Dialog",Font.PLAIN,26));
        symbolsToggle.setBackground(Color.GREEN);
        symbolsToggle.setBounds(282,373,225,54);
        add(symbolsToggle);

        // Generate Button to generate password
        JButton generateBtn = new JButton("Generate");
        generateBtn.setFont(new Font("Dialog",Font.PLAIN,32));
        generateBtn.setBounds(155,477,222,41);
        generateBtn.setBackground(Color.BLUE);

        // Action Listener for Generate Button
        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if length is provided
                if(passLenInputArea.getText().length() <= 0)
                    return;

                // Check if any toggle option is selected
                boolean anyToggleSelected = lowercaseToggle.isSelected() || uppercaseToggle.isSelected() || numsToggle.isSelected() || symbolsToggle.isSelected();

                int passowrdLength = Integer.parseInt(passLenInputArea.getText()); // Get password length

                if (anyToggleSelected){
                    // Generate password using backend class
                    String generatePass = passwordGenerator.generatePassword(passowrdLength,
                            uppercaseToggle.isSelected(),
                            lowercaseToggle.isSelected(),
                            numsToggle.isSelected(),
                            symbolsToggle.isSelected());

                    passOutput.setText(generatePass); // Display generated password
                }

            }
        });

        add(generateBtn); // Add generate button to window
    }

}

