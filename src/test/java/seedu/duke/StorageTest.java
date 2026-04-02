package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.recordtype.Project;
import seedu.duke.recordtype.Record;

public class StorageTest {
    private Path recordsPath;
    private boolean recordsFileExistedBeforeTest;
    private byte[] originalRecordsContent;

    @BeforeEach
    public void setUp() throws IOException {
        recordsPath = Path.of(Storage.getFilepath()).toAbsolutePath().normalize();
        recordsFileExistedBeforeTest = Files.exists(recordsPath);
        if (recordsFileExistedBeforeTest) {
            originalRecordsContent = Files.readAllBytes(recordsPath);
        } else {
            originalRecordsContent = null;
        }
        User.loadFrom("Default User", 11111111, "default@example.com");
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (recordsFileExistedBeforeTest) {
            Files.write(recordsPath, originalRecordsContent);
        } else {
            Files.deleteIfExists(recordsPath);
        }
    }

    @Test
    public void saveToFile_withUserAndRecord_writesExpectedLines() throws Exception {
        User.loadFrom("Alex", 98765432, "alex@example.com");
        Storage storage = new Storage(new SilentUi());
        RecordList list = new RecordList();
        Project project = new Project(
                "Resume Builder",
                "Developer",
                "Java",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        project.addBullet("Built parser");
        project.addBullet("Wrote tests");
        list.add(project);

        storage.saveToFile(list);

        List<String> lines = Files.readAllLines(recordsPath);
        assertEquals("USER|Alex|98765432|alex@example.com", lines.get(0));
        assertEquals(
                "project Resume Builder /role Developer /tech Java /from 2026-01 /to 2026-03 "
                        + "/bullets Built parser ;; Wrote tests",
                lines.get(1)
        );
    }

    @Test
    public void loadFromFile_missingFile_createsFileAndReturnsEmptyList() throws Exception {
        Storage storage = new Storage(new SilentUi());
        Path tempDir = Files.createTempDirectory("storage-test-create");
        Path tempFile = tempDir.resolve("records.txt");
        try {
            RecordList loaded = storage.loadFromFile(tempFile.toString());

            assertTrue(Files.exists(tempFile));
            assertEquals(0, loaded.getSize());
        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    public void loadFromFile_withUserAndRecords_parsesValidRecords() throws Exception {
        Storage storage = new Storage(new SilentUi());
        Path tempDir = Files.createTempDirectory("storage-test-load");
        Path tempFile = tempDir.resolve("records.txt");
        List<String> lines = List.of(
                "USER|Jamie|87654321|jamie@example.com",
                "project Alpha /role Lead /tech Java /from 2024-01 /to 2024-03 /bullets Built API ;; Added tests",
                "invalid line without required fields",
                "experience Beta /role Intern /tech Python /from 2023-05 /to 2023-08"
        );
        Files.write(tempFile, lines);

        try {
            RecordList loaded = storage.loadFromFile(tempFile.toString());

            assertEquals(2, loaded.getSize());
            Record first = loaded.getRecord(0);
            Record second = loaded.getRecord(1);
            assertEquals("P", first.getRecordType());
            assertEquals("Alpha", first.getTitle());
            assertEquals(2, first.getBullets().size());
            assertEquals("E", second.getRecordType());
            assertEquals("Beta", second.getTitle());
            assertEquals("Jamie", User.getInstance().getName());
            assertEquals(87654321, User.getInstance().getNumber());
            assertEquals("jamie@example.com", User.getInstance().getEmail());
        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);
        }
    }

    private static class SilentUi extends Ui {
        @Override
        public void showMessage(String message) {
        }

        @Override
        public void showLine() {
        }
    }
}
