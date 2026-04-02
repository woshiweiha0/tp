package seedu.duke;

import seedu.duke.exceptions.ResumakeException;

public class User {
    private static User instance;
    private static final Ui ui = new Ui();

    private String name;
    private int number;
    private String email;

    private User(String name, int number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public static void userInit() {
        String name = promptForName();
        int number = promptForNumber();
        String email = promptForEmail();

        instance = new User(name, number, email);
    }

    public static void loadFrom(String name, int number, String email) {
        instance = new User(name, number, email);
    }

    public static User getInstance() {
        if (instance == null) {
            userInit();
        }
        return instance;
    }

    public void editField(String field, String value) throws ResumakeException {
        switch (field.toLowerCase()) {
        case "name":
            if (value == null || value.isBlank()) {
                throw new ResumakeException("Please enter a valid name.");
            }
            this.name = value.trim();
            break;
        case "number":
            try {
                int parsedNumber = Integer.parseInt(value.trim());
                if (parsedNumber <= 0) {
                    throw new NumberFormatException("Number must be positive");
                }
                this.number = parsedNumber;
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please enter a valid number.");
            }
            break;
        case "email":
            if (!isValidEmail(value)) {
                throw new ResumakeException("Please enter a valid email.");
            }
            this.email = value.trim();
            break;
        default:
            throw new ResumakeException("Unknown field: " + field
                    + ". Use name, number, or email.");
        }
    }

    public String getName(){
        return this.name;
    }

    public Integer getNumber(){
        return this.number;
    }

    public String getEmail(){
        return this.email;
    }

    private static String promptForName() {
        while (true) {
            ui.showMessage("Hello, what is your name?");
            String name = ui.readCommand().trim();
            if (!name.isBlank()) {
                return name;
            }
            ui.showMessage("Please enter a valid name.");
        }
    }

    private static int promptForNumber() {
        while (true) {
            ui.showMessage("Hello what is your number?");
            String numberInput = ui.readCommand().trim();
            try {
                int number = Integer.parseInt(numberInput);
                if (number <= 0) {
                    throw new NumberFormatException("Number must be positive");
                }
                return number;
            } catch (NumberFormatException e) {
                ui.showMessage("Please enter a valid number.");
            }
        }
    }

    private static String promptForEmail() {
        while (true) {
            ui.showMessage("Finally, what is your email?");
            String email = ui.readCommand().trim();
            if (isValidEmail(email)) {
                return email;
            }
            ui.showMessage("Please enter a valid email.");
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String normalized = email.trim();
        return normalized.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }
}
