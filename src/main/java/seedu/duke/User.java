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
        logger.fine("User instance created: name=" + name + ", number=" + number + ", email=" + email);
    }

    public static void userInit() {
        logger.info("Starting interactive user initialisation");
        ui.showMessage("Welcome! What is your name?");
        String name = promptForName();
        ui.showMessage("Next, what is your number?");
        int number = promptForNumber();
        ui.showMessage("Finally, what is your email?");
        String email = promptForEmail();

        instance = new User(name, number, email);
        logger.info("User initialisation complete");
    }

    public static void loadFrom(String name, int number, String email) {
        logger.info("Loading user from storage: name=" + name + ", number=" + number + ", email=" + email);
        instance = new User(name, number, email);
        logger.fine("User instance loaded successfully from storage");
    }

    public static User getInstance() {
        if (instance == null) {
            logger.info("No existing user instance found, starting userInit");
            userInit();
        } else {
            logger.fine("Returning existing user instance for: " + instance.name);
        }
        return instance;
    }

    public void editField(String field, String value) throws ResumakeException {
        logger.fine("editField called: field=\"" + field + "\", value=\"" + value + "\"");
        switch (field.toLowerCase()) {
        case "name":
            if (value == null || value.isBlank()) {
                logger.warning("editField failed: name value is null or blank");
                throw new ResumakeException("Please enter a valid name.");
            }
            logger.fine("Updating name from \"" + this.name + "\" to \"" + value.trim() + "\"");
            this.name = value.trim();
            logger.info("User name updated successfully to: " + this.name);
            break;
        case "number":
            try {
                if (value == null || value.isBlank()) {
                    logger.warning("editField failed: number value is null or blank");
                    throw new NumberFormatException("Number cannot be blank.");
                }
                int parsedNumber = Integer.parseInt(value.trim());
                if (parsedNumber <= 0) {
                    logger.warning("editField failed: number is not positive: " + parsedNumber);
                    throw new NumberFormatException("Number must be positive.");
                }
                logger.fine("Updating number from " + this.number + " to " + parsedNumber);
                this.number = parsedNumber;
                logger.info("User number updated successfully to: " + this.number);
            } catch (NumberFormatException e) {
                logger.warning("editField failed: invalid number input \"" + value + "\": " + e.getMessage());
                throw new ResumakeException("Please enter a valid number.");
            }
            break;
        case "email":
            if (!isValidEmail(value)) {
                logger.warning("editField failed: invalid email format \"" + value + "\"");
                throw new ResumakeException("Please enter a valid email.");
            }
            logger.fine("Updating email from \"" + this.email + "\" to \"" + value.trim() + "\"");
            this.email = value.trim();
            logger.info("User email updated successfully to: " + this.email);
            break;
        default:
            logger.warning("editField failed: unknown field \"" + field + "\"");
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
        logger.info("Retrieving skills as string, skill count: " + this.skills.size());
        if (this.skills.isEmpty()) {
            logger.fine("No skills present, returning placeholder message");
            return "No skills added yet.";
        }
        String result = String.join(", ", this.skills.keySet());
        logger.fine("Skills retrieved: " + result);
        return result;
    }

    /**
     * Adds a skill to the user's skill map.
     * If the skill already exists, its count is incremented by one.
     * Surrounding quotation marks are removed before storing.
     *
     * @param skill The skill to be added.
     */
    public void addSkills(String skill) {
        if (skill == null) {
            logger.warning("addSkills called with null skill, ignoring");
            return;
        }
        skill = skill.toLowerCase();
        skill = removeQuotes(skill);
        if (skill.isBlank()) {
            logger.warning("addSkills called with blank skill after normalisation, ignoring");
            return;
        }
        if (this.skills.containsKey(skill)) {
            int newcount = this.skills.get(skill) + 1;
            this.skills.put(skill, newcount);
            logger.fine("Skill \"" + skill + "\" already present, incremented count to " + newcount);
        } else {
            this.skills.put(skill, 1);
            logger.info("New skill added: \"" + skill + "\"");
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
        if (skill == null) {
            logger.warning("removeSkills called with null skill, ignoring");
            return;
        }
        skill = skill.toLowerCase();
        skill = removeQuotes(skill);
        if (skill.isBlank()) {
            logger.warning("removeSkills called with blank skill after normalisation, ignoring");
            return;
        }
        if (!this.skills.containsKey(skill)) {
            logger.fine("removeSkills: skill \"" + skill + "\" not found in map, no action taken");
            return;
        }

        int newcount = this.skills.get(skill) - 1;
        if (newcount == 0) {
            this.skills.remove(skill);
            logger.info("Skill \"" + skill + "\" fully removed (count reached zero)");
        } else {
            this.skills.put(skill, newcount);
            logger.fine("Skill \"" + skill + "\" count decremented to " + newcount);
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
        String original = skill;

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

        if (!skill.equals(original)) {
            logger.fine("removeQuotes: stripped quotes from \"" + original + "\" -> \"" + skill + "\"");
        }

        return skill;
    }

    public static String promptForName() {
        logger.fine("Prompting user for name");
        while (true) {
            String name = ui.readCommand().trim();
            if (!name.isBlank()) {
                logger.fine("Valid name received: " + name);
                return name;
            }
            logger.warning("promptForName: blank name entered, re-prompting");
            ui.showMessage("Please enter a valid name.");
        }
    }

    public static int promptForNumber() {
        logger.fine("Prompting user for number");
        while (true) {
            String numberInput = ui.readCommand().trim();
            try {
                int number = Integer.parseInt(numberInput);
                if (number <= 0) {
                    logger.warning("promptForNumber: non-positive number entered: " + number);
                    throw new NumberFormatException("Number must be positive.");
                }
                logger.fine("Valid number received: " + number);
                return number;
            } catch (NumberFormatException e) {
                logger.warning("promptForNumber: invalid input \"" + numberInput + "\": " + e.getMessage());
                ui.showMessage("Please enter a valid number.");
            }
        }
    }

    public static String promptForEmail() {
        logger.fine("Prompting user for email");
        while (true) {
            String email = ui.readCommand().trim();
            if (isValidEmail(email)) {
                logger.fine("Valid email received: " + email);
                return email;
            }
            logger.warning("promptForEmail: invalid email format entered: \"" + email + "\"");
            ui.showMessage("Please enter a valid email.");
        }
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            logger.fine("isValidEmail: null or blank input, returning false");
            return false;
        }
        String normalized = email.trim();
        boolean valid = normalized.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
        logger.fine("isValidEmail: \"" + normalized + "\" -> " + valid);
        return valid;
    }

}
