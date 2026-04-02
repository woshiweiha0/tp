package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

import java.util.logging.Logger;

/**
 * Represents a command that searches for records containing a given keyword.
 * The search is performed across all records in the {@link RecordList},
 * and matching records are displayed to the user.
 */
public class FindCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindCommand.class.getName());

    private final String keyword;
    private final Ui ui;

    /**
     * Creates a FindCommand with the specified keyword.
     *
     * @param keyword The keyword used to search for matching records.
     * @throws IllegalArgumentException if the keyword is null or blank.
     */
    public FindCommand(String keyword) {
        this(keyword, new Ui());
    }

    public FindCommand(String keyword, Ui ui) {
        if (keyword == null) {
            throw new IllegalArgumentException("Keyword cannot be null");
        }

        String trimmedKeyword = keyword.trim();
        if (trimmedKeyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be blank");
        }

        this.keyword = trimmedKeyword;
        this.ui = ui == null ? new Ui() : ui;

        assert this.ui != null : "Ui should be initialized";
        assert this.keyword != null && !this.keyword.isBlank() : "Keyword should not be blank";

        logger.info("FindCommand created with keyword: " + this.keyword);
    }

    /**
     * Returns the keyword used by this command for matching records.
     *
     * @return the non-blank keyword for the search.
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Executes the find operation by searching for records that contain
     * the specified keyword and printing the results.
     *
     * @param list the {@link RecordList} to search from.
     */
    @Override
    public void execute(RecordList list) {
        logger.info("Executing FindCommand with keyword: " + keyword);

        try {
            if (list == null) {
                throw new IllegalArgumentException("RecordList cannot be null");
            }

            assert list != null : "RecordList passed to FindCommand should not be null";

            ui.showLine();
            ui.showMessage("Matching records:");

            boolean hasMatch = false;
            int displayIndex = 1;

            for (Record record : list) {
                assert record != null : "Record in RecordList should not be null";

                if (record == null) {
                    logger.warning("Encountered null record while searching");
                    continue;
                }

                logger.fine("Checking record: " + record.getTitle());

                if (record.containsKeyword(keyword)) {
                    logger.info("Match found for keyword \"" + keyword + "\": " + record.getTitle());
                    ui.showMessage(displayIndex + ". " + record);
                    displayIndex++;
                    hasMatch = true;
                }
            }

            if (!hasMatch) {
                logger.info("No matches found for keyword: " + keyword);
                ui.showMessage("No matching records found for keyword: " + keyword);
            }

            ui.showLine();
            logger.info("FindCommand completed successfully for keyword: " + keyword);

        } catch (IllegalArgumentException e) {
            logger.warning("FindCommand failed: " + e.getMessage());
            ui.showLine();
            ui.showError(e.getMessage());
            ui.showLine();
        }
    }
}
