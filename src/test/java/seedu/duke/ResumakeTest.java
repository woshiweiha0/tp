package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResumakeTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

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

        List<String> initialLines = List.of("USER|Test User|11111111|test@example.com");
        Files.write(recordsPath, initialLines);
        User.loadFrom("Test User", 11111111, "test@example.com");
    }

    @AfterEach
    public void tearDown() throws IOException {
        System.setIn(originalIn);
        System.setOut(originalOut);

        if (recordsFileExistedBeforeTest) {
            Files.write(recordsPath, originalRecordsContent);
        } else {
            Files.deleteIfExists(recordsPath);
        }
    }

    @Test
    public void run_byeInput_exits() {
        String userInput = "bye" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Resumake app = new Resumake();
        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Welcome to ResuMake"));
        assertTrue(output.contains("Loaded records from file."));
        assertTrue(output.contains("bye"));
    }

    @Test
    public void run_invalidInput_showsError() {
        String userInput = "nonsense" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Resumake app = new Resumake();
        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Error:"));
    }

    @Test
    public void run_duplicateRecord_continuesToNextCommand() {
        String recordCommand = "project Portfolio /role Developer /tech Java /from 2026-01 /to 2026-03";
        String userInput = recordCommand + System.lineSeparator()
                + recordCommand + System.lineSeparator()
                + "list" + System.lineSeparator()
                + "bye" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(userInput.getBytes(StandardCharsets.UTF_8)));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Resumake app = new Resumake();
        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Duplicate record: an identical record already exists"));
        assertTrue(output.contains("Here is a list of all records."));
        assertTrue(output.contains("1. [P] Portfolio | role: Developer | tech: Java | from: 2026-01 | to: 2026-03"));
        assertTrue(output.contains("bye"));
    }
}
