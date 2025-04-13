package HangmanGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HangmanGame extends JFrame {
    // List of words to guess
    private String[] words = {"elephant", "tiger", "computer", "notebook", "internet"};
    private String selectedWord;
    private StringBuilder hiddenWord;
    private int attemptsLeft = 6;
    private JButton restartButton;
    private JComboBox<String> difficultySelector;
    private int maxAttempts = 6;

    // UI components
    private JLabel wordLabel, statusLabel;
    private JTextField inputField;
    private JButton guessButton;
    private HangmanPanel hangmanPanel;

    public HangmanGame() {
        createUI();   // Build the interface
        setupGame();  // Initialize the game
    }

    // Chooses a random word and sets up the hidden word display
    private void setupGame() {
        Random rand = new Random();
        selectedWord = words[rand.nextInt(words.length)];
        hiddenWord = new StringBuilder("_".repeat(selectedWord.length()));
        setAttemptsBasedOnDifficulty();  // Set attempts based on selected difficulty
    }

    // Adjust maxAttempts depending on difficulty level
    private void setAttemptsBasedOnDifficulty() {
        String selected = (String) difficultySelector.getSelectedItem();
        if (selected != null) {
            if (selected.contains("Easy")) maxAttempts = 8;
            else if (selected.contains("Medium")) maxAttempts = 6;
            else maxAttempts = 4;
        }
        attemptsLeft = maxAttempts;
    }

    // Resets the game when Restart button is clicked
    private void restartGame() {
        setupGame();
        wordLabel.setText(hiddenWord.toString());
        statusLabel.setText("Attempts left: " + attemptsLeft);
        inputField.setText("");
        hangmanPanel.repaint();
    }

    // Builds the full user interface
    private void createUI() {
        setTitle("Hangman Game");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Custom panel with background image
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("src/HangmanGUI/background.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());

        // Left panel: Hangman drawing
        hangmanPanel = new HangmanPanel();
        hangmanPanel.setPreferredSize(new Dimension(300, 400));
        hangmanPanel.setOpaque(false);

        // Top: Word to guess
        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Serif", Font.BOLD, 40));
        wordLabel.setForeground(Color.WHITE);
        wordLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));

        // Bottom: Attempts left
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        statusLabel.setForeground(Color.YELLOW);

        // Input and guess button
        inputField = new JTextField(10);
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        guessButton = new JButton("Guess Letter");
        JButton fullWordButton = new JButton("Guess Word");

        // Style guess buttons
        guessButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        fullWordButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        guessButton.setBackground(Color.GREEN);
        guessButton.setForeground(Color.WHITE);
        fullWordButton.setBackground(Color.GREEN);
        fullWordButton.setForeground(Color.WHITE);

        // Restart and difficulty selection
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        restartButton.setBackground(Color.RED);
        restartButton.setForeground(Color.WHITE);

        String[] levels = {"Easy (8)", "Medium (6)", "Hard (4)"};
        difficultySelector = new JComboBox<>(levels);
        difficultySelector.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JLabel inputLabel = new JLabel("Enter letter/word:", SwingConstants.CENTER);
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        inputLabel.setForeground(Color.WHITE);

        // Right center panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new GridLayout(5, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(guessButton);
        inputPanel.add(fullWordButton);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.add(inputPanel, BorderLayout.CENTER);

        // Bottom panel for difficulty and restart
        JLabel difficultyLabel = new JLabel("Difficulty:");
        difficultyLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        difficultyLabel.setForeground(Color.WHITE);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // moves panel a little higher
        bottomPanel.add(difficultyLabel);
        bottomPanel.add(difficultySelector);
        bottomPanel.add(restartButton);

        rightPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Add components to background layout
        backgroundPanel.add(wordLabel, BorderLayout.NORTH);
        backgroundPanel.add(hangmanPanel, BorderLayout.WEST);
        backgroundPanel.add(rightPanel, BorderLayout.EAST);
        backgroundPanel.add(statusLabel, BorderLayout.SOUTH);

        // Button actions
        guessButton.addActionListener(e -> makeGuess(false));
        fullWordButton.addActionListener(e -> makeGuess(true));
        restartButton.addActionListener(e -> restartGame());

        setVisible(true); // Show the window
    }

    // Main game logic: guessing a letter or full word
    private void makeGuess(boolean isFullWord) {
        String input = inputField.getText().toLowerCase().trim();
        inputField.setText(""); // Clear input

        if (isFullWord) {
            // Full word guess
            if (input.equals(selectedWord)) {
                wordLabel.setText(selectedWord);
                JOptionPane.showMessageDialog(this, "Correct! You win!");
                System.exit(0);
            } else {
                attemptsLeft--;
                statusLabel.setText("Attempts left: " + attemptsLeft);
            }
        } else {
            // Letter guess
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                JOptionPane.showMessageDialog(this, "Enter a single valid letter.");
                return;
            }

            char guessedChar = input.charAt(0);
            boolean correctGuess = false;

            for (int i = 0; i < selectedWord.length(); i++) {
                if (selectedWord.charAt(i) == guessedChar && hiddenWord.charAt(i) == '_') {
                    hiddenWord.setCharAt(i, guessedChar);
                    correctGuess = true;
                }
            }

            if (!correctGuess) {
                attemptsLeft--;
            }

            wordLabel.setText(hiddenWord.toString());
            statusLabel.setText("Attempts left: " + attemptsLeft);
        }

        hangmanPanel.repaint();

        // Win or lose conditions
        if (hiddenWord.toString().equals(selectedWord)) {
            JOptionPane.showMessageDialog(this, "You win! The word was: " + selectedWord);
            System.exit(0);
        } else if (attemptsLeft == 0) {
            JOptionPane.showMessageDialog(this, "You lost! The word was: " + selectedWord);
            System.exit(0);
        }
    }

    // Drawing logic for hangman figure
    class HangmanPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            g.drawLine(50, 250, 150, 250); // Base
            g.drawLine(100, 250, 100, 50); // Pole
            g.drawLine(100, 50, 200, 50);  // Top bar
            g.drawLine(200, 50, 200, 80);  // Rope

            if (attemptsLeft <= 5) g.drawOval(180, 80, 40, 40);       // Head
            if (attemptsLeft <= 4) g.drawLine(200, 120, 200, 170);    // Body
            if (attemptsLeft <= 3) g.drawLine(200, 130, 170, 150);    // Left arm
            if (attemptsLeft <= 2) g.drawLine(200, 130, 230, 150);    // Right arm
            if (attemptsLeft <= 1) g.drawLine(200, 170, 170, 210);    // Left leg
            if (attemptsLeft <= 0) g.drawLine(200, 170, 230, 210);    // Right leg
        }
    }

    public static void main(String[] args) {
        new HangmanGame(); // Launch the game
    }
}
