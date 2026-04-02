# Tore Lim - Project Portfolio Page

## New Feature: Listing Records

### What It Does

The ListCommand feature allows users to display records either as a full list or filtered by category. The command accepts specific record types such as Experience (E), CCA (C), and Project (P), whil
e also supporting a default mode that lists all records. During execution, the command checks whether the requested type is valid, handles the case where no records exist, and prints only the relevant records in a numbered format.

### Justification

This feature improves usability by helping users quickly view only the subset of records they want to work with instead of scanning the entire 
database every time. In a resume-building application, users often need to focus on one category at a time, for example viewing only project entries when updating a technical portfolio. Adding input validation also makes the command more robust, since it prevents invalid filters from silently failing or producing confusing output.

---

## New Feature: Generate Resume

### What it does

The GenerateCommand feature iterates through predefined record categories, namely CCA, Experience, and Project, and displays records under each section. For each category, it derives the corresponding short form used internally in the system and scans the stored records to identify matches. Matching entries are then displayed one by one using ShowCommand.

### Justification

This feature is useful because it organises the user’s stored records into structured sections that resemble a resume layout. Rather than forcing the user to manually inspect all entries and mentally group them, the system can automatically separate them into meaningful headings. This improves convenience and supports the main purpose of the application, which is to help users manage and present resume content more effectively.

### Highlights

- Automatically groups records into logical resume sections.
- Reuses ShowCommand instead of duplicating display logic, which improves modularity.
- Uses an internal mapping from full category names to stored type identifiers.
- Produces cleaner output for users by separating each section with divider lines.

---

## New Feature: Sort

### Automatically groups records into logical resume sections.
Reuses ShowCommand instead of duplicating display logic, which improves modularity.
Uses an internal mapping from full category names to stored type identifiers.
Produces cleaner output for users by separating each section with divider lines.

You may want to be careful when presenting this feature, because based on the code shown, ShowCommand.execute() currently does not actually display a record. If that is the latest version, then in your write-up you should describe the intended design but avoid overstating that it is fully complete unless you have already fixed it.

### What it does

The SortCommand sorts all stored records alphabetically by title, using a case-insensitive comparator. Once sorting is complete, it informs the user that the list has been reordered.

### Justification

Sorting improves the overall manageability of the record list. As the number of entries grows, it becomes harder for users to find a specific item quickly. Alphabetical sorting makes the collection more predictable and easier to browse, especially when users are searching for a record by name. This also improves the readability of later commands such as list and generate, since they operate on a more organised dataset.

### Highlights
- Uses Java’s Comparator.comparing() with String.CASE_INSENSITIVE_ORDER for clean and readable implementation.
- Sorts directly at the RecordList level, making the feature efficient and simple to invoke.
- Improves downstream commands by keeping records in a consistent order.
- Includes logging statements to aid debugging and trace execution flow.

---

## New Feature Individual record display 

### What it does

The ShowCommand is intended to display a specific record selected by index. It converts the user-facing 1-based index into a 0-based internal index and checks whether the requested record is within valid bounds before proceeding.

### Justification

A targeted display feature is important because users may want to inspect a single record in detail without printing the full list. This is especially helpful when the application stores multiple experiences, projects, and activities, and the user wants to verify one entry before editing or using it elsewhere. Index validation is also necessary to prevent runtime crashes when users enter invalid positions.

### Highlights
- Translates user input into an internal array index cleanly.
- Checks for negative or out-of-range indices before accessing the list.
- Uses logging to record invalid access attempts for debugging.
- Improves fault tolerance by catching errors instead of letting the program terminate unexpectedly.
