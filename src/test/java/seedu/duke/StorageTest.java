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
        User.resetInstance();

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
        User.resetInstance();

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
        assertTrue(lines.get(1).startsWith("project b64u:"));
        assertTrue(lines.get(1).contains(" /role b64u:"));
        assertTrue(lines.get(1).contains(" /tech b64u:"));
        assertTrue(lines.get(1).contains(" /from 2026-01 /to 2026-03 /bullets "));
        assertTrue(lines.get(1).contains("b64:"));
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

    @Test
    public void loadFromFile_nonUserFirstLine_parsesAsRecord() throws Exception {
        User.resetInstance();

        User.loadFrom("Existing User", 99999999, "existing@example.com");
        Storage storage = new Storage(new SilentUi());
        Path tempDir = Files.createTempDirectory("storage-test-first-line-record");
        Path tempFile = tempDir.resolve("records.txt");
        List<String> lines = List.of(
                "project First /role Dev /tech Java /from 2026-01 /to 2026-02"
        );
        Files.write(tempFile, lines);

        try {
            RecordList loaded = storage.loadFromFile(tempFile.toString());

            assertEquals(1, loaded.getSize());
            assertEquals("First", loaded.getRecord(0).getTitle());
            assertEquals("Existing User", User.getInstance().getName());
        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    public void loadFromFile_invalidNonUserFirstLine_skipsLineAndParsesRemaining() throws Exception {
        User.resetInstance();

        User.loadFrom("Existing User", 99999999, "existing@example.com");
        Storage storage = new Storage(new SilentUi());
        Path tempDir = Files.createTempDirectory("storage-test-invalid-first-line");
        Path tempFile = tempDir.resolve("records.txt");
        List<String> lines = List.of(
                "invalid line without required fields",
                "project Good /role Dev /tech Java /from 2026-01 /to 2026-02"
        );
        Files.write(tempFile, lines);

        try {
            RecordList loaded = storage.loadFromFile(tempFile.toString());

            assertEquals(1, loaded.getSize());
            assertEquals("Good", loaded.getRecord(0).getTitle());
        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    public void loadFromFile_invalidUserNumber_fallsBackToExistingUser() throws Exception {
        User.resetInstance();

        User.loadFrom("Fallback User", 12345678, "fallback@example.com");
        Storage storage = new Storage(new SilentUi());
        Path tempDir = Files.createTempDirectory("storage-test-invalid-user");
        Path tempFile = tempDir.resolve("records.txt");
        List<String> lines = List.of(
                "USER|BadUser|NaN|bad@example.com",
                "project Good /role Dev /tech Java /from 2026-01 /to 2026-02"
        );
        Files.write(tempFile, lines);

        try {
            RecordList loaded = storage.loadFromFile(tempFile.toString());

            assertEquals(1, loaded.getSize());
            assertEquals("Good", loaded.getRecord(0).getTitle());
            assertEquals("Fallback User", User.getInstance().getName());
            assertEquals(12345678, User.getInstance().getNumber());
            assertEquals("fallback@example.com", User.getInstance().getEmail());
        } finally {
            Files.deleteIfExists(tempFile);
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    public void saveToFile_unknownRecordType_skipsUnknownRecord() throws Exception {
        User.resetInstance();

        User.loadFrom("Alex", 98765432, "alex@example.com");
        Storage storage = new Storage(new SilentUi());
        RecordList list = new RecordList();
        list.add(new UnknownRecordType());

        storage.saveToFile(list);

        List<String> lines = Files.readAllLines(recordsPath);
        assertEquals(1, lines.size());
        assertEquals("USER|Alex|98765432|alex@example.com", lines.get(0));
    }

    @Test
    public void saveToFile_bulletWithDelimiter_roundTripsAsSingleBullet() throws Exception {
        User.resetInstance();

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
        project.addBullet("first ;; second");
        list.add(project);

        storage.saveToFile(list);
        RecordList loaded = storage.loadFromFile(Storage.getFilepath());

        assertEquals(1, loaded.getSize());
        assertEquals(1, loaded.getRecord(0).getBullets().size());
        assertEquals("first ;; second", loaded.getRecord(0).getBullets().get(0));
    }

    @Test
    public void saveToFile_fieldsWithDelimiterLikeText_roundTripsCorrectly() throws Exception {
        User.loadFrom("Alex", 98765432, "alex@example.com");
        Storage storage = new Storage(new SilentUi());
        RecordList list = new RecordList();
        Project project = new Project(
                "Parser /role experiment",
                "Lead /tech mentor",
                "Java /from scratch",
                YearMonth.parse("2026-01"),
                YearMonth.parse("2026-03")
        );
        list.add(project);

        storage.saveToFile(list);
        RecordList loaded = storage.loadFromFile(Storage.getFilepath());

        assertEquals(1, loaded.getSize());
        assertEquals("Parser /role experiment", loaded.getRecord(0).getTitle());
        assertEquals("Lead /tech mentor", loaded.getRecord(0).getRole());
        assertEquals("Java /from scratch", loaded.getRecord(0).getTech());
    }

    private static class UnknownRecordType extends Record {
        UnknownRecordType() {
            super("Unknown", "Dev", "Java", YearMonth.parse("2026-01"), YearMonth.parse("2026-02"));
            this.recordType = "X";
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
