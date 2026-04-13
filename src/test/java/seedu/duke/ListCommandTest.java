package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.YearMonth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.duke.commands.ListCommand;
import seedu.duke.exceptions.ResumakeException;
import seedu.duke.recordtype.Cca;
import seedu.duke.recordtype.Experience;
import seedu.duke.recordtype.Project;

public class ListCommandTest {

    @BeforeEach
    public void setUpUser() {
        User.loadFrom("Test User", 12345678, "test@example.com");
    }

    @Test
    public void execute_emptyList_printsNoRecordsFound() throws Exception {
        RecordList list = new RecordList();
        ListCommand cmd = new ListCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("No records found."));
        assertEquals(0, list.getSize());
    }

    @Test
    public void execute_listAll_printsAllRecords() throws Exception {
        RecordList list = new RecordList();
        Experience exp = new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1));
        Project proj = new Project("Alpha Club", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1));

        list.add(exp);
        list.add(proj);

        ListCommand cmd = new ListCommand();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("Here is a list of all records."));
        assertTrue(result.contains("1. " + exp));
        assertTrue(result.contains("2. " + proj));
        assertEquals(2, list.getSize());
    }

    @Test
    public void execute_filterByExperience_printsOnlyExperience() throws Exception {
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

        ListCommand cmd = new ListCommand("E");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("Here is a list of E records."));
        assertTrue(result.contains("1. " + exp));
        assertFalse(result.contains(proj.toString()));
        assertFalse(result.contains(cca.toString()));
        assertEquals(3, list.getSize());
    }

    @Test
    public void execute_filterByCca_printsOnlyCca() throws Exception {
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

        ListCommand cmd = new ListCommand("C");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("Here is a list of C records."));
        assertTrue(result.contains("1. " + cca));
        assertFalse(result.contains(exp.toString()));
        assertFalse(result.contains(proj.toString()));
    }

    @Test
    public void execute_filterByProject_printsOnlyProject() throws Exception {
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

        ListCommand cmd = new ListCommand("P");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("Here is a list of P records."));
        assertTrue(result.contains("1. " + proj));
        assertFalse(result.contains(exp.toString()));
        assertFalse(result.contains(cca.toString()));
    }

    @Test
    public void execute_invalidType_throwsExceptionWithCorrectMessage() {
        RecordList list = new RecordList();
        list.add(new Experience("Zebra Club", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1)));

        ListCommand cmd = new ListCommand("Z");

        ResumakeException ex = assertThrows(ResumakeException.class, () -> cmd.execute(list));
        assertTrue(ex.getMessage().contains("Invalid type for list command."));
        assertTrue(ex.getMessage().contains("Valid types: E: Experience, C: Cca, P: Project"));
    }

    @Test
    public void execute_nullList_throwsAssertionError() {
        ListCommand cmd = new ListCommand();
        assertThrows(AssertionError.class, () -> cmd.execute(null));
    }

    @Test
    public void execute_filteredNumbering_startsFromOneForMatchingRecordsOnly() throws Exception {
        RecordList list = new RecordList();

        Experience exp1 = new Experience("Exp One", "Member", "Java",
                YearMonth.of(2024, 1), YearMonth.of(2025, 1));
        Project proj = new Project("Proj One", "Lead", "Python",
                YearMonth.of(2023, 1), YearMonth.of(2024, 1));
        Experience exp2 = new Experience("Exp Two", "Intern", "C",
                YearMonth.of(2022, 1), YearMonth.of(2022, 12));

        list.add(exp1);
        list.add(proj);
        list.add(exp2);

        ListCommand cmd = new ListCommand("E");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            cmd.execute(list);
        } finally {
            System.setOut(originalOut);
        }

        String result = output.toString();
        assertTrue(result.contains("1. " + exp1));
        assertTrue(result.contains("2. " + exp2));
        assertFalse(result.contains("3. "));
    }
}
