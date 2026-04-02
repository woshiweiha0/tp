package seedu.duke.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.User;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Record;


public class GenerateCommand extends Command {
    private static final Logger logger = Logger.getLogger(GenerateCommand.class.getName());
    private final Ui ui;
    User user = User.getInstance();

    public GenerateCommand(){
        this.ui = new Ui();
    }

    @Override
    public void execute(RecordList list) throws ResumakeException {
        List<String> recordTypes = new ArrayList<>(List.of("Cca", "Experience", "Project"));

        ui.showMessage(user.getName());
        ui.showMessage("Number: " + user.getNumber());
        ui.showMessage("Email: " + user.getEmail());
        ui.showLine();

        for (String type : recordTypes){
            ui.showMessage(type);
            ui.showLine();
            String charType = type.substring(0,1);
            Integer index = 1;
            for (Record record : list){
                if (type.equals("all") || record.getRecordType().equals(charType)) {
                    logger.info("Executing ShowCommand with Index " + index);

                    assert list != null : "RecordList should not be null";

                    try {

                        System.out.println(record);

                        ArrayList<String> bullets = record.getBullets();

                        if (bullets.isEmpty()) {
                            System.out.println("  (no bullets)");
                        } else {
                            System.out.println("  Bullets:");
                            for (int i = 0; i < bullets.size(); i++) {
                                System.out.println("  " + (i + 1) + ". " + bullets.get(i));
                            }
                        }

                    } catch (Exception e) {
                        logger.warning("Error executing ShowCommand: " + e.getMessage());
                        System.out.println("Error: " + e.getMessage());
                    }

                    ui.showLine();
                }
                index++;
            }
        }

    }
}
