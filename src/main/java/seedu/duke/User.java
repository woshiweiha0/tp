package seedu.duke;

import java.util.HashMap;
import java.util.logging.Logger;

import seedu.duke.exceptions.ResumakeException;

/**
 * Represents the user's personal details in the application.
 *
 * <p>This class stores the user's name, phone number, and email address,
 * and provides methods to initialize, load, retrieve, and edit these fields.
 * It follows the singleton pattern so that only one user instance exists
 * throughout the program.
 *
 * <p>The class also includes input validation for editable fields and
 * interactive prompting methods for first-time user initialization.
 */

public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());
    private static final Ui ui = new Ui();
    private static User instance;

    private final HashMap<String, Integer> skills;
    private String name;
    private int number;
    private String email;

    private User(String name, int number, String email) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.skills = new HashMap<>();
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
                if (value == null || value.isBlank()) {
                    throw new NumberFormatException("Number cannot be blank");
                }
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

    /**
     * Returns all current skills as a comma-separated string.
     *
     * @return A string containing all current skills, or a message if no skills exist.
     */
    public String getSkillsAsString() {
        logger.info("Printing Skills");
        if (this.skills.isEmpty()) {
            return "No skills added yet.";
        }
        return String.join(", ", this.skills.keySet());
    }

    /**
     * Adds a skill to the user's skill map.
     * If the skill already exists, its count is incremented by one.
     * Surrounding quotation marks are removed before storing.
     *
     * @param skill The skill to be added.
     */
    public void addSkills(String skill) {
        logger.info("Adding skill: " + skill);
        skill = removeQuotes(skill);
        if (this.skills.containsKey(skill)) {
            int newcount = this.skills.get(skill) + 1;
            this.skills.put(skill, newcount);
        } else {
            this.skills.put(skill, 1);
        }
    }

    /**
     * Removes one occurrence of a skill from the user's skill map.
     * If the skill count becomes zero, the skill is removed entirely.
     * If the skill does not exist, no action is taken.
     * Surrounding quotation marks are removed before processing.
     *
     * @param skill The skill to be removed.
     */
    public void removeSkills(String skill){
        logger.info("Removing skill: " + skill);
        skill = removeQuotes(skill);
        if (!this.skills.containsKey(skill)) {
            return;
        }

        int newcount = this.skills.get(skill) - 1;
        if (newcount == 0) {
            this.skills.remove(skill);
        } else {
            this.skills.put(skill, newcount);
        }
    }

    /**
     * Removes matching surrounding quotation marks from a skill string.
     * Supports double quotes, curly quotes, and single quotes.
     *
     * @param skill The skill string to be cleaned.
     * @return The cleaned skill string without surrounding quotation marks,
     *         or null if the input is null.
     */
    private static String removeQuotes(String skill) {
        if (skill == null) {
            return null;
        }

        skill = skill.trim();

        while (skill.length() >= 2) {
            char first = skill.charAt(0);
            char last = skill.charAt(skill.length() - 1);

            boolean isWrapped =
                    (first == '"' && last == '"') ||
                            (first == '\u201C' && last == '\u201D') ||
                            (first == '\'' && last == '\'');

            if (!isWrapped) {
                break;
            }

            skill = skill.substring(1, skill.length() - 1).trim();
        }

        return skill;
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
