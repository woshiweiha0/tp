# Ryan's Project Portfolio Page

Project: Resumake CLI

Resumake CLI is a desktop command line application designed for university
students or recently graduated students to design and structure their resume
on the spot. Users can add projects, experiences or CCAs, and can also add
bullets to their records.

Given below are my contributions to the project.

---

## New Feature: Added EditCommand

**What it does:**
EditCommmand allows users to modify existing records in the list by specifying the record index
and the fields to be updated. The user an update one or mutliple fields
at a time (e.g., title, role, tech, date range) in a single command without affecting other fields.

**Justification:** This feature significantly improves usability of the product by allowing users
to change fields that they entered previously without deleting or recreating records.

In real world usage, resume content is frequently revised depending on the role the user is applying for.
So, providing an efficient way for user to edit records is directly essential for maintaining accuracy
and convenience.

**Highlights:**

- Implemented flexible parsing logic in Parser.java to support editing multiple optional fields
  in a single command.
- Designed command to allow partial updates, ensuring unchanged fields remain intact.
- Ensured data consistency by validating date range. (e.g., preventing end date from being earlier than start date)
- Adopted a fail-safe design, where no changes are applied if validation fails.
- Added comprehensive unit tests to cover valid edits, invalid inputs, and edge cases.
- Improved maintainability by introducing structured field parsing (ParsedFields) to simplify argument handling.

---

## New Feature: Added EditBulletCommand

**What it does**: The EditBulletCommand allows users to modify an existing bullet point within a record by specifying both the record index and the bullet index.
It replaces the content of the selected bullet without affecting other bullets in the record.

**Justification**: Bullet points are a key component of resume entries, often requiring frequent refinement for clarity and impact.
This feature enables users to directly edit individual bullet points without needing to delete and recreate them, improving efficiency and usability.

**Highlights:**

- Supports precise editing of individual bullet points using record and bullet indices.
- Maintains list integrity, ensuring no change in bullet count after editing
- Includes validation to prevent editing with invalid indices or blank bullet content.
- Handles errors by throwing ResumakeException for invalid operations.
- Includes comprehensive test coverage for success cases, invalid indices and edge cases.

---

## New Feature: ListBullets

**What it does**: Displays all bullet points associated with a specific record, providing users with a comprehensive view of achievements and responsibilities within a single resume entry.

**Justification**: While editing records individually is useful, having a feature to view all bullets associated with a record improves the overall usability and allows users to review their complete resume content at a glance.

**Highlights**:

- Seamlessly integrated bullet display into the ShowCommand
- Allows users to view structured resume content with all related bullets
- Built to be extensible for future enhancements

---

## Code Contributed:

<a href="https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=ryantrc&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false">RepoSense Link</a>

---

## Project Management:

- Managed feature development by updating existing classes and methods to integrate new features
  well with existing classes like Parser.java.
- Contributed by maintaining gradle checks across multiple pull requests.
- Maintained javadoc across methods in EditCommand and EditBulletCommand, as well as in related classes.

---

## Enhancements to existing features:

- Enhanced the record editing workflow by strengthening validation and defensive programming practices (assertions) for EditCommand, EditBulletCommand, and related record mutation methods.
- Improved robustness of editing-related commands by adding logging and better error handling for invalid inputs and edge cases.
- Increased test coverage for editing-related features by adding comprehensive unit tests for commands, parser edge cases, and Record mutation methods.

## Parser Improvements:

- Improved Parser.java to handle edge cases and validate user input more effectively.
- Enhanced error messages to provide users with clearer guidance on correct command syntax.
- Added validation to prevent malformed commands from corrupting records.

---

## Documentation

### User Guide:

- Added documentation for editing-related commands such as edit, editbullet, addbullet, and movebullet.
- Improved command descriptions and examples so users can understand how to update resume fields and bullet points correctly.

---

## Community:

- Assisted with debugging and reviewing editing-related code paths, especially around parser behavior, command validation, and testing.
- Contributed to team integration by resolving merge/sync issues between feature branches and updated upstream branches.

## Tools:

- Used Gradle extensively to run test, check, checkstyleMain and checkstyleTest during development and debugging.
- Improved quality of codebase through systematic use of assertions, logging and Junit tests.
