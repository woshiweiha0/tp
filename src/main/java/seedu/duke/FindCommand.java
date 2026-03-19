package seedu.duke;

import seedu.duke.recordtype.Record;

public class FindCommand extends Command {
    private final String keyword;
    private final Ui ui;

    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
        this.ui = new Ui();
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public void execute(RecordList list) {
        ui.showLine();
        System.out.println("Matching records:");

        boolean hasMatch = false;
        int displayIndex = 1;

        for (Record record : list) {
            if (record.containsKeyword(keyword)) {
                System.out.println(displayIndex + ". " + record.getTitle());
                displayIndex++;
                hasMatch = true;
            }
        }

        if (!hasMatch) {
            System.out.println("No matching records found for keyword: " + keyword);
        }

        ui.showLine();
    }
}
