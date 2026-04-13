package seedu.duke.commands;

import seedu.duke.RecordList;
import seedu.duke.Ui;
import seedu.duke.recordtype.Record;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Represents a command that searches for bullets containing a given keyword.
 * The search is performed across all records in the {@link RecordList},
 * and only matching bullets are displayed under each matching record.
 */
public class FindBulletCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindBulletCommand.class.getName());

    private final String keyword;
    private final Ui ui;

    /**
     * Creates a FindBulletCommand with the specified keyword.
     *
     * @param keyword the keyword used to search for matching bullets.
     * @throws IllegalArgumentException if the keyword is null or blank.
     */
    public FindBulletCommand(String keyword) {
        this(keyword, new Ui());
    }

    public FindBulletCommand(String keyword, Ui ui) {
        if (keyword == null) {
            throw new IllegalArgumentException("Keyword cannot be null.");
        }

        String trimmedKeyword = keyword.trim();
        if (trimmedKeyword.isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be blank.");
        }

        this.keyword = trimmedKeyword;
        this.ui = ui == null ? new Ui() : ui;

        assert this.ui != null : "Ui should be initialized";
        assert this.keyword != null && !this.keyword.isBlank() : "Keyword should not be blank";

        logger.info("FindBulletCommand created with keyword: " + this.keyword);
    }

    public String getKeyword() {
        return keyword;
    }

    /**
     * Executes the find bullet operation by searching for bullet points that contain
     * the specified keyword and printing matching records with matching bullets.
     *
     * @param list the {@link RecordList} to search from.
     */
    @Override
    public void execute(RecordList list) {
        logger.info("Executing FindBulletCommand with keyword: " + keyword);

        try {
            if (list == null) {
                throw new IllegalArgumentException("RecordList cannot be null.");
            }

            assert list != null : "RecordList passed to FindBulletCommand should not be null";

            boolean hasMatch = false;
            String lowerKeyword = keyword.toLowerCase();
            int recordDisplayIndex = 0;

            for (Record record : list) {
                recordDisplayIndex++;
                if (record == null) {
                    logger.warning("Encountered null record while searching bullets");
                    continue;
                }

                assert record != null : "Record in RecordList should not be null";

                ArrayList<String> bullets = record.getBullets();
                assert bullets != null : "Bullets list should not be null";

                int matchCount = 0;
                StringBuilder bulletOutput = new StringBuilder();

                for (int i = 0; i < bullets.size(); i++) {
                    String bullet = bullets.get(i);
                    if (bullet == null) {
                        continue;
                    }

                    if (bullet.toLowerCase().contains(lowerKeyword)) {
                        matchCount++;
                        bulletOutput.append("  ")
                                .append(i + 1)
                                .append(". ")
                                .append(bullet)
                                .append(System.lineSeparator());
                    }
                }

                if (matchCount > 0) {
                    hasMatch = true;
                    ui.showMessage(recordDisplayIndex + ". " + record);
                    ui.showMessage("Bullets:");
                    ui.showMessage(bulletOutput.toString().stripTrailing());
                }
            }

            if (!hasMatch) {
                ui.showMessage("No matching bullet points found for keyword: " + keyword);
            }

            logger.info("FindBulletCommand completed successfully for keyword: " + keyword);
        } catch (IllegalArgumentException e) {
            logger.warning("FindBulletCommand failed: " + e.getMessage());
            ui.showError(e.getMessage());
        }
    }
}
