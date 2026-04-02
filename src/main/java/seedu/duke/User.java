package seedu.duke;

import java.util.*;

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
        Scanner sc = new Scanner(System.in);
        ui.showMessage("Hello, what is your name?");
        String name = sc.nextLine();
        ui.showMessage("Hello what is your number?");
        int number = 0;
        try {
            number = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e){
            ui.showMessage("Please enter a number");
        }
        ui.showMessage("Finally, what is your email?");
        String email = sc.nextLine();

        instance = new User(name, number, email);
    }

    public static void loadFrom(String name, int number, String email) {
        instance = new User(name, number, email);
    }

    public static User getInstance() {
        if (instance == null) {
            ui.showMessage("You have not been registered.");
            userInit();
        }
        return instance;
    }

    public void editField(String field, String value) throws ResumakeException {
        switch (field.toLowerCase()) {
        case "name":
            this.name = value;
            break;
        case "number":
            try {
                this.number = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ResumakeException("Please enter a valid number.");
            }
            break;
        case "email":
            this.email = value;
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

}
