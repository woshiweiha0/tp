# Tore Lim - Project Portfolio Page

## Overview

**ResuMake CLI** is a desktop command-line application that helps users manage and structure resume content such as projects, experiences, CCAs, and bullet points. The user interacts with it through CLI commands, and the application is written in Java.

Given below are my contributions to the project.

---

## New Feature: List Records

**What it does:**  
Displays all stored records, or filters them by category. Accepts `E` (Experience), `C` (CCA), and `P` (Project) as filter arguments, or shows everything when no filter is given. Records are numbered by their actual position in the full list, so indices remain consistent across filtered and unfiltered views.

**Justification:**  
Users frequently need to inspect a specific category of records without scrolling through the entire list. Consistent indexing across filtered and unfiltered views is important because other commands such as `show`, `edit`, and `delete` all operate by index — if the displayed index were re-numbered during filtering, users would reference the wrong records.

**Highlights:**
- Accepts type filters case-insensitively, so `e`, `E`, and `experience` are all treated uniformly.
- Validates the filter argument and surfaces a clear error message listing the accepted values.
- Uses actual list-position indices even when only a subset of records is shown, keeping indices consistent with all other commands.
- Handles the empty-list case with a dedicated message instead of printing an empty block.

---

## New Feature: Generate Resume

**What it does:**  
Produces a structured resume-style output from the user's stored data. It prints the user's personal details (name, phone number, email), then groups all records under their respective section headings (CCA, Experience, Project), displaying each record together with its bullet points. A skills section derived from the user's tracked tech stack is appended at the end.

**Justification:**  
The core purpose of the application is to help users assemble resume content. Without a way to view everything together in a structured layout, users would have to manually piece records together. The generate command closes that gap by producing output that mirrors a real resume structure in one step.

**Highlights:**
- Reuses `ShowCommand.showRecordWithBullets()` instead of duplicating display logic, keeping the codebase modular.
- Groups records by type using the same single-character type identifiers stored internally, avoiding a separate mapping layer.
- Appends a skills section that aggregates tech tags collected across all records.
- Produces clean section separators between groups for readability.

---

## New Feature: Show Individual Record

**What it does:**  
Displays a single record selected by its 1-based user-facing index, including all its bullet points. If the record has no bullets, a placeholder message is shown.

**Justification:**  
Users need to inspect one record in detail, for example to verify its content before editing or to review its bullets. Printing the full list every time would be slow and noisy for users with many records.

**Highlights:**
- Converts the user-facing 1-based index to a 0-based internal index cleanly in the constructor.
- Validates bounds before accessing the list and surfaces a clear error without crashing.
- Exposed `showRecordWithBullets()` as a static utility so `GenerateCommand` can reuse the same display logic.
- Uses logging to trace invalid access attempts for debugging.

---

## New Feature: Sort Records

**What it does:**  
Sorts all stored records alphabetically by title using a case-insensitive comparator, then confirms to the user that the list has been reordered.

**Justification:**  
As the number of records grows, finding a specific entry by scanning an unsorted list becomes tedious. Alphabetical ordering makes the collection more predictable and improves the readability of subsequent `list` and `generate` output.

**Highlights:**
- Uses `Comparator.comparing()` with `String.CASE_INSENSITIVE_ORDER` for a concise and readable implementation.
- Sorts directly at the `RecordList` level, keeping the command simple and side-effect-free from the caller's perspective.
- Subsequent commands that rely on list order immediately benefit without any additional steps.

---

## New Feature: Edit User Profile (`edituser`)

**What it does:**  
Allows users to update their personal details, name, phone number, or email, one field at a time.

**Justification:**  
User details are displayed in the generated resume, so they must be editable after the initial setup.

**Highlights:**
- Validates the field name at construction time and throws immediately if an unsupported field is given, so invalid commands never reach execution.
- Shows the current value of the field before prompting so the user knows what they are replacing.
- Throws a descriptive final error after exhausting all attempts, telling the user how to retry.

---

## New Feature: User Profile and Skill Tracking

**What it does:**  
Maintains the user's personal details (name, phone number, email) as a singleton throughout the session. Tracks which tech skills appear across the user's records via `addSkills()` and `removeSkills()`, and exposes the aggregated skill set for display in the generated resume.

**Justification:**  
A resume generation tool needs stable user data that persists across commands. Skill tracking avoids requiring the user to maintain a separate skills list — the application derives it automatically from the tech tags already recorded.

**Highlights:**
- Implemented as a singleton so user data is accessible from anywhere without being passed through every command.
- Skill counts are reference-counted: adding a skill increments a counter and removing decrements it, so a skill is only dropped when no record references it.
- Normalises skill strings by stripping surrounding quotation marks (double, curly, and single) before storing them.
- Input validation for name, number, and email is centralised in `editField()`, keeping the prompt logic and the storage logic consistent.

---

## Enhancement: Standardised Error Messages

**What it does:**  
Unified inconsistent error messages across multiple commands so that the same class of error always produces the same user-facing text. For example, all invalid-index errors now surface the same message regardless of which command triggered them.

**Justification:**  
Inconsistent error messages confuse users and make the application harder to reason about. Standardisation also makes test assertions more reliable, since tests can check for a single canonical string.

**Highlights:**
- Standardised invalid-index error text across `DeleteCommand`, `EditBulletCommand`, `MoveBulletCommand`, `EditCommand`, `AddBulletCommand`, and `ShowCommand`.
- Fixed punctuation inconsistencies (missing periods) in date-range and empty-command errors across `Parser`, `Record`, and `EditCommand`.
- Unified duplicate blank-bullet error messages across `Record.addBullet()` and `Record.editBullet()`.

---

## Summary of Contributions

### Code Contributed

- Functional code and tests: [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=tlimnus&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=tlimnus&tabRepo=AY2526S2-CS2113-F09-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Enhancements Implemented

1. **Consistent list indexing**
   - Changed `ListCommand` so that filtered results display their actual list-position index rather than a re-numbered sequence.
   - Benefit: indices shown in `list` always match what other commands such as `show`, `edit`, and `delete` expect.

2. **EditUser retry loop**
   - Extended `EditUserCommand` to retry invalid input up to four times with a decreasing-attempts counter displayed after each failure.
   - Benefit: users are not immediately dropped out of the command on a formatting mistake.

3. **Standardised error messages**
   - Unified invalid-index messages and punctuation across six command files and `Record`.
   - Benefit: consistent user-facing text and more reliable test assertions.

4. **Enabled JVM assertions in Gradle**
   - Added `-ea` flag to `build.gradle` so assert statements are enforced during tests.
   - Benefit: assertion violations surface during test runs instead of being silently ignored.

5. **Comprehensive test coverage**
   - Wrote and significantly expanded tests in `ListCommandTest`, `ShowCommandTest`, `GenerateCommandTest`, `UserTest`, `SortCommandTest`, and `EditUserCommandTest`.
   - Covered normal operation, boundary indices, invalid types, empty lists, skill tracking, and retry-loop behaviour.

### Contributions to the User Guide (UG)

- Documented the `list`, `show`, `sort`, `generate`, and `edituser` commands with usage examples and expected output.
- Clarified valid filter arguments for `list` and the retry behaviour of `edituser`.

### Contributions to the Developer Guide (DG)

- Added implementation notes for `ListCommand`, `GenerateCommand`, `ShowCommand`, `SortCommand`, and `EditUserCommand`.
- Documented the skill-tracking design in `User` and how it integrates with `GenerateCommand`.

### Contributions to Team-Based Tasks

- Standardised error message conventions across the codebase to reduce user-facing inconsistencies.
- Reviewed and fixed Gradle build issues to unblock CI and local test runs.
- Contributed to team discussions on index consistency, command retry behaviour, and error message standards.

### Tools

- Used JUnit to write and expand command and model tests.
- Used Gradle (`test`, `checkstyleMain`, `checkstyleTest`) for build verification.
- Used Java `Logger` for tracing command execution and diagnosing issues.

---
