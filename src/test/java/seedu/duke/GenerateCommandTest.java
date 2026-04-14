package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.GenerateCommand;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

public class GenerateCommandTest {

    @BeforeEach
    public void setUp() {
        User.resetInstance();

        User.loadFrom("John", 91234567, "john@example.com");
    }

    @Test
    public void execute_withRecords_printsUserAndAllSections() throws Exception {
        RecordList list = new RecordList();

        Experience exp = new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1));
        Project proj = new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1));
        Cca cca = new Cca("Middle Club", "VP", "C++",
                YearMonth.of(2022, 1), YearMonth.of(2023, 1));

        list.add(exp);
        list.add(proj);
        list.add(cca);

        GenerateCommand cmd = new GenerateCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("John"));
        assertTrue(result.contains("Number: 91234567"));
        assertTrue(result.contains("Email: john@example.com"));

        assertTrue(result.contains("Cca"));
        assertTrue(result.contains("Experience"));
        assertTrue(result.contains("Project"));

        assertTrue(result.contains(cca.toString()));
        assertTrue(result.contains(exp.toString()));
        assertTrue(result.contains(proj.toString()));

        assertEquals(3, list.getSize());
    }

    @Test
    public void execute_recordWithBullets_printsBullets() throws Exception {
        RecordList list = new RecordList();

        Experience exp = new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1));
        exp.addBullet("Built parser");
        exp.addBullet("Wrote tests");

        list.add(exp);

        GenerateCommand cmd = new GenerateCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains(exp.toString()));
        assertTrue(result.contains("Bullets:"));
        assertTrue(result.contains("1. Built parser"));
        assertTrue(result.contains("2. Wrote tests"));
    }

    @Test
    public void execute_recordWithoutBullets_printsNoBulletsMessage() throws Exception {
        RecordList list = new RecordList();

        Project proj = new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1));

        list.add(proj);

        GenerateCommand cmd = new GenerateCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains(proj.toString()));
        assertTrue(result.contains("(no bullets)"));
    }

    @Test
    public void execute_emptyList_printsUserAndSectionHeadersOnly() throws Exception {
        RecordList list = new RecordList();
        GenerateCommand cmd = new GenerateCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();

        assertTrue(result.contains("John"));
        assertTrue(result.contains("Number: 91234567"));
        assertTrue(result.contains("Email: john@example.com"));

        assertTrue(result.contains("Cca"));
        assertTrue(result.contains("Experience"));
        assertTrue(result.contains("Project"));

        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_userDataAccessible_correctValuesLoaded() {
        User user = User.getInstance();
        assertEquals("John", user.getName());
        assertEquals(91234567, user.getNumber());
        assertEquals("john@example.com", user.getEmail());
    }
}
