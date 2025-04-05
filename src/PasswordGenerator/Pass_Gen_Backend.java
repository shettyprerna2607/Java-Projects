package PasswordGenerator;

import java.util.Random;

public class Pass_Gen_Backend {

    // String containing lowercase alphabets
    public static final String Lowercase_Char = "abcdefghijklmnopqrstuvwxyz";

    // String containing uppercase alphabets
    public static final String Uppercase_Char = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // String containing numbers
    public static final String Numbers = "0123456789";

    // String containing special symbols
    public static final String Special_Symbols = "!@#$%^&*()_-+=[]{};:,.<>/|?";

    // Random class object to generate random numbers
    private final Random random;

    // Constructor to initialize random object
    public Pass_Gen_Backend() {
        random = new Random();
    }

    // Method to generate password based on selected conditions
    public String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeNumbers, boolean includeSpecialSymbol) {

        // StringBuilder to store final generated password
        StringBuilder passBuilder = new StringBuilder();

        // String to store valid characters based on user selection
        String validCharacters = "";

        // Check if uppercase is selected and add to valid characters
        if (includeUppercase)
            validCharacters += Uppercase_Char;

        // Check if lowercase is selected and add to valid characters
        if (includeLowercase)
            validCharacters += Lowercase_Char;

        // Check if numbers are selected and add to valid characters
        if (includeNumbers)
            validCharacters += Numbers;

        // Check if symbols are selected and add to valid characters
        if (includeSpecialSymbol)
            validCharacters += Special_Symbols;

        // Loop to generate random password of given length
        for (int i = 0; i < length; i++) {
            // Get random index number from valid characters
            int randomIndex = random.nextInt(validCharacters.length());

            // Get character from random index
            char randomChar = validCharacters.charAt(randomIndex);

            // Append random character to password
            passBuilder.append(randomChar);
        }

        // Return the final generated password
        return passBuilder.toString();
    }
}

